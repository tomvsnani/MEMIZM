package com.example.memeizm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memeizm.databinding.CustomTextTabRowLayoutBinding

class CustomTextTabRecyclerAdapter :
    ListAdapter<CustomTextModelClass, CustomTextTabRecyclerAdapter.CustomTextViewHolder>(
        CustomTextModelClass.diff
    ) {
    lateinit var context: Context

    inner class CustomTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var binding = CustomTextTabRowLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomTextViewHolder {
        context = parent.context
        return CustomTextViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_text_tab_row_layout, parent, false)
        )

    }

    override fun onBindViewHolder(holder: CustomTextViewHolder, position: Int) {
//        var model = currentList[position]
//        if (model.isActive)
//            holder.binding.isViewActiveImageView.setImageDrawable(
//                ResourcesCompat.getDrawable(
//                    context.resources, R.drawable.ic_baseline_visibility_24, null
//                )
//            )
//        else
//            holder.binding.isViewActiveImageView.setImageDrawable(
//                ResourcesCompat.getDrawable(
//                    context.resources, R.drawable.ic_baseline_visibility_off_24, null
//                )
//            )
//

    }

    override fun getItemCount(): Int {
        return 10
    }
}