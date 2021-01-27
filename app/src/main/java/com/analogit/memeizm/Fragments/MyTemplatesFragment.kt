package com.analogit.memeizm.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analogit.memeizm.Adapters.MyTemplatesAdapter
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.Models.MyTemplatesModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.FragmentMyTemplatesBinding


class MyTemplatesFragment : Fragment(R.layout.fragment_my_templates) {
    lateinit var binding: FragmentMyTemplatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMyTemplatesBinding.bind(view)
        (activity as MainActivity).setUpFragmentsToolbarProperties("My Stuff", true)

        binding.mytemplatesRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = MyTemplatesAdapter(activity).apply {
                submitList(
                    listOf(
                        MyTemplatesModelClass(
                            "My Creations",
                            "1",
                            iconImageSrc =R.drawable.ic_baseline_edit
                        ),
                        MyTemplatesModelClass(
                            "My Downloads",
                            "2",
                            iconImageSrc =R.drawable.black_download_icon
                        ),
                        MyTemplatesModelClass(
                            "My Favourites",
                            "3",
                            iconImageSrc =R.drawable.ic_baseline_edit
                        ),
                        MyTemplatesModelClass(
                            "Saved",
                            "4",
                            iconImageSrc = R.drawable.black_download_icon
                        )

                    )
                )
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        for (i in 0 until menu.size())
            menu[i].isVisible = false
        menu.add("ok").apply {
            icon = resources.getDrawable(R.drawable.ic_baseline_settings_24)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        super.onPrepareOptionsMenu(menu)
    }

}