package com.gonyan2ee.stockproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gonyan2ee.stockproject.databinding.ActivityLoginBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

val moneyData = hashMapOf(
    "money" to 1000000
)

const val PERMISSION_REQUEST_CODE = 1001

class Login : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stockName = listOf(
            "삼성전자", "LG에너지솔루션", "SK하이닉스", "NAVER", "삼성바이오로직스", "카카오", "현대차", "삼성SDI", "LG화학", "기아",
            "카카오뱅크", "셀트리온", "POSCO홀딩스", "KB금융", "삼성물산", "LG전자", "신한지주", "현대모비스", "카카오페이", "SK이노베이션"
        )

        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        var phone = "default"

        requestPermissions(arrayOf(Manifest.permission.READ_PHONE_NUMBERS), PERMISSION_REQUEST_CODE)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_NUMBERS), PERMISSION_REQUEST_CODE)
            Toast.makeText(this, "전화번호 접근 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
        } else
            phone = tm.line1Number

        UserApiClient.instance.accessTokenInfo { tokenInfo, _ ->
            if (tokenInfo != null) {
                stockDataSetting(phone, stockName)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
//                    }
//                    else -> { // Unknown
//                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else if (token != null) {
//                defaultMoneySetting(phone)
//                stockDataSetting(phone, stockName)
//                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
//            }
//        }

//        binding.btnLogin.setOnClickListener {
//            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
//                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
//            } else {
//                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//            }
//        }
    }
}

fun defaultMoneySetting(phone: String) {
    val firebase = FirebaseFirestore.getInstance()

    firebase.collection(phone)
        .document("moneyData")
        .set(moneyData)
}

fun stockDataSetting(phone: String, stockName: List<String>) {
    val firebase = FirebaseFirestore.getInstance()

    val stockData = hashMapOf(
        "구매수량" to 0,
        "주문가격" to 0
    )

    for (i in stockName.indices) {
        firebase.collection(phone)
            .document(stockName[i])
            .set(stockData)
    }


}