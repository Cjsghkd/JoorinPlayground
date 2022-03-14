package com.gonyan2ee.stockproject

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "c8e2badce72d706a4eb1f526a7e4c2f7")
    }
}