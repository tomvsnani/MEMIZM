package com.analogit.memeizm

import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil

data class CustomTextModelClass(
    var isActive: Boolean=true,
    var text: String="Enter text here",
    var id: String,
    var textColor: String= Color.WHITE.toString(),
    var textStyle: String="bold",
    var textAlignment: String="start",
    var textSize: String="24sp"
) {
    companion object {
        var diff = object : DiffUtil.ItemCallback<CustomTextModelClass>() {
            override fun areItemsTheSame(
                oldItem: CustomTextModelClass,
                newItem: CustomTextModelClass
            ): Boolean {
                return oldItem==newItem
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
