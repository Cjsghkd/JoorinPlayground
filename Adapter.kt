package com.gonyan2ee.stockproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Stock(
    var image: Int,
    var price: String,
    var name : String,
    var prePrice : String
)

class Adapter(private val items: ArrayList<Stock>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        val image: ImageView = items.findViewById(R.id.ic_image)
        val price: TextView = items.findViewById(R.id.price)
        val name: TextView = items.findViewById(R.id.name)
        val prePrice: TextView = items.findViewById(R.id.profit_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            image.setImageResource(items[position].image)
            price.text = items[position].price
            name.text = items[position].name
            prePrice.text = items[position].prePrice
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}