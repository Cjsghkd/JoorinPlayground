package com.gonyan2ee.stockproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TradingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stockCode = listOf(
            "005930", "373220", "000660", "035420", "207940", "035720", "005380", "006400", "051910", "000270", "323410", "068270", "005490", "005490", "028260", "066570", "055550",
            "012330", "377300", "096770"
        )
        val stockName = listOf(
            "삼성전자", "LG에너지솔루션", "SK하이닉스", "NAVER", "삼성바이오로직스", "카카오", "현대차", "삼성SDI", "LG화학", "기아",
            "카카오뱅크", "셀트리온", "POSCO홀딩스", "KB금융", "삼성물산", "LG전자", "신한지주", "현대모비스", "카카오페이", "SK이노베이션"
        )

        val imageList = listOf(
            R.drawable.ic_samsung, R.drawable.ic_lg_energy_solution, R.drawable.ic_sk_hynix, R.drawable.ic_naver,
            R.drawable.ic_samsung_biologics, R.drawable.ic_kakao, R.drawable.ic_hyundai, R.drawable.ic_samsung_sdi,
            R.drawable.ic_lg_chemistry, R.drawable.ic_kia, R.drawable.ic_kakao_bank, R.drawable.ic_celltrion,
            R.drawable.ic_posco, R.drawable.ic_kb, R.drawable.ic_samsung_cnt, R.drawable.ic_lg_electronics,
            R.drawable.ic_shinhan_financial_group, R.drawable.ic_hyundai_mobis, R.drawable.ic_kakao_pay,
            R.drawable.ic_sk_innovation
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val context = context

        val url = "https://finance.naver.com/item/main.naver?code="
        Log.d("size", stockCode.size.toString())
        CoroutineScope(Dispatchers.Main).launch {


            val prePriceList = mutableListOf<String>()

            for (code in stockCode)
                prePriceList.add(sharePrice((url + code), "preSharePrice"))
            while (true) {
                val price = mutableListOf<String>()
                val profitRateList = mutableListOf<String>()

                for (i in stockCode.indices)
                    price.add(sharePrice(url + stockCode[i], "sharePrice") + "원")
                for (i in 0 until prePriceList.size) {
                    val priceInt = price[i].replace("원", "").toInt()
                    val profit = priceInt / (prePriceList[i].toInt()).toDouble()
                    var profitRate = String.format("%.2f", profit * 100 - 100)

                    if (profitRate.toDouble() >= 0)
                        profitRate = "+$profitRate"
                    profitRateList.add("$profitRate%")
                }
                Log.d("change", price.toString())
                val stockList = stockListAssignment(imageList, price, stockName, profitRateList)
                val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                recyclerView.adapter = Adapter(stockList)
                recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }


        }
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}

suspend fun sharePrice(url: String, dataType: String): String {
    val price: Int = withContext(Dispatchers.IO) {
        val doc: Document = Jsoup.connect(url).get()
        val todayList = doc.select(".new_totalinfo dl>dd")
        val data = when (dataType) {
            "sharePrice" -> todayList[3].text().split(" ")[1]
            "preSharePrice" -> todayList[4].text().split(" ")[1]
            else -> ""
        }
        data.replace(",", "").toInt()
    }
    return price.toString()
}

fun stockListAssignment(img: List<Int>, price: MutableList<String>, name: List<String>, prePrice: List<String>): ArrayList<Stock> {
    val stockList = ArrayList<Stock>()
    for (i in img.indices)
        stockList.add(Stock(img[i], price[i], name[i], prePrice[i]))
    return stockList
}