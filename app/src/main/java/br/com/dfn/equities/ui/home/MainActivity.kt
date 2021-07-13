package br.com.dfn.equities.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.dfn.equities.databinding.MainBinding
import br.com.dfn.equities.repositories.Result
import br.com.dfn.equities.ui.home.adapters.EquitiesAdapter
import br.com.dfn.equities.ui.model.Equities
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: EquitiesViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private val TAG = "Diego"
    private val adapter = EquitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews(binding)
        setupObservable()
        registerFirebase()
    }

    private fun updateEquities(code: String, price: String, percent: String) {
        val list = adapter.dataSet
        val position = list.indexOfFirst { it.code == code }
        if (position != 1) {
            val share = list[position]
            list[position] = share.copy(lastPrice = price, percent = percent)
            adapter.notifyItemChanged(position)
        }
    }

    private fun registerFirebase() {
        val database = Firebase.database
        val myRef = database.reference

        val equities = myRef.child("AAPL34")
        equities.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val price = snapshot.child("price")
                val percent = snapshot.child("percent")
                Log.d(TAG, "price is: ${price.value}")
                Log.d(TAG, "percent is: ${percent.value}")
                updateEquities("AAPL34",price.value.toString(),percent.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        val equities2 = myRef.child("AMZO34")
        equities2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val price = snapshot.child("price")
                val percent = snapshot.child("percent")
                Log.d(TAG, "price is: ${price.value}")
                Log.d(TAG, "percent is: ${percent.value}")
                updateEquities("AMZO34",price.value.toString(),percent.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        val equities3 = myRef.child("COCA34")
        equities3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val price = snapshot.child("price")
                val percent = snapshot.child("percent")
                Log.d(TAG, "price is: ${price.value}")
                Log.d(TAG, "percent is: ${percent.value}")
                updateEquities("COCA34",price.value.toString(),percent.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun setupObservable() {
        viewModel.getEquities()
        viewModel.equities.observe(this, {
            when (it) {
                is Result.Loading -> {
                    Toast.makeText(baseContext, "Carregando....", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    adapter.dataSet = it.data.toMutableList()
                    recyclerView.adapter = adapter
                }
                is Result.EquitiesError -> {
                    Toast.makeText(baseContext, "EquitiesError", Toast.LENGTH_SHORT).show()
                }
                is Result.NoConnectionError -> {
                    Toast.makeText(baseContext, "EquitiesError", Toast.LENGTH_SHORT).show()
                }
                is Result.GenericError -> {
                    Toast.makeText(baseContext, "GenericError", Toast.LENGTH_SHORT).show()
                }
                is Result.EquitiesError -> {
                    it.error
                }
                else -> Toast.makeText(baseContext, "GenericError", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupViews(binding: MainBinding) {
        recyclerView = binding.rcvEquities
    }
}
