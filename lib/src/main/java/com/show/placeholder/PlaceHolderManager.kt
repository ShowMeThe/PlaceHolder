package com.show.placeholder

import android.util.ArrayMap
import android.view.View
import java.lang.ref.SoftReference


class PlaceHolderManager {

    private val views = SoftReference<ArrayMap<View, PlaceConfig>>(ArrayMap())

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

    fun clear(){
        if( views.get()!!.isNotEmpty()){
            for(it in  views.get()!!){
                it.value.getHolder()?.clear()
            }
        }
    }

}