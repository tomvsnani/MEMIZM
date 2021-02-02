package com.analogit.memeizm.Models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainContentModel(
    @PrimaryKey
    var id: String = "",
    var images_path: String? = "",
    var isFav: Boolean? = false,
    var shareCount: String? = "",
    var tag_title: String? = "",
    var isInProgressTemplate:Boolean?=false,
    var isInMyDownloads:Boolean?=false,
    var isSaved:Boolean?=false,

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
                return oldItem.images_path == newItem.images_path
            }
        }
    }
}