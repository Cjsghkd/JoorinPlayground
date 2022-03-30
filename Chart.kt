package com.gonyan2ee.stockproject

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gonyan2ee.stockproject.databinding.ActivityChartBinding
import com.google.firebase.firestore.FirebaseFirestore
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.concurrent.thread


class Chart : AppCompatActivity() {
    private lateinit var items: Stock
    private val binding by lazy { ActivityChartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stockCode = listOf(
            "005930", "373220", "000660", "035420", "207940", "035720", "005380", "006400", "051910", "000270", "323410", "068270", "005490", "005490", "028260", "066570", "055550",
            "012330", "377300", "096770"
        )

        val stockName = listOf(
            "삼성전자", "LG에너지솔루션", "SK하이닉스", "NAVER", "삼성바이오로직스", "카카오", "현대차", "삼성SDI", "LG화학", "기아",
            "카카오뱅크", "셀트리온", "POSCO홀딩스", "KB금융", "삼성물산", "LG전자", "신한지주", "현대모비스", "카카오페이", "SK이노베이션"
        )

        items = intent.getSerializableExtra("items") as Stock

        binding.image.setImageResource(items.image)
        binding.name.text = items.name
        binding.price.text = items.price

        thread {
            var url = "https://ssl.pstatic.net/imgfinance/chart/item/candle/day/"
            var code = ""
            for (i in stockName.indices) {
                if (stockName[i] == items.name) {
                    code = stockCode[i]
                }

            }
            url = "$url$code.png"

            runOnUiThread {
                Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_load_error)
                    .error(R.drawable.ic_load_error)
                    .fallback(R.drawable.ic_load_error)
                    .into(binding.chart)
            }

        }
        binding.buy.setOnClickListener {
            trading(binding.buy)
            Toast.makeText(this, "매수가 정상처리 되었습니다", Toast.LENGTH_SHORT).show()
        }

        binding.sell.setOnClickListener {
            trading(binding.sell)
            Toast.makeText(this, "매도가 정상처리 되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun trading(buttonID: Button) {
        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        var phone = ""
        val firebase = FirebaseFirestore.getInstance()

        requestPermissions(arrayOf(Manifest.permission.READ_PHONE_NUMBERS), PERMISSION_REQUEST_CODE)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "수동으로 전화번호 접근 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
            finish()

        } else
            phone = tm.line1Number

        val docRef = firebase.collection(phone).document("moneyData")
        var money: Long
        val stockPriceText = items.price.replace("원", "")
        Log.d("stockPrice", stockPriceText)
        val stockPrice = stockPriceText.toInt()

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    money = document.data?.get("money") as Long

                    val amount = Integer.parseInt(binding.amount.text.toString())
                    val totalPrice = amount * stockPrice

                    when (buttonID) {
                        binding.buy -> money -= totalPrice
                        binding.sell -> money += totalPrice
                        else -> Log.d("error", "잘못된 접근입니다")
                    }

                    moneyData["money"] = money.toInt()

                    firebase.collection(phone)
                        .document("moneyData")
                        .set(moneyData)
                        .addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "success")
                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                        }

                } else {
                    Log.d("error", "empty")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", "error!")
            }
    }
}
