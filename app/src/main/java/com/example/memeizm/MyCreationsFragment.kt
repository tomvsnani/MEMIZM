package com.example.memeizm

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.get


class MyCreationsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setUpFragmentsToolbarProperties("My Stuff", true)
        return inflater.inflate(R.layout.fragment_my_creations, container, false)
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