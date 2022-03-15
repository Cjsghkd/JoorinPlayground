package com.gonyan2ee.stockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.concurrent.thread

class Trading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trading)

        val kospi = mutableMapOf<String, Int>()

        kospi["Samsung"] = sharePrice("https://finance.naver.com/item/main.naver?code=005930")
        kospi["LGEnergySolution"] =
            sharePrice("https://finance.naver.com/item/main.naver?code=373220")
        kospi["SKHynix"] = sharePrice("https://finance.naver.com/item/main.naver?code=000660")
        kospi["NAVER"] = sharePrice("https://finance.naver.com/item/main.naver?code=035420")
        kospi["SamsungBiologics"] =
            sharePrice("https://finance.naver.com/item/main.naver?code=207940")
        kospi["KaKao"] = sharePrice("https://finance.naver.com/item/main.naver?code=035720")
        kospi["Hyundai"] = sharePrice("https://finance.naver.com/item/main.naver?code=005380")
        kospi["SamsungSDI"] = sharePrice("https://finance.naver.com/item/main.naver?code=006400")
        kospi["LGChemistry"] = sharePrice("https://finance.naver.com/item/main.naver?code=051910")
        kospi["Kia"] = sharePrice("https://finance.naver.com/item/main.naver?code=000270")
        kospi["KakaoBank"] = sharePrice("https://finance.naver.com/item/main.naver?code=323410")
        kospi["Celltrion"] = sharePrice("https://finance.naver.com/item/main.naver?code=068270")
        kospi["POSCO"] = sharePrice("https://finance.naver.com/item/main.naver?code=005490")
        kospi["KBFinancialGroup"] =
            sharePrice("https://finance.naver.com/item/main.naver?code=105560")
        kospi["SamsungCnT"] = sharePrice("https://finance.naver.com/item/main.naver?code=028260")
        kospi["LGElectronics"] = sharePrice("https://finance.naver.com/item/main.naver?code=066570")
        kospi["ShinhanHoldings"] =
            sharePrice("https://finance.naver.com/item/main.naver?code=055550")
        kospi["HyundaiMobis"] = sharePrice("https://finance.naver.com/item/main.naver?code=012330")
        kospi["KaKaoPay"] = sharePrice("https://finance.naver.com/item/main.naver?code=377300")
        kospi["SKInnovation"] = sharePrice("https://finance.naver.com/item/main.naver?code=096770")

        Log.d("kospi", kospi.toString())
    }
}

fun sharePrice(url: String): Int {

    val doc: Document = Jsoup.connect(url).get()
    val todayList = doc.select(".new_totalinfo dl>dd")
    val price = todayList[3].text().split(" ")[1].toInt()
    return price
}


//    val price: Int = GlobalScope.async(Dispatchers.IO) {
//        val doc: Document = Jsoup.connect(url).get()
//        val todayList = doc.select(".new_totalinfo dl>dd")
//        todayList[3].text().split(" ")[1].toInt()
//    }.await()
//    return price

//    var price : Int
//    thread(start = true) {
//        val doc: Document = Jsoup.connect(url).get()
//        val todayList = doc.select(".new_totalinfo dl>dd")
//        price = todayList[3].text().split(" ")[1].toInt()
//
//    }
//
//    fun getSharePrice() : Int {
//        return price
//    }




