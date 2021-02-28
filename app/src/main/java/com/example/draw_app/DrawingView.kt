package com.example.draw_app

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet, ) : View(context, attrs) {//ke thua class View
    private var mDrawPath : CustomPath? = null
    private  var mCanvasBitmap: Bitmap? = null
    private var mDrawingPaint: Paint? = null
    private  var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0.toFloat()
    private var color = Color.GREEN
    private var canvas: Canvas? = null

    init {
        _setUpDrawingPaint()
    }

    private fun _setUpDrawingPaint() {
        mDrawingPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawingPaint!!.color = color
        mDrawingPaint!!.style = Paint.Style.STROKE
        mDrawingPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawingPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f,0f, mCanvasPaint)
        if(!mDrawPath!!.isEmpty){
            mDrawingPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawingPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!,mDrawingPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize
                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX, touchY)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()
        return true
    }

    //internal: class noi bo, chi dung duoc ben trong DrawingVieww
    internal inner class CustomPath(var color : Int, var brushThickness : Float) : Path() {

    }
}