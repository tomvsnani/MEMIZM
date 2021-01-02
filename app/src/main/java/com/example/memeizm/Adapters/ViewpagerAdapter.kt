package com.example.memeizm.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memeizm.GallaryTabFragment
import com.example.memeizm.LogoTabFragment
import com.example.memeizm.TextTabFragment

class ViewpagerAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TextTabFragment()
            1 -> return GallaryTabFragment()
            2 -> return LogoTabFragment()
        }
        return TextTabFragment()
    }


}