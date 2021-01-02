package com.example.memeizm.Models

import androidx.recyclerview.widget.DiffUtil

data class CategoryModelClass(var categoryName:String="", var id:String="",var isSelected:Boolean=false) {
    companion object{
        var diffUtil= object : DiffUtil.ItemCallback<CategoryModelClass>() {
            override fun areItemsTheSame(
                oldItem: CategoryModelClass,
                newItem: CategoryModelClass
            ): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryModelClass,
                newItem: CategoryModelClass
            ): Boolean {
                return oldItem.categoryName==newItem.categoryName
            }
        }
    }
}