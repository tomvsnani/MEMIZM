package com.example.memeizm

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.memeizm.databinding.FragmentLogoTabBinding
import kotlin.math.max
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogoTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogoTabFragment : Fragment() {
    var matrix = Matrix()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_logo_tab, container, false)
        var binding=FragmentLogoTabBinding.bind(view)
        var scaleFactor1 = 1.0f
        var v = binding.customview
        v.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.img))
        var scaleGestureDetector =
            ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {

                    v.apply {
                        scaleFactor1 *= detector!!.scaleFactor;
                        Log.d("scaleevent", detector!!.scaleFactor.toString())

                        scaleFactor1 = max(0.1f, min(scaleFactor1, 5.0f));

                        scaleX = scaleFactor1
                        scaleY = scaleFactor1
                        Log.d("scallingg", detector!!.scaleFactor.toString())
                    }
//                    if (detector!!.scaleFactor < scaleFactor || detector!!.scaleFactor > scaleFactor) {
//                        return false
//                    }
                    //                    return false
                    //                }

                    return true
                }

                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                    return true
                }

                override fun onScaleEnd(detector: ScaleGestureDetector?) {
                    v.scaleX = scaleFactor1
                    v.scaleY = scaleFactor1
                }
            })
//        v.setOnTouchListener { v, event ->
//            //scaleGestureDetector.onTouchEvent(event)
//            return@setOnTouchListener true
//        }
//        CustomImageView = view.findViewById(R.id.customview)
//       CustomImageView.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.chimney))
        return view
    }


}