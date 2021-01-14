package com.example.memeizm.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memeizm.GallaryTabFragment
import com.example.memeizm.LogoTabFragment
import com.example.memeizm.RecreationFragment
import com.example.memeizm.TextTabFragment

class ViewpagerAdapter(var fragment: Fragment, var size: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return (fragment as RecreationFragment).viewpagerFragmentList[position]


    }


}