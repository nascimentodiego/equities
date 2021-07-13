package br.com.dfn.equities.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.dfn.equities.R
import br.com.dfn.equities.ui.extensions.loadUrl
import br.com.dfn.equities.ui.model.Equities

class EquitiesAdapter(
    var dataSet: MutableList<Equities> = mutableListOf()
) : RecyclerView.Adapter<EquitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgLogo: AppCompatImageView = view.findViewById(R.id.img_logo)
        val txtCode: TextView = view.findViewById(R.id.txtCode)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtLastPrice: TextView = view.findViewById(R.id.txtLastPrice)
        val txtPercent: TextView = view.findViewById(R.id.txtPercent)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val share = dataSet[position]
        holder.apply {
            imgLogo.loadUrl(share.logo)
            txtCode.text = share.code
            txtDescription.text = share.description
            txtLastPrice.text = share.lastPrice
            txtPercent.text = share.percent
        }
    }
}