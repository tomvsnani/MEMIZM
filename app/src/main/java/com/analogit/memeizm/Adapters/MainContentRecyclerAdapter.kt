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

class MainContentRecyclerAdapter(var activity: Activity?) :
    ListAdapter<MainContentModel, MainContentRecyclerAdapter.MainContentViewHolder>(MainContentModel.diff) {
    inner class MainContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = MainContentRecyclerRowLayoutBinding.bind(view)

        init {

            binding.recreateButton.setOnClickListener {


                (activity as MainActivity).apply {

                    insert(currentList[adapterPosition].apply {
                        isInProgressTemplate = true
                    })


                    editingImageBitmap =
                        this@MainContentViewHolder.binding.imageView.drawToBitmap()

                    modelThatIsBeingEdited=currentList[adapterPosition].copy()


                    loadFragment(RecreationFragment())

                }


            }


            binding.favImageView.setOnClickListener {

                (activity as MainActivity).insert(currentList[adapterPosition].apply {
                    isFav=true
                })
            }

            binding.shareImageView.setOnClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_STREAM, "hello")
                    type = "image/*"
                    activity?.startActivity(this)
                }
            }

            binding.saveImageView.setOnClickListener {
                (activity as MainActivity).insert(currentList[adapterPosition].apply {
                    isSaved = true
                })

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
        Glide.with(holder.itemView.context).load(Constants.IMAGE_URL + model.image)
            .into(holder.binding.imageView)


    }

}
