package com.example.instagram

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JoinActivity : AppCompatActivity() {

    var username: String = ""
    var password: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        findViewById<EditText>(R.id.id_input).doAfterTextChanged {
            username = it.toString()
        }

        findViewById<EditText>(R.id.pw_input).doAfterTextChanged {
            password = it.toString()
        }

        findViewById<TextView>(R.id.login_btn).setOnClickListener {
            val user = HashMap<String, Any>()
            user.put("username", username)
            user.put("password", password)
            retrofitService.instaLogin(user).enqueue(object : Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if(response.isSuccessful){
                        val token: Token = response.body()!!
                    }
                    else{
                        Toast.makeText(this@JoinActivity, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {

                }
            })
        }
    }
}