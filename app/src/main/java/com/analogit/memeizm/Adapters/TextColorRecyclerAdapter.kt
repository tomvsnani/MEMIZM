package com.analogit.memeizm.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.TextColorRowLayoutBinding

class TextColorRecyclerAdapter(var param: (position:Int)  -> Unit) :
    ListAdapter<String, TextColorRecyclerAdapter.MainContentViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {
    inner class MainContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = TextColorRowLayoutBinding.bind(view)

        init {
            binding.textcolorChnageButton.setOnClickListener {
                param(adapterPosition)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainContentViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_color_row_layout, parent, false)
        return MainContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainContentViewHolder, position: Int) {
        var model = currentList[position]
        holder.binding.textcolorChnageButton.setBackgroundColor(Color.parseColor(model))

    }

}
