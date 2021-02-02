package com.analogit.memeizm.Fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_OUTSIDE
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.GridLayoutManager
import com.analogit.memeizm.Constants
import com.analogit.memeizm.R
import com.dinuscxj.gesture.MultiTouchGestureDetector
import kotlin.math.max
import kotlin.math.min




class CustomConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {


    private var isMovingIntoBounds = true
    var numberOfDynamicViewsCount = 0
    var margin = 40
    lateinit var mainEditableImageView: ImageView
    lateinit var dynamicViewwithIdHashmap: HashMap<Int, View>
    lateinit var viewBeingTransformed: View
    var isTransformLocked = false
    private var scalex = 1f
    private var scaley = 1f
    var active_pointer_id = -1
    var pointersCount = -1
    var multiTouchGestureDetector: MultiTouchGestureDetector? = null
    var hashMapGesture = HashMap<View, MultiTouchGestureDetector>()
    var type=""





    init {


        viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                if(type.isNotEmpty()){
                   background= ResourcesCompat.getDrawable(resources,R.drawable.template_shape_border,null)
                }


                    mainEditableImageView = getChildAt(0) as ImageView

                dynamicViewwithIdHashmap = HashMap()

//                if (this@CustomConstraintLayout::mainEditableImageView.isInitialized)
//                    mainEditableImageView.setOnClickListener {
//                        Log.d("clickeddd", childCount.toString())
//                        generateViewDynamically(
//                            Constants.TYPE_TEXT,
//                            textView = TextView(context).apply {
//                                text = "From custom View"
//                                setBackgroundColor(Color.WHITE)
//                                setTextColor(Color.BLACK)
//                            })
//                    }


                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return type.isEmpty() || childCount>=2

    }

    fun setLayoutType(type:String=""){
        this.type=type
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        pointersCount = event?.pointerCount!!



        when (event?.action!! and event.actionMasked) {

            MotionEvent.ACTION_OUTSIDE -> {
                Log.d("outside", "yesss")
            }


            MotionEvent.ACTION_DOWN -> {


                var rectF = Rect()
                var j = 0;

                for (i in dynamicViewwithIdHashmap.toSortedMap(reverseOrder())) {

                    i.value.apply {
                        getHitRect(rectF)
                        if (rectF.contains(event?.x.toInt(), event?.y.toInt()))
                            j = 1
                    }
                    if (j == 1) {
                        isTransformLocked = false
                        viewBeingTransformed = i.value
                        break
                    }
                }
                if (j == 0)
                    isTransformLocked = true

                if (this::viewBeingTransformed.isInitialized && !isTransformLocked) {
                    event?.apply {
                        active_pointer_id = getPointerId(0)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            scalex = viewBeingTransformed!!.x - getRawX(
                                findPointerIndex(active_pointer_id)
                            )
                            scaley = viewBeingTransformed!!.y - getRawY(
                                findPointerIndex(active_pointer_id)
                            )
                        }


                    }
                    // detectWhereTouched(view1!!.width, view1!!.height, event?, view1!!)
                    if (!hashMapGesture.containsKey(viewBeingTransformed)

                    ) {
                        multiTouchGestureDetector = MultiTouchGestureDetector(
                            context, setListener(viewBeingTransformed!!, 1.0f, 0f)
                        )

                        hashMapGesture[viewBeingTransformed!!] = multiTouchGestureDetector!!
                    } else {
                        multiTouchGestureDetector = hashMapGesture[viewBeingTransformed!!]
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                try {
                    isMovingIntoBounds = event.run {

                        preventScreenFromGoingRightOfScreen() && preventFromGoingToLeftOfScreen() &&

                                preventSCreenFromGoingTop() && preventSCreenFromGoingBottom()

                    }
                } catch (e: Exception) {
                }

                if (true) {


                    if (pointersCount == 1 && !isTransformLocked) {


                        viewBeingTransformed!!.apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                x = scalex + event?.getRawX(
                                    event?.findPointerIndex(active_pointer_id)
                                )
                                y = scaley + event?.getRawY(
                                    event?.findPointerIndex(active_pointer_id)
                                )
                            }

                        }
                    }

                }

            }

            MotionEvent.ACTION_POINTER_UP -> {
                event?.apply {
                    if (getPointerId(actionIndex) == active_pointer_id) {
                        var newPointerIndex = if (actionIndex == 0) 1 else 0

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            scaley = viewBeingTransformed!!.y - getRawY(newPointerIndex)
                            scalex = viewBeingTransformed!!.x - getRawX(newPointerIndex)
                        }


                        active_pointer_id = getPointerId(newPointerIndex)


                    }

                }

            }

        }

        if (multiTouchGestureDetector != null) {
            multiTouchGestureDetector?.onTouchEvent(event)
        }



        return true
    }

    private fun MotionEvent.preventSCreenFromGoingTop(): Boolean {
        return (((viewBeingTransformed.y < 0) && (getHistoricalY(
            historySize - 1
        ) < y)) || viewBeingTransformed.y >= 0)
    }

    private fun MotionEvent.preventSCreenFromGoingBottom(): Boolean {
        return ((((viewBeingTransformed.y + viewBeingTransformed.height > viewBeingTransformed.rootView.height) && (getHistoricalY(
            historySize - 1
        ) > y)) || viewBeingTransformed.y + viewBeingTransformed.height <= viewBeingTransformed.rootView.height))
    }

    private fun MotionEvent.preventScreenFromGoingRightOfScreen() =
        ((((viewBeingTransformed.x + viewBeingTransformed.width > viewBeingTransformed.rootView.width) && (getHistoricalX(
            historySize - 1
        ) > x)) || viewBeingTransformed.x + viewBeingTransformed.width <= viewBeingTransformed.rootView.width))

    private fun MotionEvent.preventFromGoingToLeftOfScreen() =
        ((viewBeingTransformed.x < 0) && (getHistoricalX(
            historySize - 1
        ) < x)) || viewBeingTransformed.x >= 0


    public fun generateViewDynamically(
        type: String,
        uri: String = "",
        textView: TextView? = null
    ): View {
        numberOfDynamicViewsCount++
        var constraintSet = ConstraintSet()
        var id = childCount + 1

        var dynamicView: View? = null
        dynamicView = when (type) {
            Constants.TYPE_TEXT -> {
                textView.apply {
                    this?.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.corner_radius_stroke_drawable,
                        null
                    )
                    DrawableCompat.setTint(this?.background!!, Color.BLUE)
                }

            }
            Constants.TYPE_IMAGE -> {

                ImageView(context).apply {
                    setImageURI(Uri.parse(uri))

                    background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.image_transparent_background,
                        null
                    )

                    adjustViewBounds = true
                    setBackgroundColor(Color.RED)
                    measure(
                        MeasureSpec.makeMeasureSpec(
                            this@CustomConstraintLayout.width / 2,
                            MeasureSpec.AT_MOST
                        ),
                        MeasureSpec.makeMeasureSpec(
                            mainEditableImageView.height - (this.marginTop + this.marginBottom + this@CustomConstraintLayout.marginTop + this@CustomConstraintLayout.marginBottom),
                            MeasureSpec.AT_MOST
                        )
                    )

                    layoutParams = ConstraintLayout.LayoutParams(
                        measuredWidth,
                        measuredHeight
                    )


                }
            }
            else -> return TextView(context)
        }



        dynamicView?.id = id

        addView(dynamicView)
        constraintSet.clone(this)
        constraintSet.apply {
            setMargin(
                dynamicView?.id!!,
                ConstraintSet.TOP,
                (numberOfDynamicViewsCount * margin * resources.displayMetrics.density).toInt()
            )
            setMargin(
                dynamicView?.id!!,
                ConstraintSet.START,
                (numberOfDynamicViewsCount * margin * resources.displayMetrics.density).toInt()
            )


            connect(
                dynamicView?.id!!,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                dynamicView?.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
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
        constraintSet.applyTo(this)
        dynamicViewwithIdHashmap[dynamicView?.id!!] = dynamicView!!
        if (type != Constants.TYPE_TEXT) {
            if (this::mainEditableImageView.isInitialized)
                bringChildToFront(mainEditableImageView)
        }


        return dynamicView!!
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
//                    when (viewTouchPoint) {
//                        3 -> {
//                            pivotX = 0f
//                            pivotY = 0f
//                        }
//                        2 -> {
//                            pivotX = v.width.toFloat()
//                            pivotY = 0f
//                        }
//                        1 -> {
//                            pivotX = 0f
//                            pivotY = v.height.toFloat()
//                        }
//                        0 -> {
//                            pivotX = v.width.toFloat()
//                            pivotY = v.height.toFloat()
//                        }
//                    }

                }


            }

            override fun onMove(detector: MultiTouchGestureDetector?) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                    if (pointersCount == 1) {
                        v.x = v.x + detector!!.moveX
                        v.y = v.y + detector!!.moveY
                    }
                }


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