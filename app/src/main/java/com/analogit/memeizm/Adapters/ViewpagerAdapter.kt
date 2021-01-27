package com.analogit.memeizm.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.analogit.memeizm.Fragments.RecreationFragment

class ViewpagerAdapter(var fragment: Fragment, var size: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        return (fragment as RecreationFragment).viewpagerFragmentList[position]


    }


}