package com.gonyan2ee.stockproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gonyan2ee.stockproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val tradingFragment = TradingFragment()
        val rankFragment = RankFragment()
        val mypageFragment = MypageFragment()
        val fragmentManager = supportFragmentManager

        binding.bottomNavigation.selectedItemId = R.id.mypage
        showFragment(fragmentManager.beginTransaction(), mypageFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            val fragmentTransaction = fragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.rank -> showFragment(fragmentTransaction, rankFragment)
                R.id.trading -> showFragment(fragmentTransaction, tradingFragment)
                R.id.mypage -> showFragment(fragmentTransaction, mypageFragment)
            }
            true
        }
    }
}

fun showFragment(fragmentTransaction: FragmentTransaction, fragment: Fragment) {
    fragmentTransaction.replace(R.id.container, fragment)
    fragmentTransaction.commit()
}