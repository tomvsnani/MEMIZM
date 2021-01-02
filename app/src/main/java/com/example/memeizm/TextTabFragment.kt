package com.example.memeizm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memeizm.databinding.FragmentTextTabBinding


class TextTabFragment : Fragment(R.layout.fragment_text_tab) {

    lateinit var binding: FragmentTextTabBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentTextTabBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)
    }

}