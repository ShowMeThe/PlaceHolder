package com.show.placeholder

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver


class PlaceHolder(
    private var view: View,
    private var drawable: PlaceHolderDrawable,
    private var holderWidth: Int = 0,
    private var holderHeight: Int = 0) {


    fun onPatchView() {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                var width = holderWidth
                var height = holderHeight
                width = if (width == 0) {
                    view.measuredWidth
                } else {
                    view.minimumWidth = width
                    width
                }
                height = if (height == 0) {
                    view.measuredHeight
                } else {
                    view.minimumHeight = height
                    height
                }
                drawable.setBounds(0, 0, width, height)
                drawable.setupAnimator()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.foreground = drawable
                }

            }

        })
    }

    fun clear() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.foreground = null
            view.minimumWidth = 0
            view.minimumHeight = 0
        }
    }


}