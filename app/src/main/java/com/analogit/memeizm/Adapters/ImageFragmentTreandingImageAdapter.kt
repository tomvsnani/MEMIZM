package com.analogit.memeizm.Adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Models.MainCategoryModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.CategoriesRowLayoutBinding
import com.analogit.memeizm.databinding.ImageFragmentImageRowLayoutBinding
import com.bumptech.glide.Glide

class ImageFragmentTreandingImageAdapter :
    ListAdapter<String, ImageFragmentTreandingImageAdapter.MainCategoryViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {


    inner class MainCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ImageFragmentImageRowLayoutBinding.bind(view)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_fragment_image_row_layout, parent, false)
        return MainCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
//        var model = currentList[position]
//
        Glide.with(holder.binding.root.context).load(  ResourcesCompat.getDrawable(
            holder.binding.root.context.resources,
            R.drawable.chimney
            ,null)).placeholder(
            ResourcesCompat.getDrawable(
                holder.binding.root.context.resources,
                R.drawable.chimney
            ,null)
        ).into(holder.binding.trendingImagesImageView)

        Log.d("ressponn","okkk")
    }

    override fun getItemCount(): Int {
        return 6
    }
}