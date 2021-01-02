package com.example.memeizm.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memeizm.MainActivity
import com.example.memeizm.Models.MainContentModel
import com.example.memeizm.R
import com.example.memeizm.RecreationFragment
import com.example.memeizm.databinding.MainContentRecyclerRowLayoutBinding

class MainContentRecyclerAdapter(var activity: Activity?) :
    ListAdapter<MainContentModel, MainContentRecyclerAdapter.MainContentViewHolder>(MainContentModel.diff) {
    inner class MainContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = MainContentRecyclerRowLayoutBinding.bind(view)

        init {

            binding.recreateButton.setOnClickListener {
                (activity as MainActivity).loadFragment(RecreationFragment())
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainContentViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_content_recycler_row_layout, parent, false)
        return MainContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainContentViewHolder, position: Int) {
   //     var model = currentList[position]


    }

    override fun getItemCount(): Int {
        return 3
    }
}
