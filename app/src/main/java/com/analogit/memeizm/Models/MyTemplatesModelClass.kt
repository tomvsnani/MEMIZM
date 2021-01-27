package com.analogit.memeizm.Models

import androidx.recyclerview.widget.DiffUtil

data class MyTemplatesModelClass(
    var categoryName: String = "",
    var id: String = "",
    var isSelected: Boolean = false,
    var imagesrc: String="",
    var iconImageSrc:Int?=null
) {
    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<MyTemplatesModelClass>() {
            override fun areItemsTheSame(
                oldItem: MyTemplatesModelClass,
                newItem: MyTemplatesModelClass
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MyTemplatesModelClass,
                newItem: MyTemplatesModelClass
            ): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }
        }
    }
}