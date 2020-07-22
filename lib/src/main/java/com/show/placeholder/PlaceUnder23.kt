package com.show.placeholder

import android.graphics.drawable.Drawable
import android.view.View

/**
 * 低于Android 6.0 的实现这个接口，自行处理兼容，或单独使用PlaceHolderDrawable
 */
interface PlaceUnder23 {

    fun patchView(view:View,drawable: Drawable)

    fun clear(view: View)
}