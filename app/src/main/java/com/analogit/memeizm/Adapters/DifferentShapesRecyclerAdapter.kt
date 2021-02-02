package com.analogit.memeizm.Adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Fragments.DifferentShapesRecreationFragment
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.DifferentShapesTemplatesRowLayoutBinding
import com.bumptech.glide.Glide

class DifferentShapesRecyclerAdapter(var requireActivity: FragmentActivity) :
    ListAdapter<String, DifferentShapesRecyclerAdapter.MainCategoryViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {


    inner class MainCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = DifferentShapesTemplatesRowLayoutBinding.bind(view)

        init {

            binding.root.setOnClickListener {
                (requireActivity as MainActivity).loadFragment(DifferentShapesRecreationFragment().apply {  arguments=
                    Bundle().apply {
                        putString("num",adapterPosition.toString())
                    }
                }
                )
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.different_shapes_templates_row_layout, parent, false)
        return MainCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {

        Glide.with(holder.binding.root.context).load(
            ResourcesCompat.getDrawable(
                holder.binding.root.context.resources,
                R.drawable.chimney, null
            )
        ).placeholder(
            ResourcesCompat.getDrawable(
                holder.binding.root.context.resources,
                R.drawable.chimney, null
            )
        ).into(holder.binding.imageview)

        Log.d("ressponn", "okkk")
    }

    override fun getItemCount(): Int {
        return  (requireActivity as MainActivity).differentTemplateShapesList.size
    }
}