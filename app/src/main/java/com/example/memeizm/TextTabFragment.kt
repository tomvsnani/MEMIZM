package com.example.memeizm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeizm.databinding.FragmentTextTabBinding


class TextTabFragment : Fragment(R.layout.fragment_text_tab) {

    lateinit var binding: FragmentTextTabBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTextTabBinding.bind(view)
        var adapter = CustomTextTabRecyclerAdapter()
        binding.customTextRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter =adapter
        }

//        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
//            Log.d("width111", binding.constraint.width.toString())
//            binding.root.apply {
//
//                layoutParams = (layoutParams as FrameLayout.LayoutParams).apply {
//                    var dp = 16
//
//                    setMargins(
//                        convertDpToPixels(dp),
//                        convertDpToPixels(dp),
//                        convertDpToPixels(dp),
//                        convertDpToPixels(dp)
//                    )
//
//
//                }
//            }
//        }
    }

    private fun convertDpToPixels(dp: Int): Int {
        return dp * resources.displayMetrics.density.toInt()
    }

}