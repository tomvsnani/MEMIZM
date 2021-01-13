package com.example.memeizm

import androidx.recyclerview.widget.DiffUtil

data class CustomTextModelClass(
    var isActive: Boolean,
    var text: Boolean,
    var id: String,
    var textColor: String,
    var textStyle: String,
    var textAlignment: String,
    var textSize: String
) {
    companion object {
        var diff = object : DiffUtil.ItemCallback<CustomTextModelClass>() {
            override fun areItemsTheSame(
                oldItem: CustomTextModelClass,
                newItem: CustomTextModelClass
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CustomTextModelClass,
                newItem: CustomTextModelClass
            ): Boolean {
                return oldItem.text == newItem.text && oldItem.textColor == newItem.textColor && oldItem.textSize == newItem.textSize
                        && oldItem.isActive == newItem.isActive
            }
        }
    }

}
