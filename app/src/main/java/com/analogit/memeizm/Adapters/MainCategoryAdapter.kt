package com.analogit.memeizm.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Models.MainCategoryModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.CategoriesRowLayoutBinding

class MainCategoryAdapter(var collectionClickedCallback: (Int) -> Unit) :
    ListAdapter<MainCategoryModelClass, MainCategoryAdapter.MainCategoryViewHolder>(
        MainCategoryModelClass.diffUtil
    ) {
    var selectedPosition = 0

    inner class MainCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CategoriesRowLayoutBinding.bind(view)


        init {
            view.layoutParams.width =
                view.context.resources.displayMetrics.widthPixels / (currentList.size)

            binding.categoryRowTextview.setOnClickListener {
                collectionClickedCallback(currentList[adapterPosition].collection_id.toInt())
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_row_layout, parent, false)
        return MainCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        var model = currentList[position]

        holder.binding.categoryRowTextview.text = model.collection_name
        if (selectedPosition == position) {
            holder.binding.categoryRowTextview.apply {
                setBackgroundColor(Color.BLACK)
                setTextColor(Color.WHITE)
            }
        } else {
            holder.binding.categoryRowTextview.apply {
                setBackgroundColor(Color.WHITE)
                setTextColor(Color.BLACK)
            }
        }
    }
}