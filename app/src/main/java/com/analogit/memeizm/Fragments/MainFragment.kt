package com.analogit.memeizm.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.MainCategoryAdapter
import com.analogit.memeizm.Adapters.MainContentRecyclerAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.Models.MainCategoryModelClass
import com.analogit.memeizm.Models.MainContentResponseModel
import com.analogit.memeizm.Models.MainResponseModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        (activity as MainActivity).binding.progressbarcard.visibility = View.VISIBLE

        setUpCategoryAdapter()




    }

    private fun setUpMainContentAdapter(id:Int) {
        val adapter = MainContentRecyclerAdapter(activity)
        binding.mainContentRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }

        (activity as MainActivity).apply {
            retrofitInterface.getMainPageTemplates(id.toString())
                .enqueue(object : Callback<MainResponseModelClass> {
                    override fun onResponse(
                        call: Call<MainResponseModelClass>,
                        response: Response<MainResponseModelClass>
                    ) {
                        if (response.isSuccessful) {
                            adapter.submitList(response.body()?.data)
                        } else {
                            Toast.makeText(
                                context,
                                response.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        (activity as? MainActivity)?.binding?.progressbarcard?.visibility =
                            View.GONE
                        Log.d("datareceived", response.toString())
                    }

                    override fun onFailure(call: Call<MainResponseModelClass>, t: Throwable) {
                        (activity as MainActivity).binding.progressbarcard.visibility = View.GONE
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
        (activity as MainActivity).makeProgressVisible()
        val adapter = MainCategoryAdapter(){
//            collectionIdClicked=it
            setUpMainContentAdapter(it)
        }
        binding.mainCategoryRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            this.adapter = adapter
        }


        (activity as MainActivity).retrofitInterface.getHomePageCollections().enqueue(object :
            Callback<MainContentResponseModel> {
            override fun onResponse(
                call: Call<MainContentResponseModel>,
                response: Response<MainContentResponseModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "1") {
                        if(response.body()?.data!!.isNotEmpty())
                        setUpMainContentAdapter(response.body()?.data!![0].collection_id.toInt())
                        adapter.submitList(response.body()?.data!!)
                    }
                    else
                        (activity as MainActivity).showToast(response.body()?.message!!)
                } else
                    (activity as MainActivity).showToast(response.toString())

                (activity as MainActivity).makeProgressInvisible()
            }

            override fun onFailure(call: Call<MainContentResponseModel>, t: Throwable) {

                (activity as MainActivity).apply {
                    makeProgressInvisible()
                    showToast(t.toString())
                }
            }
        })


    }


}