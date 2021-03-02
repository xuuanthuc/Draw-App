package com.example.draw_app

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    //ke thua class View
    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawingPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0.toFloat()
    private var color = Color.RED
    private var canvas: Canvas? = null
    private val mPath = ArrayList<CustomPath>()
    private  val mUndo = ArrayList<CustomPath>()

    init {
        _setUpDrawingPaint()
    }
    fun onClickUndo(){
        if(mPath.size > 0){
            mUndo.add(mPath.removeAt(mPath.size -1))
            invalidate()
        }
    }

    private fun _setUpDrawingPaint() {
        mDrawingPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawingPaint!!.color = color
        mDrawingPaint!!.style = Paint.Style.STROKE
        mDrawingPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawingPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 10.toFloat()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)
        for (path in mPath) {
            mDrawingPaint!!.strokeWidth = path.brushThickness
            mDrawingPaint!!.color = path.color
            canvas.drawPath(path, mDrawingPaint!!)
        }
        if (!mDrawPath!!.isEmpty) {
            mDrawingPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawingPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawingPaint!!)
        }
    }
    fun setColor(newColor: String){
        color = Color.parseColor(newColor)
        mDrawingPaint!!.color = color
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize
                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX, touchY)
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
                mPath.add(mDrawPath!!)
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun setSizeBrush(newSize: Float) {
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize, resources.displayMetrics
        )
        mDrawingPaint!!.strokeWidth = mBrushSize
    }

    //internal: class noi bo, chi dung duoc ben trong DrawingVieww
    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }
}