package com.show.placeholder

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.view.View


class PlaceConfig(var view:View){
    private var defaultColor = Color.parseColor("#eeeeee")
    private var lightColor = Color.parseColor("#f7f7f7")
    private var rectRadius = 8f
    private var drawable: PlaceHolderDrawable? = null
    private var width = 0
    private var height = 0
    private var attachStart = true
    private var attachBackground = false //为True时候放到background

    fun setDefaultColor(defaultColor:Int) : PlaceConfig {
        this.defaultColor = defaultColor
        return this
    }

    fun setAttachBackground(isBackground:Boolean):PlaceConfig{
        attachBackground = isBackground
        return this
    }
    fun getAttachBackground() = attachBackground

    fun getDefaultColor() = defaultColor

    fun setLightColor(lightColor:Int) : PlaceConfig {
        this.lightColor = lightColor
        return this
    }

    fun setRectRadius(radius:Float) : PlaceConfig{
        this.rectRadius = radius
        return this
    }
    fun getRectRadius() = rectRadius

    fun getLightColor() = lightColor

    fun setPlaceHolderSize(width:Int,height: Int): PlaceConfig {
        this.width = width
        this.height = height
        return this
    }
    fun getPlaceHolderSize() = width to height


    fun setAttachFirst(first:Boolean):PlaceConfig{
        this.attachStart = first
        return this
    }

    fun getAttachFirst() = attachStart

    fun getHolder() : PlaceHolderDrawable?{
        if(drawable == null){
            drawable = PlaceHolderDrawable(this)
        }
        return drawable
    }

}
