package com.analogit.memeizm.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentGallaryTabBinding

class GallaryTabFragment : Fragment(R.layout.fragment_gallary_tab) {
lateinit var binding:FragmentGallaryTabBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentGallaryTabBinding.bind(view)
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
            binding.root.measure(wMeasureSpec,hMeasureSpec)
            binding.root.layoutParams.height= binding.root.measuredHeight
            binding.root.requestLayout()
        }
        super.onResume()

    }

}