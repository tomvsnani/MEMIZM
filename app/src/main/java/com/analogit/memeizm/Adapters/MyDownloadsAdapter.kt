package com.analogit.memeizm.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.analogit.memeizm.Constants
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.Models.MainContentModel
import com.analogit.memeizm.R
import com.analogit.memeizm.Fragments.RecreationFragment
import com.analogit.memeizm.databinding.MainContentRecyclerRowLayoutBinding

class MyDownloadsAdapter(var activity: Activity?) :
    ListAdapter<MainContentModel, MyDownloadsAdapter.MainContentViewHolder>(MainContentModel.diff) {
    inner class MainContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = MainContentRecyclerRowLayoutBinding.bind(view)

        init {

            binding.recreateButton.setOnClickListener {


                (activity as MainActivity).insert(currentList[adapterPosition].apply {
                    isInProgressTemplate = true
                })


                (activity as MainActivity).editingImageBitmap =
                    binding.imageView.drawToBitmap()

                (activity as MainActivity).loadFragment(RecreationFragment())
            }

            binding.shareImageView.setOnClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_STREAM, "hello")
                    type = "image/*"
                    activity?.startActivity(this)
                }
            }




            binding.favImageView.setOnClickListener {

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainContentViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_content_recycler_row_layout, parent, false)
        return MainContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainContentViewHolder, position: Int) {
        var model = currentList[position]


        Glide.with(holder.itemView.context).load(Constants.IMAGE_URL + model.images_path)
            .into(holder.binding.imageView)


    }

}
