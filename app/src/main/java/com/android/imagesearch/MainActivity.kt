package com.android.imagesearch

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.android.imagesearch.adapter.ViewPagerAdapter
import com.android.imagesearch.data.MyItems
import com.android.imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val tabTextList = listOf("검색","내 보관함")
    private val tabIconList = listOf(R.drawable.search,R.drawable.youtube)

    var itemBox : ArrayList<MyItems> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        val viewPager = binding.viewpager
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tablayout,binding.viewpager) {tab, position ->
            tab.text = tabTextList[position]
            tab.setIcon(tabIconList[position])
        }.attach()

    }

    //좋아요 기능
    fun like(item : MyItems) {
        if (!itemBox.contains(item)){
            itemBox.add(item)
        }
    }
    //뒤로가기 구현해보기?
//    interface OnBackPressedListener {
//        fun onBackPressed()
//    }
//
//    override fun onBackPressed() {
//        val fragmentList = supportFragmentManager.fragments
//            for (fragment in fragmentList) {
//                if (fragment is OnBackPressedListener) {
//                    (fragment as OnBackPressedListener).onBackPressed()
//                    return
//                }
//            }
//    }

}
