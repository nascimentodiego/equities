package br.com.dfn.equities.realtime

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealTimeBuilder : LifecycleObserver {
    private var items: MutableList<Item> = mutableListOf()
    private var listeners: HashMap<String, ValueEventListener> = hashMapOf()
    private val database = Firebase.database
    private val myRef = database.reference

    fun registerItem(
        node: String,
        keys: List<String>,
        onDataChange: (key: String, values: List<String>) -> Unit
    ) {
        val item = Item(node, keys, onDataChange)
        items.add(item)
        register(item)
    }

    fun unRegisterItem(key: String) {
        val position = items.indexOfFirst { it.node == key }
        if (position != -1) {
            items.removeAt(position)
            val listener = listeners[key]
            listener?.let {
                myRef.removeEventListener(it)
            }
        }
    }

    private fun register(item: Item) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val values = item.keys.map { snapshot.child(it).value.toString() }
                item.onDataChange(item.node, values)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        listeners[item.node] = listener
        myRef.child(item.node)
            .addValueEventListener(listener)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun registerAll() {
        listeners.forEach {
            myRef.child(it.key)
                .addValueEventListener(it.value)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun unRegisterAll() {
        listeners.forEach {
            myRef.removeEventListener(it.value)
        }
    }
}

data class Item(
    var node: String,
    var keys: List<String>,
    var onDataChange: (key: String, values: List<String>) -> Unit
)

fun realTimeRegister(func: RealTimeBuilder.() -> Unit): RealTimeBuilder {
    return RealTimeBuilder().apply(func)
}



