package com.analogit.memeizm.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.SearchFragmentCategoryAdapter
import com.analogit.memeizm.Adapters.SearchFragmentMainContentAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.Models.MainCategoryModelClass
import com.analogit.memeizm.Models.MainResponseModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        (activity as MainActivity).setUpFragmentsToolbarProperties(
            getString(R.string.app_name),
            true,
            resources.getDrawable(R.drawable.ic_baseline_format_align_right_24)
        )

        (activity as MainActivity).setSupportActionBar(null)
        (activity as MainActivity).binding.maintoolbar.visibility = View.GONE
        (activity as MainActivity).binding.progressbarcard.visibility=View.VISIBLE

        setUpCategoryAdapter()

        setUpMainContentAdapter()


    }

    private fun setUpMainContentAdapter() {
        val adapter = SearchFragmentMainContentAdapter(activity)
        binding.searchRcyclerRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }

        (activity as MainActivity).apply {
            retrofitInterface.getSearchPageTemplates()
                .enqueue(object : Callback<MainResponseModelClass> {
                    override fun onResponse(
                        call: Call<MainResponseModelClass>,
                        response: Response<MainResponseModelClass>
                    ) {
                        if (response.isSuccessful) {
                            adapter.submitList(response.body()?.data)
                        } else {
                            (activity as MainActivity).binding.progressbarcard.visibility=View.GONE
                            Toast.makeText(
                                context,
                                response.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        Log.d("datareceived", response.toString())
                        (activity as MainActivity).binding.progressbarcard.visibility=View.GONE
                    }

                    override fun onFailure(call: Call<MainResponseModelClass>, t: Throwable) {
                        (activity as MainActivity).binding.progressbarcard.visibility=View.GONE
                        Toast.makeText(
                            context,
                            "Sorry there is some problem in fetching templates",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("datareceived", t.toString())
                    }
                })
        }

    }

    private fun setUpCategoryAdapter() {
        val adapter = SearchFragmentCategoryAdapter()
        binding.searchCategoryRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.adapter = adapter
        }

        var list = listOf<MainCategoryModelClass>(
            MainCategoryModelClass("Today", "1", isSelected = true),
            MainCategoryModelClass("Trending", "2"), MainCategoryModelClass("This Week", "3"),
            MainCategoryModelClass("Popular", "4")
        )

        adapter.submitList(list)
    }

    override fun onStop() {
        (activity as MainActivity).setSupportActionBar((activity as MainActivity).binding.maintoolbar.apply {
            visibility = View.VISIBLE
        })

        super.onStop()
    }


}