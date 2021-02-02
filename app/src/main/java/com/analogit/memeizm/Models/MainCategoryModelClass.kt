package com.analogit.memeizm.Models

import androidx.recyclerview.widget.DiffUtil

data class MainCategoryModelClass(
    var collection_id: String = "",
    var id: String = "",
    var isSelected: Boolean = false,
    var collection_name: String
) {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<MainCategoryModelClass>() {
            override fun areItemsTheSame(
                oldItem: MainCategoryModelClass,
                newItem: MainCategoryModelClass
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MainCategoryModelClass,
                newItem: MainCategoryModelClass
            ): Boolean {
                return oldItem.collection_id == newItem.collection_id
            }
        }
    }
}