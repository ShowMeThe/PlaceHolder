package com.show.placeholder

import android.util.ArrayMap
import android.view.View
import java.lang.ref.SoftReference


class PlaceHolderManager private constructor(){

    companion object{
        private val ins by lazy { PlaceHolderManager() }
        fun getManager() = ins
    }

    private val views = SoftReference<ArrayMap<View, PlaceConfig>>(ArrayMap())

    private lateinit var placeUnder: PlaceUnder23

    fun getViews() = views

    fun patchViews(vararg varView: View){
        for(view in varView){
            val config = PlaceConfig(view)
            config.getHolder()
            views.get()!![view] = config
        }
    }


    fun patchViews(vararg varView: PlaceConfig){
        for(config in varView){
            config.getHolder()
            views.get()!![config.view] = config
        }
    }

    fun patchViews(vararg varView: Any){
        for(view in varView){
            if(view is View){
                val config = PlaceConfig(view)
                config.getHolder()
                views.get()!![config.view] = config
            }else if(view is PlaceConfig){
                view.getHolder()
                views.get()!![view.view] = view
            }
        }
    }

    fun patchUnder23(placeUnder23: PlaceUnder23){
        this.placeUnder = placeUnder23
    }

    fun patchUnder23() = placeUnder

    fun clear(){
        views.get()?.apply {
            if(isNotEmpty()){
                for(it in this){
                    it.value.getHolder()?.clear()
                }
            }
        }
    }


    fun clear(v:View){
        views.get()?.apply {
            this[v]?.getHolder()?.clear()
        }
    }

}