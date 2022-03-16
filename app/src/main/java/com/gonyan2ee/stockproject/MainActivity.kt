package com.gonyan2ee.stockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gonyan2ee.stockproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.bottomNavigation.setOnNavigationItemReselectedListener() {

        }
    }
}