package com.analogit.memeizm.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.analogit.memeizm.Fragments.MyCreationsFragment
import com.analogit.memeizm.Fragments.MyDownloadsFragment
import com.analogit.memeizm.Fragments.MyFavouritesFragment
import com.analogit.memeizm.Fragments.SavedTemplateFragment
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.Models.MyTemplatesModelClass
import com.analogit.memeizm.databinding.MyTemplatesRowLayoutBinding

class MyTemplatesAdapter(var activity: Activity?) :
    ListAdapter<MyTemplatesModelClass, MyTemplatesAdapter.MainContentViewHolder>(
        MyTemplatesModelClass.diffUtil
    ) {


    inner class MainContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = MyTemplatesRowLayoutBinding.bind(view)

        init {

//            binding.cardwrap.layoutParams.width=view.resources.displayMetrics.widthPixels/2

            binding.root.setOnClickListener {
                var fragment: Fragment? = null
                when (adapterPosition) {
                    0 -> {
                        fragment = MyCreationsFragment()

                    }

                    1 -> {
                        fragment = MyDownloadsFragment()

                    }

                    2 -> {
                        fragment = MyFavouritesFragment()

                    }


                    3 -> {
                        fragment = SavedTemplateFragment()

                    }


                }
                (activity as? MainActivity)?.loadFragment(fragment!!)

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainContentViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_templates_row_layout, parent, false)
        return MainContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainContentViewHolder, position: Int) {
        var model = currentList[position]

        holder.binding.collectionName.text = model.categoryName
        Glide.with(holder.itemView.context).load(model.imagesrc).placeholder(R.drawable.chimney)
            .into(holder.binding.cardImage)
        Glide.with(holder.itemView.context).load(model.iconImageSrc)

            .into(holder.binding.iconimageview)


    }



}
