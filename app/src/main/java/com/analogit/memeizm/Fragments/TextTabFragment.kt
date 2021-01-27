package com.analogit.memeizm.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.CustomTextModelClass
import com.analogit.memeizm.CustomTextTabRecyclerAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentTextTabBinding


class TextTabFragment : Fragment(R.layout.fragment_text_tab) {

    lateinit var binding: FragmentTextTabBinding
    lateinit var adapter: CustomTextTabRecyclerAdapter
    var list: MutableList<CustomTextModelClass>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding = FragmentTextTabBinding.bind(view)
        adapter = CustomTextTabRecyclerAdapter()
        adapter.setTextChangedCallback((activity as MainActivity).textChangeCallback)
        list = mutableListOf(
            CustomTextModelClass(true, id = "1"),
            CustomTextModelClass(false, id = "2"),
        )
        binding.customTextRecyclerview.apply {
            adapter = this@TextTabFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }

        adapter.submitList(
            list
        )


        binding.addTextImageView.setOnClickListener {
            (activity as MainActivity).binding.progressbarcard.visibility = View.VISIBLE

            list!!.add(CustomTextModelClass(id = (list!!.size + 1).toString()))

            adapter.submitList(list)
            requestLayout()


        }


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        requestLayout()
        super.onResume()

    }

    private fun requestLayout() {
        if (this::binding.isInitialized) {
            val wMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                binding.customTextRecyclerview.width,
                View.MeasureSpec.EXACTLY
            )
            val hMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
            binding.customTextRecyclerview.measure(wMeasureSpec, hMeasureSpec)
            binding.customTextRecyclerview.layoutParams.height =
                binding.customTextRecyclerview.measuredHeight
//            + (binding.customTextRecyclerview[0].height * (adapter.currentList.size - 1))

            binding.customTextRecyclerview.requestLayout()
            (activity as? MainActivity)?.binding?.progressbarcard?.visibility = View.GONE
        }
    }

    private fun convertDpToPixels(dp: Int): Int {
        return dp * resources.displayMetrics.density.toInt()
    }

}