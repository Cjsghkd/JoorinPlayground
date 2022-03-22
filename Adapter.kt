package com.gonyan2ee.stockproject

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

data class Stock(
    var image: Int,
    var price: String,
    var name: String,
    var profitRate: String
) : Serializable

class Adapter(private val items: ArrayList<Stock>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(items: View) : RecyclerView.ViewHolder(items) {
        val image: ImageView = items.findViewById(R.id.ic_image)
        val price: TextView = items.findViewById(R.id.price)
        val name: TextView = items.findViewById(R.id.name)
        val profitRate: TextView = items.findViewById(R.id.profit_rate)
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
            profitRate.text = items[position].profitRate

            val profitRateValue = (profitRate.text.toString()).replace("%", "").toDouble()
            when {
                profitRateValue > 0 -> profitRate.setTextColor(Color.RED)
                profitRateValue < 0 -> profitRate.setTextColor(Color.BLUE)
                else -> profitRate.setTextColor(Color.GRAY)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Chart::class.java)
                intent.putExtra("items", items[position])

                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}