package com.show.placeholder

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import java.lang.ref.WeakReference

/**
 *  com.example.ken.kmvvm.placeholder
 *  2019/11/10
 *  19:43
 */
class PlaceHolderDrawable(config: PlaceConfig) : ShapeDrawable() {

    private val defaultColor = config.getDefaultColor()
    private val heightColor = config.getLightColor()
    private val radius = config.getRectRadius()
    private val mColors: IntArray = intArrayOf(defaultColor,heightColor,defaultColor)
    private var valueAnimator: ValueAnimator? = null
    private val duration = 2500L
    private val interpolator = LinearInterpolator()

    private var xStartCoordinate = 0f
    private var xEndCoordinate = 0f
    private var animatedValue = 0f
    private var mCanvasWidth = 0
    private var mCanvasHeight = 0

    private var mGradientCanvas: Canvas? = null
    private var mGradientLayer: Bitmap? = null

    private var mBackgroundCanvas: Canvas? = null
    private var mBackgroundLayer: Bitmap? = null
    private var target: WeakReference<View> = WeakReference(config.view)

    init {

        target.get()?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {

            }

            override fun onViewDetachedFromWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                cancelAnimation()
            }
        })

        target.get()?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                if (mGradientCanvas == null) {
                    if(config.getPlaceHolderSize().first == 0 && config.getPlaceHolderSize().second == 0){
                        mCanvasWidth = target.get()?.measuredWidth?:0
                        mCanvasHeight = target.get()?.measuredHeight?:0
                    }else{
                        mCanvasWidth = config.getPlaceHolderSize().first
                        mCanvasHeight = config.getPlaceHolderSize().second
                    }
                    if(mCanvasWidth >0 && mCanvasHeight >0){
                        mGradientLayer = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ALPHA_8)
                        mGradientCanvas = Canvas(mGradientLayer!!)

                        mBackgroundLayer = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ARGB_8888)
                        mBackgroundCanvas = Canvas(mBackgroundLayer!!)
                        target.get()?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        })

        shape = getReboundRect(radius)

    }

    private fun getReboundRect(radius: Float): RoundRectShape {
        return RoundRectShape(floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius), null, null)
    }

    override fun draw(canvas: Canvas) {
        if (bounds.width() <= 0
                || bounds.height() <= 0) {
            super.draw(canvas)
            return
        }
        if (target.get() == null) {
            cancelAnimation()
            return
        }


        if (mBackgroundLayer!!.isRecycled
            || mGradientLayer!!.isRecycled ) {
            super.draw(canvas)
            return
        }

        paint.color = defaultColor
        shape.draw(mBackgroundCanvas, paint)

        canvas.drawBitmap(mBackgroundLayer!!, 0f, 0f, paint)

        xStartCoordinate = animatedValue
        xEndCoordinate = xStartCoordinate + mCanvasWidth/2

        val floatArray = floatArrayOf( 0f,0.4f,0.8f)

        paint.shader = LinearGradient(xStartCoordinate, 0f, xEndCoordinate, 0f, mColors, floatArray, Shader.TileMode.CLAMP)
        shape.draw(mGradientCanvas, paint)
        canvas.drawBitmap(mGradientLayer!!, 0f, 0f, paint)

    }


    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        mCanvasWidth = bounds.width()
        mCanvasHeight = bounds.height()
    }


    fun setupAnimator() {
        xStartCoordinate = 0f
        valueAnimator = ValueAnimator.ofFloat(- mCanvasWidth.toFloat()/3, mCanvasWidth.toFloat())
        valueAnimator?.duration = duration
        valueAnimator?.interpolator = interpolator
        valueAnimator?.repeatMode = ValueAnimator.RESTART
        valueAnimator?.repeatCount = ValueAnimator.INFINITE
        valueAnimator?.addUpdateListener { animation ->
            animatedValue = animation.animatedValue as Float
            invalidateSelf()
        }
        valueAnimator?.start()
    }


    fun cancelAnimation() {
        if (valueAnimator != null) {
            valueAnimator?.cancel()
            valueAnimator = null
        }

        if (mGradientLayer != null) {
            if (!mGradientLayer?.isRecycled!!) {
                mGradientLayer?.recycle()
            }
            mGradientLayer = null
        }
        if (mBackgroundLayer != null) {
            if (!mBackgroundLayer?.isRecycled!!) {
                mBackgroundLayer?.recycle()
            }

            mGradientLayer = null
        }
    }

}
