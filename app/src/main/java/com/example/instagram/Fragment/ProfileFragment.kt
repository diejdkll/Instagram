package com.example.instagram.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.instagram.ChangeProfileActivity
import com.example.instagram.InstaMainActivity
import com.example.instagram.RetrofitService
import com.example.instagram.UserInfo
import com.example.instagram.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeImg.setOnClickListener {
            startActivity(Intent(activity as InstaMainActivity, ChangeProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val glide = Glide.with(activity as InstaMainActivity)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        val header = HashMap<String, String>()
        val sp = (activity as InstaMainActivity).getSharedPreferences(
            "user_info",
            Context.MODE_PRIVATE
        )
        val token = sp.getString("token", "")
        header.put("Authorization", "token " + token!!)

        retrofitService.getUserInfo(header).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    val userInfo: UserInfo = response.body()!!
                    userInfo.profile.image?.let {
                        // profile image 받아오기
                        glide.load(it).into(binding.profileImg)
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
            }
        })
    }
}