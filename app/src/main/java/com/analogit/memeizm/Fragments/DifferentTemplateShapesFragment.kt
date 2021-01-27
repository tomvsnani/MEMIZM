package com.analogit.memeizm.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentDifferentTemplateShapesBinding


class DifferentTemplateShapesFragment : Fragment(R.layout.fragment_different_template_shapes) {
   lateinit var binding:FragmentDifferentTemplateShapesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentDifferentTemplateShapesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }



}