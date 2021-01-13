package com.example.memeizm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.MimeTypeFilter
import androidx.core.view.drawToBitmap
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dinuscxj.gesture.MultiTouchGestureDetector
import com.example.memeizm.Adapters.ViewpagerAdapter
import com.example.memeizm.databinding.CustomAlertDialogBinding
import com.example.memeizm.databinding.FragmentRecreationBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.Comparator
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class RecreationFragment : Fragment(R.layout.fragment_recreation) {
    lateinit var binding: FragmentRecreationBinding
    lateinit var multiTouchGestureListener: MultiTouchGestureDetector.OnMultiTouchGestureListener
    lateinit var listener: ScaleGestureDetector.OnScaleGestureListener
    val margin = 40
    var count = 0;
    private var scalex = 1f
    private var scaley = 1f
    var pointer_id = -1
    lateinit var hashMap: HashMap<Int, View>
    var pointersCount = 0;
    var countt = 0;
    var multiTouchGestureDetector: MultiTouchGestureDetector? = null
    var hashMapGesture = HashMap<View, MultiTouchGestureDetector>()
    var view1: View? = null
    var isTransformLocked = false
    var tabSize = 4

    var viewTouchPoint = -1
    lateinit var text: View
    var a: ActivityResultLauncher<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        a = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
//                binding.mainEditableImageView.setImageURI(it)
                generateViewDynamically(Constants.TYPE_IMAGE, uri = it.toString())
            }

        }
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecreationBinding.bind(view)
        hashMap = HashMap()
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

        binding.downloadbutton.setOnClickListener {
            binding.constrait.apply {

                var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                var canvas = Canvas(bitmap)
                if (background != null && background is BitmapDrawable)
                    canvas.drawBitmap(
                        (background as BitmapDrawable).bitmap,
                        0f,
                        0f,
                        null
                    )
                else if (background != null && background is ColorDrawable)
                    canvas.drawColor((background as ColorDrawable).color)
                else
                    setBackgroundColor(Color.WHITE)
                draw(canvas)
                try {


                    var outputStream = File.createTempFile(
                        "okkk",
                        "yess" + ".png",

                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    ).let {

                        FileOutputStream(it)
                    }

                    bitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }




        binding.constrait.setOnTouchListener { v, event ->
            pointersCount = event.pointerCount
            binding.nestedScroll.isEnableScrolling = isTransformLocked

            Log.d("eventtt", hashMap.size.toString())
            when (event.action and event.actionMasked) {

                MotionEvent.ACTION_DOWN -> {
                    countt++
//                    if(countt==3)
//                        ( text as TextView).text="GHGFYggdygcxghbcxkh"
                    var rectF = Rect()
                    var j = 0;

                    for (i in hashMap.toSortedMap(reverseOrder())) {

                        i.value.apply {
                            getHitRect(rectF)
                            if (rectF.contains(event.x.toInt(), event.y.toInt()))
                                j = 1
                        }
                        if (j == 1) {
                            isTransformLocked = false
                            view1 = i.value
                            break
                        }
                    }
                    if (j == 0)
                        isTransformLocked = true

                    if (view1 != null && !isTransformLocked) {
                        event.apply {
                            pointer_id = getPointerId(0)
                            scalex = view1!!.x - getRawX(findPointerIndex(pointer_id))
                            scaley = view1!!.y - getRawY(findPointerIndex(pointer_id))

                        }
                        // detectWhereTouched(view1!!.width, view1!!.height, event, view1!!)
                        if (!hashMapGesture.containsKey(view1)

                        ) {
                            multiTouchGestureDetector = MultiTouchGestureDetector(
                                context, setListener(view1!!, 1.0f, 0f)
                            )

                            hashMapGesture[view1!!] = multiTouchGestureDetector!!
                        } else {
                            multiTouchGestureDetector = hashMapGesture[view1!!]
                        }
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    Log.d("eventttookk", event.action.toString())
                    if (pointersCount == 1 && !isTransformLocked) {
                        view1!!.apply {
                            x = scalex + event.getRawX(event.findPointerIndex(pointer_id))
                            y = scaley + event.getRawY(event.findPointerIndex(pointer_id))
                        }
                    }

                }

                MotionEvent.ACTION_POINTER_UP -> {
                    event.apply {
                        if (getPointerId(actionIndex) == pointer_id) {
                            var newPointerIndex = if (actionIndex == 0) 1 else 0
                            scalex = view1!!.x - getRawX(newPointerIndex)
                            scaley = view1!!.y - getRawY(newPointerIndex)
                            pointer_id = getPointerId(newPointerIndex)
                        }

                    }

                }

            }
            if (multiTouchGestureDetector != null) {
                multiTouchGestureDetector?.onTouchEvent(event)
            }


//            binding.frame.apply {
//
//                getHitRect(rectF)
//            }
//            (v as View).apply {
//                touchDelegate = TouchDelegate(rectF, binding.frame)
//            }


            return@setOnTouchListener true
        }


        attachViewPager()

    }


    private fun attachViewPager() {
        var viewPager2: ViewPager2 = binding.viewpager2

        viewPager2.adapter = ViewpagerAdapter(this, tabSize)

        viewPager2.isUserInputEnabled = false

        TabLayoutMediator(binding.tablayout, viewPager2) { tab, position ->
            tab.view.setOnClickListener {


                when (it.tag) {
                    Constants.TYPE_IMAGE -> {
//

                        a!!.launch("image/*")


                    }
                    Constants.TYPE_LOGO -> {
                    }
                    Constants.TYPE_GRAPHICS -> {
                    }
                    Constants.TYPE_TEXT -> {
//                        generateViewDynamically(Constants.TYPE_TEXT,"yesss")


                        createCustomTextDialog()

                    }

                }
                binding.viewpager2.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        childFragmentManager.fragments.get(position).tag
                        super.onPageSelected(position)
                    }
                })

            }



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
        }.attach()


    }

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


    private fun generateViewDynamically(type: String, text: String = "", uri: String = ""): View {

        var constraintSet = ConstraintSet()
        var id =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                View.generateViewId()
            } else {
                0
            }
        Log.d("intextgeneration", "yess")

        var dynamicView: View? = null
        dynamicView = when (type) {
            Constants.TYPE_TEXT -> {
                TextView(context).apply {
                    setText(text)

                    textSize = 24f
                    setTextColor(Color.RED)
                    setPadding(32 * resources.displayMetrics.density.toInt())

//                    setBackgroundColor(resources.getColor(R.color.graywhite))

                }
            }
            Constants.TYPE_IMAGE -> {

                ImageView(context).apply {
                    setImageURI(Uri.parse(uri))
                    setBackgroundColor(Color.RED)

                }
            }
            else -> return TextView(context)
        }




        dynamicView.id = id
        binding.constrait.addView(dynamicView)
        constraintSet.clone(binding.constrait)
        constraintSet.apply {
            setMargin(id, ConstraintSet.TOP, count * margin)

            connect(
                dynamicView.id,
                ConstraintSet.TOP,
                binding.downloadbutton.id,
                ConstraintSet.BOTTOM
            )
            connect(
                dynamicView.id,
                ConstraintSet.BOTTOM,
                binding.mainEditableImageView.id,
                ConstraintSet.BOTTOM,
            )
            connect(
                dynamicView.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
            )
            connect(
                dynamicView.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
            )
        }
        constraintSet.applyTo(binding.constrait)
        hashMap[id] = dynamicView
        binding.constrait.bringChildToFront(binding.mainEditableImageView)


        return dynamicView!!
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("clickeddd", "fragmentclcikedd")
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

    private fun setListener(
        v: View,
        scaleFactor1: Float,
        rotationfactor: Float
    ): MultiTouchGestureDetector.OnMultiTouchGestureListener {
        var scaleFactor11 = scaleFactor1
        var rotationfactor1 = rotationfactor

        return object : MultiTouchGestureDetector.OnMultiTouchGestureListener {
            override fun onScale(detector: MultiTouchGestureDetector?) {
                v.apply {
                    scaleFactor11 *= detector!!.scale
                    scaleFactor11 = max(0.1f, min(scaleFactor11, 5.0f))
                    scaleX = scaleFactor11
                    scaleY = scaleFactor11
                    when (viewTouchPoint) {
                        3 -> {
                            pivotX = 0f
                            pivotY = 0f
                        }
                        2 -> {
                            pivotX = v.width.toFloat()
                            pivotY = 0f
                        }
                        1 -> {
                            pivotX = 0f
                            pivotY = v.height.toFloat()
                        }
                        0 -> {
                            pivotX = v.width.toFloat()
                            pivotY = v.height.toFloat()
                        }
                    }


                }
            }

            override fun onMove(detector: MultiTouchGestureDetector?) {
//                if (pointersCount == 1) {
//                    v.x = v.x + detector!!.moveX
//                    v.y = v.y + detector!!.moveY
//                }
            }

            override fun onRotate(detector: MultiTouchGestureDetector?) {
                rotationfactor1 += detector!!.rotation
                v.apply {

                    rotation = rotationfactor1
                }

            }

            override fun onBegin(detector: MultiTouchGestureDetector?): Boolean {
                return true
            }

            override fun onEnd(detector: MultiTouchGestureDetector?) {

            }
        }
    }
}