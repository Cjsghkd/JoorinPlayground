package com.gonyan2ee.stockproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Trading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trading)

        val kospi = mutableMapOf<String, Int>()

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                kospi["Samsung"] = sharePrice("https://finance.naver.com/item/main.naver?code=005930")
                kospi["LGEnergySolution"] = sharePrice("https://finance.naver.com/item/main.naver?code=373220")
                kospi["SKHynix"] = sharePrice("https://finance.naver.com/item/main.naver?code=000660")
                kospi["NAVER"] = sharePrice("https://finance.naver.com/item/main.naver?code=035420")
                kospi["SamsungBiologics"] = sharePrice("https://finance.naver.com/item/main.naver?code=207940")
                kospi["Kakao"] = sharePrice("https://finance.naver.com/item/main.naver?code=035720")
                kospi["Hyundai"] = sharePrice("https://finance.naver.com/item/main.naver?code=005380")
                kospi["SamsungSDI"] = sharePrice("https://finance.naver.com/item/main.naver?code=006400")
                kospi["LGChemistry"] = sharePrice("https://finance.naver.com/item/main.naver?code=051910")
                kospi["Kia"] = sharePrice("https://finance.naver.com/item/main.naver?code=000270")
                kospi["KakaoBank"] = sharePrice("https://finance.naver.com/item/main.naver?code=323410")
                kospi["Celltrion"] = sharePrice("https://finance.naver.com/item/main.naver?code=068270")
                kospi["POSCO"] = sharePrice("https://finance.naver.com/item/main.naver?code=005490")
                kospi["KBFinancialGroup"] = sharePrice("https://finance.naver.com/item/main.naver?code=105560")
                kospi["SamsungCnT"] = sharePrice("https://finance.naver.com/item/main.naver?code=028260")
                kospi["LGElectronics"] = sharePrice("https://finance.naver.com/item/main.naver?code=066570")
                kospi["ShinhanHoldings"] = sharePrice("https://finance.naver.com/item/main.naver?code=055550")
                kospi["HyundaiMobis"] = sharePrice("https://finance.naver.com/item/main.naver?code=012330")
                kospi["KaKaoPay"] = sharePrice("https://finance.naver.com/item/main.naver?code=377300")
                kospi["SKInnovation"] = sharePrice("https://finance.naver.com/item/main.naver?code=096770")
            }
        }
    }

    private suspend fun sharePrice(url: String): Int {
        val price: Int = withContext(Dispatchers.IO) {
            val doc: Document = Jsoup.connect(url).get()
            val todayList = doc.select(".new_totalinfo dl>dd")
            val data = todayList[3].text().split(" ")[1]
            data.replace(",", "").toInt()
        }
        return price
    }
}




