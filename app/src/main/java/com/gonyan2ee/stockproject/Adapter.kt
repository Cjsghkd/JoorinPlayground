package com.gonyan2ee.stockproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

data class Stock(
    var image: Int,
    var price: String
)

class Adapter(private val items: ArrayList<Stock>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        val image: ImageView = items.findViewById<ImageView>(R.id.image)
        val price: TextView = items.findViewById<TextView>(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items.get(position).image != null) {
            holder.image.setImageDrawable(items.get(position).image.toDrawable())
        }
        holder.price.text = items.get(position).price
    }

    override fun getItemCount(): Int {
        return items.size
    }
}