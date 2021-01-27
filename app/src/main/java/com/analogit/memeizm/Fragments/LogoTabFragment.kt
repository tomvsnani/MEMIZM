package com.analogit.memeizm.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentLogoTabBinding


class LogoTabFragment : Fragment(R.layout.fragment_logo_tab) {


    lateinit var binding: FragmentLogoTabBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLogoTabBinding.bind(view)

        var v = binding.customview
//        v.setBitmap(BitmapFactory.decodeResource(resources, R.drawable.img))
//        binding.root.requestLayout()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {

        if (this::binding.isInitialized) {
            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                binding.root.width,
                View.MeasureSpec.EXACTLY
            )
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            binding.root.measure(wMeasureSpec, hMeasureSpec)
            binding.root.layoutParams.height = binding.root.measuredHeight
            binding.root.requestLayout()
        }
        super.onResume()

    }


}