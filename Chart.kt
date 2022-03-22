package com.gonyan2ee.stockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gonyan2ee.stockproject.databinding.ActivityChartBinding

class Chart : AppCompatActivity() {
    private lateinit var items: Stock
    private val binding by lazy {ActivityChartBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        items = intent.getSerializableExtra("items") as Stock

        binding.image.setImageResource(items.image)
        binding.name.text = items.name
        binding.price.text = items.price
    }
}