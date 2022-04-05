package com.gonyan2ee.stockproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.talk.TalkApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class MypageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val balance = view.findViewById<TextView>(R.id.balance)
        val firebase = FirebaseFirestore.getInstance()

        val tm = activity?.getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager

        var phone = ""

        requestPermissions(arrayOf(Manifest.permission.READ_PHONE_NUMBERS), PERMISSION_REQUEST_CODE)

        if (ContextCompat.checkSelfPermission(view.context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_DENIED) {


            Toast.makeText(view.context, "수동으로 전화번호 접근 권한을 허용해주세요", Toast.LENGTH_SHORT).show()

        } else
            phone = tm.line1Number

        val docRef = firebase.collection(phone).document("moneyData")
        docRef.get()
            .addOnSuccessListener { document ->
                val money = document.data?.get("money") as Long
                balance.text = "잔액 ${money.toString()}원"
            }

        TalkApiClient.instance.profile { profile, error ->
            if (profile != null) {
                val nickname = view.findViewById<TextView>(R.id.nickname)
                val profileImage = view.findViewById<ImageView>(R.id.profile_image)
                Log.d("nickname", profile.nickname!!)
                nickname.text = profile.nickname
                Log.d("image", profile.thumbnailUrl.toString())

                Glide.with(this)
                    .load(profile.thumbnailUrl)
                    .placeholder(R.drawable.ic_load_error)
                    .error(R.drawable.ic_load_error)
                    .fallback(R.drawable.ic_load_error)
                    .into(profileImage)

            }
        }


    }


}



