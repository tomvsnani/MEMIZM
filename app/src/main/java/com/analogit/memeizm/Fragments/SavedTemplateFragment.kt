package com.analogit.memeizm.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.SavedTemplateAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentSavedTemplateBinding


class SavedTemplateFragment : Fragment(R.layout.fragment_saved_template) {
    lateinit var binding: FragmentSavedTemplateBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedTemplateBinding.bind(view)

        (activity as MainActivity).setUpFragmentsToolbarProperties(
            getString(R.string.savedTemplateToolbar),
            false,
            resources.getDrawable(R.drawable.ic_baseline_format_align_right_24)
        )



        setUpMainContentAdapter()

    }

    private fun setUpMainContentAdapter() {
        val adapter = SavedTemplateAdapter(activity)
        binding.savedContentRecycler.apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }

        (activity as MainActivity).apply {
            getSavedItems().observe(
                viewLifecycleOwner
            ) {
                adapter.submitList(it)
                if(it.isEmpty())
                    Toast.makeText(context,"Sorry You have no saved items ",Toast.LENGTH_SHORT).show()



                Log.d("insertedtotalllist",it.toString())
            }
        }

    }


}