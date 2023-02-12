package com.example.instagram

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "empty")

        when (token) {
            "empty" -> {
                // 로그인이 되어있지 않은 경우
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else -> {
                // 로그인이 되어있는 경우
                val intent = Intent(this, InstaMainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}