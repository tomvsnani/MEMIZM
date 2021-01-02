package com.example.memeizm.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memeizm.Models.CategoryModelClass
import com.example.memeizm.R
import com.example.memeizm.databinding.CategoriesRowLayoutBinding

class MainCategoryAdapter :
    ListAdapter<CategoryModelClass, MainCategoryAdapter.MainCategoryViewHolder>(CategoryModelClass.diffUtil) {
    inner class MainCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = CategoriesRowLayoutBinding.bind(view)

        init {
            view.layoutParams.width =
                view.context.resources.displayMetrics.widthPixels / (currentList.size )

            view.setOnClickListener {

                currentList.map {
                    it.isSelected=false
                    if(it==currentList[adapterPosition])
                        it.isSelected=true


                }
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
        holder.binding.categoryRowTextview.text = model.categoryName
        if (model.isSelected) {
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