package com.gonyan2ee.stockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.gonyan2ee.stockproject.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val kospi = mutableMapOf<String, Int>()

        CoroutineScope(Dispatchers.Main).launch {
            kospi["Samsung"] = sharePrice("https://finance.naver.com/item/main.naver?code=005930")
            kospi["LGEnergySolution"] = sharePrice("https://finance.naver.com/item/main.naver?code=373220")
            kospi["SKHynix"] = sharePrice("https://finance.naver.com/item/main.naver?code=000660")
            kospi["NAVER"] = sharePrice("https://finance.naver.com/item/main.naver?code=035420")
            kospi["SamsungBiologics"] = sharePrice("https://finance.naver.com/item/main.naver?code=207940")
            kospi["KaKao"] = sharePrice("https://finance.naver.com/item/main.naver?code=035720")
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
            Log.d("kakao", kospi.toString())
            val stockList = arrayListOf(
                Stock(R.drawable.ic_samsung, kospi["Samsung"].toString()),
                Stock(R.drawable.ic_lg_energy_solution, kospi["LGEnergySolution"].toString()),
                Stock(R.drawable.ic_sk_hynix, kospi["SKHynix"].toString()),
                Stock(R.drawable.ic_naver, kospi["NAVER"].toString()),
                Stock(R.drawable.ic_samsung_biologics, kospi["SamsungBiologics"].toString()),
                Stock(R.drawable.ic_kakao, kospi["KaKao"].toString()),
                Stock(R.drawable.ic_hyundai, kospi["Hyundai"].toString()),
                Stock(R.drawable.ic_samsung_sdi, kospi["SamsungSDI"].toString()),
                Stock(R.drawable.ic_lg_chemistry, kospi["LGChemistry"].toString()),
                Stock(R.drawable.ic_kia, kospi["Kia"].toString()),
                Stock(R.drawable.ic_kakao_bank, kospi["KakaoBank"].toString()),
                Stock(R.drawable.ic_celltrion, kospi["Celltrion"].toString()),
                Stock(R.drawable.ic_posco, kospi["POSCO"].toString()),
                Stock(R.drawable.ic_kb, kospi["KBFinancialGroup"].toString()),
                Stock(R.drawable.ic_samsung_cnt, kospi["SamsungCnT"].toString()),
                Stock(R.drawable.ic_lg_electronics, kospi["LGElectronics"].toString()),
                Stock(R.drawable.ic_shinhan_financial_group, kospi["ShinhanHoldings"].toString()),
                Stock(R.drawable.ic_hyundai_mobis, kospi["HyundaiMobis"].toString()),
                Stock(R.drawable.ic_kakao_pay, kospi["KaKaoPay"].toString()),
                Stock(R.drawable.ic_sk_innovation, kospi["SKInnovation"].toString())
            )

            binding.recyclerview.adapter = Adapter(stockList)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}

suspend fun sharePrice(url: String): Int {
    val price: Int = withContext(Dispatchers.IO) {
        val doc: Document = Jsoup.connect(url).get()
        val todayList = doc.select(".new_totalinfo dl>dd")
        val data = todayList[3].text().split(" ")[1]
        data.replace(",", "").toInt()
    }
    return price
}

