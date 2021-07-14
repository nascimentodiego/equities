package br.com.dfn.equities.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.dfn.equities.databinding.MainBinding
import br.com.dfn.equities.realtime.realTimeRegister
import br.com.dfn.equities.repositories.Result
import br.com.dfn.equities.ui.home.adapters.EquitiesAdapter
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


    private fun updateEquities(code: String, values: List<String>) {
        val list = adapter.dataSet
        val position = list.indexOfFirst { it.code == code }
        if (position != -1) {
            val share = list[position]
            list[position] = share.copy(lastPrice = values[0], percent = values[1])
            adapter.notifyItemChanged(position)
        }
    }

    private fun registerFirebase() {
        val fields = listOf("price", "percent")
        realTimeRegister {
            registerItem(
                node = "AAPL34",
                keys = fields,
                onDataChange = ::updateEquities
            )
            registerItem(
                node = "GOGL34",
                keys = fields,
                onDataChange = ::updateEquities
            )
            registerItem(
                node = "AMZO34",
                keys = fields,
                onDataChange = ::updateEquities
            )
            registerItem(
                node = "COCA34",
                keys = fields,
                onDataChange = ::updateEquities
            )
            registerItem(
                node = "TSLA34",
                keys = fields,
                onDataChange = ::updateEquities
            )
            lifecycle.addObserver(this)
        }

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
