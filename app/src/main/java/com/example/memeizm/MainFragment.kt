package com.example.memeizm

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeizm.Adapters.MainCategoryAdapter
import com.example.memeizm.Adapters.MainContentRecyclerAdapter
import com.example.memeizm.Models.CategoryModelClass
import com.example.memeizm.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    lateinit var binding: FragmentMainBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        (activity as MainActivity).setUpFragmentsToolbarProperties(
            getString(R.string.app_name),
            false,
            resources.getDrawable(R.drawable.ic_baseline_format_align_right_24)
        )
        setUpCategoryAdapter()

        setUpMainContentAdapter()


    }

    private fun setUpMainContentAdapter() {
        val adapter = MainContentRecyclerAdapter(activity)
        binding.mainContentRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

    private fun setUpCategoryAdapter() {
        val adapter = MainCategoryAdapter()
        binding.mainCategoryRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.adapter = adapter
        }

        var list = listOf<CategoryModelClass>(
            CategoryModelClass("Today", "1", isSelected = true),
            CategoryModelClass("Trending", "2"), CategoryModelClass("This Week", "3"),
            CategoryModelClass("Popular", "4")
        )

        adapter.submitList(list)
    }


}