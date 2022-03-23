package com.gonyan2ee.stockproject

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gonyan2ee.stockproject.databinding.ActivityChartBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
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
            // code 부분에 종목 코드 입력
            // date 부분에 일자 입력
            var url = "https://ssl.pstatic.net/imgfinance/chart/item/candle/day/005930.png"

            val doc: Document = Jsoup.connect(url).ignoreContentType(true).get()
            var chart = doc.select("div.chart img").attr("src")
            Log.d("chart", chart)

            Log.d("url", url)
            url.replace("code", stockCode[0])
            val samsung = url

            runOnUiThread {
                Glide.with(this)
                    .load(samsung)
                    .placeholder(R.drawable.ic_load_error)
                    .error(R.drawable.ic_load_error)
                    .fallback(R.drawable.ic_load_error)
                    .into(binding.chart)
            }

        }
    }


}