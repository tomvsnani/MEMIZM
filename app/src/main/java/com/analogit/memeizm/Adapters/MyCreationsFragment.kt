package com.analogit.memeizm.Fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.MyCreationAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentMyCreationsBinding


class MyCreationsFragment : Fragment(R.layout.fragment_my_creations) {
    lateinit var binding: FragmentMyCreationsBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMyCreationsBinding.bind(view)

        (activity as MainActivity).setUpFragmentsToolbarProperties(
            getString(R.string.MyCreations),
            true,
            resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        )



        setUpMainContentAdapter()

    }

    private fun setUpMainContentAdapter() {
        val adapter = MyCreationAdapter(activity)
        binding.myCreationsContentRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }

        (activity as MainActivity).apply {
            getTemplatesInProgress().observe(
                viewLifecycleOwner
            ) {
                adapter.submitList(it)
                if (it.isEmpty())
                    Toast.makeText(context, "You have not worked on any items ", Toast.LENGTH_SHORT)
                        .show()



                Log.d("insertedtotalllist", it.toString())
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        menu.forEach {
            it.isVisible = false
        }

        super.onPrepareOptionsMenu(menu)
    }


}