package com.example.instagram

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.Fragment.FeedFragment
import com.example.instagram.Fragment.PostFragment
import com.example.instagram.Fragment.ProfileFragment
import com.example.instagram.databinding.ActivityInstaMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InstaMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityInstaMainBinding

    // 추가할 권한들, Manifest에도 추가 필요
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    // 권한 요청
    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    Toast.makeText(applicationContext, "${it.key} 권한 허용 필요", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
            main()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInstaMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestMultiplePermission.launch(permissionList)
    }

    fun main() {
        val tabs = binding.mainTab
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_home))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_post))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_my))

        val pager = binding.mainPager
        pager.adapter = InstaMainPagerAdapter(this, 3)

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        TabLayoutMediator(tabs, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.btn_outsta_home)
                }
                1 -> {
                    tab.setIcon(R.drawable.btn_outsta_post)
                }
                else -> {
                    tab.setIcon(R.drawable.btn_outsta_my)
                }
            }
        }.attach()
    }
}

class InstaMainPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount: Int
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FeedFragment()
            1 -> return PostFragment()
            else -> return ProfileFragment()
        }
    }
}