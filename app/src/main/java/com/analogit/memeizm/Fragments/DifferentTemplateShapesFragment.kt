package com.analogit.memeizm.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.DifferentShapesRecyclerAdapter
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentDifferentTemplateShapesBinding


class DifferentTemplateShapesFragment : Fragment(R.layout.fragment_different_template_shapes) {
    lateinit var binding: FragmentDifferentTemplateShapesBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDifferentTemplateShapesBinding.bind(view)
        binding.recycler.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = DifferentShapesRecyclerAdapter(requireActivity())

        }


        super.onViewCreated(view, savedInstanceState)
    }




}