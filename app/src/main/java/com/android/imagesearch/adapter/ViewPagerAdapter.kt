package com.android.imagesearch.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.imagesearch.MyItemsFragment
import com.android.imagesearch.SearchFragment

class ViewPagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    val fragments = listOf<Fragment>(SearchFragment(),MyItemsFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {



        return fragments[position]
    }
}