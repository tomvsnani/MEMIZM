package com.analogit.memeizm.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dinuscxj.gesture.MultiTouchGestureDetector
import com.analogit.memeizm.Adapters.ViewpagerAdapter
import com.analogit.memeizm.Constants
import com.analogit.memeizm.MainActivity
import com.analogit.memeizm.Models.TextPropertiesModelClass
import com.analogit.memeizm.R
import com.analogit.memeizm.databinding.CustomAlertDialogBinding
import com.analogit.memeizm.databinding.FragmentRecreationBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class RecreationFragment : Fragment(R.layout.fragment_recreation) {
    lateinit var binding: FragmentRecreationBinding

    private val margin = 40
    var numberOfDynamicViewsCount = 0f;
    private var scalex = 1f
    private var scaley = 1f
    var active_pointer_id = -1
    lateinit var dynamicViewwithIdHashmap: HashMap<Int, View>
    var pointersCount = 0;
    var multiTouchGestureDetector: MultiTouchGestureDetector? = null
    var hashMapGesture = HashMap<View, MultiTouchGestureDetector>()
    var viewBeingTransformed: View? = null
    var isTransformLocked = false
    var tabSize = 4
    lateinit var viewpagerFragmentList: List<Fragment>
    var viewTouchPoint = -1

    var isScaling = false
    var isRotating = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecreationBinding.bind(view)
        (activity as MainActivity).editingImageBitmap?.let {
            binding.mainEditableImageView.setImageBitmap(it)
        }

        (activity as MainActivity).imageChangeCallBack = {
            generateViewDynamically(Constants.TYPE_IMAGE, uri = it)
        }

        dynamicViewwithIdHashmap = HashMap()
        (activity as MainActivity).setUpFragmentsToolbarProperties(
            "Recreation",
            true
        )


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

        setUpClickListeners()


        attachViewPager()

        (activity as MainActivity).textChangeCallback = fun(textpro: TextPropertiesModelClass) {


            val textView =
                if (binding.constrait?.findViewWithTag<TextView>(textpro.id.toInt()) == null) TextView(
                    binding.constrait.context
                ).apply {


                    textSize = 24f
                    setTextColor(textpro.color.toInt())

                    typeface = textpro.typeFace
                    setPadding(8 * resources.displayMetrics.density.toInt())


                }
                else binding.constrait?.findViewWithTag<TextView>(textpro.id.toInt()).apply {

                    text = textpro.text

                    textSize = 24f
                    setTextColor(textpro.color.toInt())
                    typeface = textpro.typeFace

                }

            if (textView.tag==null || textpro.id != (textView.tag.toString()))
                generateViewDynamically(
                    Constants.TYPE_TEXT,
                    textpro.text,
                    textView = textView.apply {
                        tag = textpro.id.toInt()
                        setBackgroundColor(Color.BLUE)
                    }
                )
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpClickListeners() {
        binding.downloadbutton.setOnClickListener {
            downloadTransformedImage()
            (activity as MainActivity).apply {
                insert(modelThatIsBeingEdited?.apply {
                    isInMyDownloads = true
                }!!)
            }
        }




        binding.constrait.setOnTouchListener { v, event ->
            pointersCount = event.pointerCount
            binding.nestedScroll.isEnableScrolling = binding.constrait.isTransformLocked

            Log.d("eventtt", dynamicViewwithIdHashmap.size.toString())



            return@setOnTouchListener false
        }
    }

//    private fun transformViewsBasedOnTouch(event: MotionEvent) {
//        when (event.action and event.actionMasked) {
//
//            MotionEvent.ACTION_DOWN -> {
//
//
//                var rectF = Rect()
//                var j = 0;
//
//                for (i in dynamicViewwithIdHashmap.toSortedMap(reverseOrder())) {
//
//                    i.value.apply {
//                        getHitRect(rectF)
//                        if (rectF.contains(event.x.toInt(), event.y.toInt()))
//                            j = 1
//                    }
//                    if (j == 1) {
//                        isTransformLocked = false
//                        viewBeingTransformed = i.value
//                        break
//                    }
//                }
//                if (j == 0)
//                    isTransformLocked = true
//
//                if (viewBeingTransformed != null && !isTransformLocked) {
//                    event.apply {
//                        active_pointer_id = getPointerId(0)
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            scalex = viewBeingTransformed!!.x - getRawX(
//                                findPointerIndex(active_pointer_id)
//                            )
//                            scaley = viewBeingTransformed!!.y - getRawY(
//                                findPointerIndex(active_pointer_id)
//                            )
//                        }
//
//
//                    }
//                    // detectWhereTouched(view1!!.width, view1!!.height, event, view1!!)
//                    if (!hashMapGesture.containsKey(viewBeingTransformed)
//
//                    ) {
//                        multiTouchGestureDetector = MultiTouchGestureDetector(
//                            context, setListener(viewBeingTransformed!!, 1.0f, 0f)
//                        )
//
//                        hashMapGesture[viewBeingTransformed!!] = multiTouchGestureDetector!!
//                    } else {
//                        multiTouchGestureDetector = hashMapGesture[viewBeingTransformed!!]
//                    }
//                }
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                Log.d("eventttookk", event.action.toString())
//                if (pointersCount == 1 && !isTransformLocked) {
//                    viewBeingTransformed!!.apply {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            x = scalex + event.getRawX(event.findPointerIndex(active_pointer_id))
//                            y = scaley + event.getRawY(event.findPointerIndex(active_pointer_id))
//                        }
//
//                    }
//                }
//
//            }
//
//            MotionEvent.ACTION_POINTER_UP -> {
//                event.apply {
//                    if (getPointerId(actionIndex) == active_pointer_id) {
//                        var newPointerIndex = if (actionIndex == 0) 1 else 0
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            scaley = viewBeingTransformed!!.y - getRawY(newPointerIndex)
//                            scalex = viewBeingTransformed!!.x - getRawX(newPointerIndex)
//                        }
//                        active_pointer_id = getPointerId(newPointerIndex)
//                    }
//
//                }
//
//            }
//
//        }
//        if (multiTouchGestureDetector != null) {
//            multiTouchGestureDetector?.onTouchEvent(event)
//        }
//
//
//        //            binding.frame.apply {
//        //
//        //                getHitRect(rectF)
//        //            }
//        //            (v as View).apply {
//        //                touchDelegate = TouchDelegate(rectF, binding.frame)
//        //            }
//    }


    private fun downloadTransformedImage() {
        binding.constrait.apply {

            if (width > 0 && height > 0 ) {
                with((activity as MainActivity)){
                    saveBitmapToMemory()
                }
            } else {
                Toast.makeText(context, "Please select an image ", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun attachViewPager() {
        viewpagerFragmentList = if (tabSize == 4) mutableListOf(
            ImageFragment(),
            TextTabFragment(),
            GallaryTabFragment(),
            LogoTabFragment()
        ) else mutableListOf(
            TextTabFragment(),
            GallaryTabFragment(),
            LogoTabFragment()
        )

        var viewPager2: ViewPager2 = binding.viewpager2

        viewPager2.adapter = ViewpagerAdapter(this, tabSize)

        viewPager2.isUserInputEnabled = false



        TabLayoutMediator(binding.tablayout, viewPager2) { tab, position ->


            displayTabsAccordingToPosition(position, tab)

        }.attach()


    }

//    private fun setConstraintHeight(id: Int) {


//        binding.tablayout.viewTreeObserver.addOnGlobalLayoutListener(
//
//            object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    binding.constrait.apply {
//                        var array = intArrayOf(0, 0)
//                        getLocationOnScreen(array)
//                        minHeight =
//                            (resources.displayMetrics.heightPixels - array[1] -
//                                    binding.tablayout.height - (32 * resources.displayMetrics.density).toInt())
//                        maxHeight = minHeight
//
//                    }
//                    binding.tablayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                }
//
//            }
//        )

//    }


    private fun displayTabsAccordingToPosition(
        position: Int,
        tab: TabLayout.Tab
    ) {


        if (tabSize == 3) {
            when (position) {
                0 -> {
                    //                    tab.setCustomView(LayoutInflater.from(context).inflate(R.layout.custom_tab_view_layout,null,false))
                    tab.text = "Text"
                    tab.setIcon(R.drawable.ic_baseline_text_fields_24)
                    tab.view.tag = Constants.TYPE_TEXT
                }
                1 -> {
                    tab.text = "Graphics"
                    tab.setIcon(R.drawable.ic_baseline_emoji_emotions_24)
                    tab.view.tag = Constants.TYPE_GRAPHICS
                }
                2 -> {
                    tab.text = "Logo"
                    tab.setIcon(R.drawable.ic_baseline_psychology_24)
                    tab.view.tag = Constants.TYPE_LOGO
                }
            }
        } else {
            when (position) {
                0 -> {
                    //                    tab.setCustomView(LayoutInflater.from(context).inflate(R.layout.custom_tab_view_layout,null,false))
                    tab.text = "Image"
                    tab.setIcon(R.drawable.ic_baseline_image_24)

                    tab.view.tag = Constants.TYPE_IMAGE
                }
                1 -> {
                    tab.text = "Text"
                    tab.setIcon(R.drawable.ic_baseline_text_fields_24)

                    tab.view.tag = Constants.TYPE_TEXT
                }
                2 -> {
                    tab.text = "Graphics"
                    tab.setIcon(R.drawable.ic_baseline_psychology_24)
                    tab.view.tag = Constants.TYPE_GRAPHICS
                }
                3 -> {
                    tab.text = "Logo"
                    tab.setIcon(R.drawable.ic_baseline_emoji_emotions_24)
                    tab.view.tag = Constants.TYPE_LOGO
                }
            }
        }
    }


//    private fun calculateHeighjtOfViewPager(position: Int) {
//
//        viewpagerFragmentList[position].view?.apply {
//            Log.d("heightwidth", "$width   $height")
//            val wMeasureSpec = makeMeasureSpec(
//                width,
//                View.MeasureSpec.EXACTLY
//            )
//            val hMeasureSpec = makeMeasureSpec(
//                0,
//                View.MeasureSpec.UNSPECIFIED
//            )
//            measure(wMeasureSpec, hMeasureSpec)
//            if (binding.viewpager2.layoutParams.height != height) {
//                binding.viewpager2.layoutParams =
//                    (binding.viewpager2.layoutParams as ConstraintLayout.LayoutParams)
//                        .also { lp ->
//                            lp.height = this.measuredHeight
//                            lp.width = this.measuredWidth
//                        }
//            }
//            Log.d("measureddheightt", measuredHeight.toString())
//
//
//        }
//    }


    private fun createCustomTextDialog() {
        var text = ""

        var alert = AlertDialog.Builder(context).create()
        var dialogBinding = CustomAlertDialogBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.custom_alert_dialog, null, false)
        )
        alert.apply {
            setView(
                dialogBinding.root
            )
            view.apply {
                dialogBinding.doneButton.setOnClickListener {
                    text =
                        dialogBinding.customEditText.text.toString()

                    dismiss()
                }
            }

        }

        alert.show()
        alert.setOnDismissListener {
            generateViewDynamically(Constants.TYPE_TEXT, text)
        }
    }


    private fun generateViewDynamically(
        type: String,
        uri: String = "",
        textView: TextView? = null
    ) {
     binding.constrait.generateViewDynamically(type,uri,textView)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }


    fun detectWhereTouched(width: Int, height: Int, event: MotionEvent, view: View): Int {
        Log.d("rounddd", "${event.x} ${event.y}  ${view.x}  ${view.y}")
        var xDiff = abs(event.x - view.x)
        var yDiff = abs(event.y - view.y)
        when {
            xDiff <= 100 && yDiff <= 100 -> viewTouchPoint = 0
            xDiff <= 100 && yDiff > 100 -> viewTouchPoint = 2
            xDiff > 100 && yDiff <= 100 -> viewTouchPoint = 1
            xDiff > 100 && yDiff > 100 -> viewTouchPoint = 3
        }
        return viewTouchPoint

    }



}