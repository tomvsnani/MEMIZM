package com.example.memeizm.Models

import androidx.recyclerview.widget.DiffUtil

data class MainContentModel(
    var id: String = "",
    var imgSrc: String = "",
    var isFav: Boolean = false,
    var shareCount: String = "",
    var name: String = ""
) {
    companion object {
        var diff = object : DiffUtil.ItemCallback<MainContentModel>() {
            override fun areItemsTheSame(
                oldItem: MainContentModel,
                newItem: MainContentModel
            ): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(
                oldItem: MainContentModel,
                newItem: MainContentModel
            ): Boolean {
                return oldItem.imgSrc == newItem.imgSrc
            }
        }
    }
}