package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInstaMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
