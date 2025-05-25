package com.sopp.pactlift

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.sopp.pactlift.ui.CheckInFragment
import com.sopp.pactlift.ui.HabitFragment
import com.sopp.pactlift.ui.RecordsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        
        // 设置ViewPager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        
        // 禁用ViewPager滑动
        viewPager.isUserInputEnabled = false
        
        // 设置底部导航栏
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_habits -> viewPager.currentItem = 0
                R.id.navigation_checkin -> viewPager.currentItem = 1
                R.id.navigation_records -> viewPager.currentItem = 2
            }
            true
        }
        
        // ViewPager页面切换监听
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = when (position) {
                    0 -> R.id.navigation_habits
                    1 -> R.id.navigation_checkin
                    2 -> R.id.navigation_records
                    else -> R.id.navigation_habits
                }
            }
        })
        
        // 设置打卡页面为默认页面
        viewPager.currentItem = 1
        bottomNavigationView.selectedItemId = R.id.navigation_checkin
    }
    
    // ViewPager适配器
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3
        
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HabitFragment()
                1 -> CheckInFragment()
                2 -> RecordsFragment()
                else -> HabitFragment()
            }
        }
    }
} 