package com.analogit.memeizm

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.dinuscxj.gesture.MultiTouchGestureDetector

class CustomImageView constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet), MultiTouchGestureDetector.OnMultiTouchGestureListener{
    private lateinit var bitmap: Bitmap
    lateinit var paint: Paint
    lateinit var roundRect: Rect
    lateinit var textBoundsRect: RectF
    lateinit var textPaint: Paint
    lateinit var testRect: RectF
    private var scalex = 1f
    private var scaley = 1f
    var scalefactor = 1f
    lateinit var multiTouchGestureDetector: MultiTouchGestureDetector
//    var scaleGestureDetector: ScaleGestureDetector
    var rotationfactor = 0f


    init {
        paint = Paint().apply {


        }
//        scaleGestureDetector = ScaleGestureDetector(context, this)
        multiTouchGestureDetector = MultiTouchGestureDetector(context, this)
        textPaint = Paint().apply {
            color = Color.GREEN
//                getTextBounds("helloo", 0, "helloo".length,textBoundsRect)
            textSize = 40f
        }

    }


    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        roundRect = Rect(40, 40, bitmap.width, bitmap.width).apply {

        }
        textBoundsRect = RectF(roundRect);
        testRect = RectF(80f, 80f, (bitmap.width - 40).toFloat(), (bitmap.width - 40).toFloat())


    }


//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        Log.d("scallinggg","event")
//        return false
//    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("scallinggssss", "event")
        multiTouchGestureDetector.onTouchEvent(event)
//        scaleGestureDetector.onTouchEvent(event)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {


                scalex = x - event.rawX
                scaley = y - event.rawY

                //                          binding.mainEditableImageView.scree

                //                        binding.mainEditableImageView.x = pointF.x
                //                        binding.mainEditableImageView.y=pointF.y
            }
            MotionEvent.ACTION_MOVE -> {

                //                            if(event.rawX!=pointF.x)
                //                          translationX=40f
                //                            translationY=40f
                if (!multiTouchGestureDetector.isInProgress ) {
                    scalex = event.rawX + scalex
                    scaley = event.rawY + scaley
                }
                //                            pointF.x = event.x
                //                            pointF.y = event.y


            }

        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
//        Log.d("hellll", "${bitmap.width}  ${bitmap.width}")
        super.onDraw(canvas)
        canvas?.apply {


//            drawRoundRect(roundRect, 1000f, 1000f, paint)
//
//            drawText(
//                "helloo",
//                roundRect.centerX() - textPaint.measureText("helloo") / 2,
//                roundRect.centerY()+50, textPaint
//            )
//            drawRect(
//                roundRect.centerX(),
//                roundRect.centerY(),
//                roundRect.centerX() + 5,
//                roundRect.centerY() + 5,
//                Paint().apply {
//                    style = Paint.Style.FILL
//                    color = Color.BLUE
//                })
//            scale(scalefactor, scalefactor, width.toFloat(), 0f)
            rotate(rotationfactor)
            translate(scalex,scaley)
            drawBitmap(bitmap, null, roundRect, paint);

         drawRoundRect(
                textBoundsRect,
                roundRect.width().toFloat(),
                roundRect.width().toFloat(),
                paint
            )
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
//            save()
//
//
//            restore()
//            canvas.restore()


            //   canvas.drawColor(Color.RED)


        }

    }

    override fun onScale(detector: MultiTouchGestureDetector?) {
        Log.d("scallinggfac", detector!!.scale.toString())
        scalefactor *= detector!!.scale
//        scalefactor = max(0.1f, min(scalefactor, 5.0f))

        invalidate()
    }

    override fun onMove(detector: MultiTouchGestureDetector?) {

    }

    override fun onRotate(detector: MultiTouchGestureDetector?) {
        rotationfactor += detector!!.rotation
    }

    override fun onBegin(detector: MultiTouchGestureDetector?): Boolean {
        return true
    }

    override fun onEnd(detector: MultiTouchGestureDetector?) {

    }



}