package com.example.memeizm

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.dinuscxj.gesture.MultiTouchGestureDetector
import com.example.memeizm.Adapters.ViewpagerAdapter
import com.example.memeizm.databinding.FragmentRecreationBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.min


class RecreationFragment : Fragment(R.layout.fragment_recreation) {
    lateinit var binding: FragmentRecreationBinding
    lateinit var multiTouchGestureListener: MultiTouchGestureDetector.OnMultiTouchGestureListener
    var matrix = Matrix()
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecreationBinding.bind(view)

        (activity as MainActivity).setUpFragmentsToolbarProperties(
            "Recreation",
            true
        )
        var rotationfactor=0.1f
        multiTouchGestureListener = object : MultiTouchGestureDetector.OnMultiTouchGestureListener {
            override fun onScale(detector: MultiTouchGestureDetector?) {

            }

            override fun onMove(detector: MultiTouchGestureDetector?) {

            }

            override fun onRotate(detector: MultiTouchGestureDetector?) {
                Log.d("rotationnn",detector?.rotation.toString())
                rotationfactor+= detector?.rotation?.toFloat()!!
//              rotationfactor=  max(0.1f, min(rotationfactor, 5.0f))
                binding.mainEditableImageView.apply {

                    rotation=rotationfactor
//                    rotation=rotationfactor
                }
            }

            override fun onBegin(detector: MultiTouchGestureDetector?): Boolean {
                return true
            }

            override fun onEnd(detector: MultiTouchGestureDetector?) {

            }
        }

        var multiTouchGestureDetector =
            MultiTouchGestureDetector(context, multiTouchGestureListener)
//        binding.root.setOnClickListener {
//            binding.mainEditableImageView.apply {
//
//                layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
//                    width = binding.mainEditableImageView.width - 10
//                    height = binding.mainEditableImageView.height - 10
//                }
//            }
//        }


//        binding.mainEditableImageView.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
//            binding.root.drawToBitmap().apply {
//
//                Canvas(this).drawText(
//                    "hello",
//                    this.width.toFloat()/2,
//                    this.height.toFloat()/2,
//                    Paint().apply {
//                        color = Color.RED
//                        textSize = 60f
//                        typeface= Typeface.create("Arial",Typeface.ITALIC)
//                        isAntiAlias=true
//
//                    })
//            }
//
//                .compress(
//                    Bitmap.CompressFormat.PNG,
//                    100,
//                    FileOutputStream(
//                        File.createTempFile(
//                            "olkkk",
//                            "compress.png",
//                            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                        )
//                    )
//                )
//        })

        var scaleFactor = 1f
        var listener = object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScale(detector: ScaleGestureDetector?): Boolean {


                scaleFactor *= detector!!.getScaleFactor();
                Log.d("scaleevent", detector!!.scaleFactor.toString())
//
                // Don't let the object get too small or too large.
                scaleFactor = max(0.1f, min(scaleFactor, 5.0f));
                binding.mainEditableImageView.scaleX = scaleFactor
                binding.mainEditableImageView.scaleY = scaleFactor
//                if (detector!!.scaleFactor < scaleFactor || detector!!.scaleFactor > scaleFactor) {
                Log.d("enteredd", scaleFactor.toString())
//                    return false
//                }

                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector?) {
                return Unit
            }
        }

        var scaleGestureDetector = ScaleGestureDetector(context, listener)
        binding.root.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                multiTouchGestureDetector.onTouchEvent(event)
                  scaleGestureDetector.onTouchEvent(event)
                Log.d("herrree", "event")
                var textview: View? = null
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("motioneventtt", "Action down")

                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.d("motioneventtt", "Action Move")
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d("motioneventtt", "Action UP")

                    }
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        Log.d("motioneventtt", "Action POINTER DOWN")
                    }
                    MotionEvent.ACTION_POINTER_UP -> {
                        Log.d("motioneventtt", "Action POINTER UP")

                    }
                    MotionEvent.ACTION_HOVER_MOVE -> {
                        Log.d("motioneventtt", "Action HOVER MOVE")
                    }
                    MotionEvent.ACTION_OUTSIDE -> {
                        Log.d("motioneventtt", "Action OUTSIDE")

                    }
                    MotionEvent.ACTION_POINTER_INDEX_SHIFT -> {
                        Log.d("motioneventtt", "Action POINTER INDEX SHIFT")
                    }
                }
                return true
            }

        })

        var viewPager2: ViewPager2 = binding.viewpager2

        viewPager2.adapter = ViewpagerAdapter(this)



        TabLayoutMediator(binding.tablayout, binding.viewpager2) { tab, position ->

            when (position) {
                0 -> {
//                    tab.setCustomView(LayoutInflater.from(context).inflate(R.layout.custom_tab_view_layout,null,false))
                    tab.text = "Text"
                    tab.setIcon(R.drawable.ic_baseline_text_fields_24)
                }
                1 -> {
                    tab.text = "Graphics"
                    tab.setIcon(R.drawable.ic_baseline_emoji_emotions_24)
                }
                2 -> {
                    tab.text = "Logo"
                    tab.setIcon(R.drawable.ic_baseline_psychology_24)
                }
            }
        }.attach()

    }

    companion object {
        fun getInstance(param1: String, param2: String): Fragment {
            return RecreationFragment().apply {
                this.arguments = Bundle().apply { putString(param1, param2) }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("clickeddd", "fragmentclcikedd")
        return true
    }


}