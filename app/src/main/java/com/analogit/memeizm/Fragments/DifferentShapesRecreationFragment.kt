package com.analogit.memeizm.Fragments

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.core.view.forEach
import androidx.viewbinding.ViewBinding
import com.analogit.memeizm.Constants
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.CustomizableShape1Binding
import com.analogit.memeizm.databinding.FragmentDifferentShapesRecreationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.HashMap

class DifferentShapesRecreationFragment : Fragment() {
    lateinit var clickedView: CustomConstraintLayout
    lateinit var imageIntent: ActivityResultLauncher<String>
    lateinit var binding: ViewBinding
    lateinit var clicklistenerList: HashMap<View, View.OnClickListener>
    var isAtleastOneImageSelected=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        var view: View? = null
        clicklistenerList = HashMap()



        view =
            LayoutInflater.from(context)
                .inflate(
                    (activity as MainActivity).differentTemplateShapesList[(arguments?.getString(
                        "num",
                        "0"
                    )!!.toInt())], container, false
                )

        binding = CustomizableShape1Binding.bind(view!!)

//       binding.customConstraintLayout1.requestDisallowInterceptTouchEvent(true)

        for (i in (binding.root as ConstraintLayout).children) {
            if (i is CustomConstraintLayout) {
                i.setLayoutType("1")
                var clickListener = clickListenerForViews()
//                clicklistenerList[i.getChildAt(0)] = clickListener
                i.getChildAt(0).setOnClickListener(clickListener)

            }

        }

        return view
    }

    private fun clickListenerForViews() = View.OnClickListener {

        clickedView = it.parent as CustomConstraintLayout
        imageIntent.launch("image/*")
        isAtleastOneImageSelected=true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        imageIntent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null && it.toString().isNotEmpty()) {
                (clickedView as CustomConstraintLayout).generateViewDynamically(
                    Constants.TYPE_IMAGE,
                    it.toString()
                )

            }

        }
        super.onCreate(savedInstanceState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 0) {

            binding.root.apply {
                with(  (activity as MainActivity)){
                    if(isAtleastOneImageSelected)
                    saveBitmapToMemory()
                    else
                        Toast.makeText(requireContext(),"Please select atleast one image",Toast.LENGTH_SHORT).show()
                }
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.forEach { item ->
            item.isVisible = false
        }
        menu.add(0, 0, 1, "download").apply {
            icon = ResourcesCompat.getDrawable(resources, R.drawable.black_download_icon, null)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        }
        super.onPrepareOptionsMenu(menu)
    }

}