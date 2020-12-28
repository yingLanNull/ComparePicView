package com.yinglan.comparepicview

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

class ComparePicView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {
    private var mBitPaint: Paint? = null

    //方框
    private var boxPaint: Paint? = null

    //方框
    private var oPaint: Paint? = null
    private var mBitmap1: Bitmap? = null
    private var mBitmap2: Bitmap? = null
    private var mSrcRect: Rect? = null
    private var mDestRect: Rect? = null

    // view 的宽高
    private var mTotalWidth = 0
    private var mTotalHeight = 0
    private var mCx = 0f
    private var mCy = 0f
    private val mRadius = 30f
    private var mInCircleButton = false
    private var moveX = 0f

    init {
        mBitPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBitPaint!!.setFilterBitmap(true)
        mBitPaint!!.setDither(true)

        boxPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        boxPaint!!.setColor(Color.GRAY)
        boxPaint!!.setAntiAlias(true)
        boxPaint!!.setStyle(Paint.Style.STROKE)
        boxPaint!!.setStrokeWidth(12f)

        oPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        oPaint!!.setColor(Color.WHITE)
        oPaint!!.setAntiAlias(true)
        oPaint!!.setStyle(Paint.Style.FILL)
    }

    fun setCompareBitmap(bitmap1: Bitmap, bitmap2: Bitmap) {
        mBitmap1 = bitmap1
        mBitmap2 = bitmap2
    }

    private fun initBitmap() {
        mBitmap1 = Bitmap.createScaledBitmap(mBitmap1!!, mTotalWidth, mTotalHeight, true)
        mBitmap2 = Bitmap.createScaledBitmap(mBitmap2!!, mTotalWidth, mTotalHeight, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mTotalHeight = measuredHeight
        mTotalWidth = measuredWidth
        initBitmap()
        moveX = mTotalWidth / 2.toFloat()
        mCx = moveX
        mCy = mTotalHeight / 2.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBitmap(canvas)
        canvas.drawLine(moveX, 0f, moveX, mTotalHeight.toFloat(), boxPaint!!)
        boxPaint!!.color = Color.WHITE
        boxPaint!!.strokeWidth = 10f
        canvas.drawLine(moveX, 0f, moveX, mTotalHeight.toFloat(), boxPaint!!)
        canvas.drawCircle(moveX, mTotalHeight / 2.toFloat(), mRadius, oPaint!!)
    }

    private fun drawBitmap(canvas: Canvas) {
        if(null == mSrcRect){
            mSrcRect = Rect(0, 0, moveX.toInt(), mTotalHeight)
        }else{
            mSrcRect!!.set(0, 0, moveX.toInt(), mTotalHeight)
        }
        if(null == mDestRect){
            mDestRect = Rect(0, 0, moveX.toInt(), mTotalHeight)
        }else{
            mDestRect!!.set(0, 0, moveX.toInt(), mTotalHeight)
        }
        canvas.drawBitmap(mBitmap1!!, mSrcRect, mDestRect!!, mBitPaint)
        mSrcRect!!.set(moveX.toInt(), 0, mTotalWidth, mTotalHeight)
        mDestRect!!.set(moveX.toInt(), 0, mTotalWidth, mTotalHeight)
        canvas.drawBitmap(mBitmap2!!, mSrcRect, mDestRect!!, mBitPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and event.actionMasked) {
            MotionEvent.ACTION_DOWN ->                 // If the point in the circle button
                if (mInCircleButton(event.x, event.y) && isEnabled) {
                    mInCircleButton = true
                }
            MotionEvent.ACTION_MOVE -> if (mInCircleButton && isEnabled) {
                moveX = event.x
                mCx = moveX
                invalidate()
            }
            MotionEvent.ACTION_UP -> mInCircleButton = false
        }
        return true
    }

    private fun mInCircleButton(x: Float, y: Float): Boolean {
        val x2 = (mCx - x)
        val y2 = (mCy - y)
        return sqrt(x2 * x2 + y2 * y2.toDouble()) < mRadius
    }

}