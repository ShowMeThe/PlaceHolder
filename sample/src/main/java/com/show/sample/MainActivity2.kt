package com.show.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.show.placeholder.PlaceConfig
import com.show.placeholder.PlaceHolderDrawable
import com.show.placeholder.PlaceHolderManager
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val text = "This is a Test View This is a Test View \n" +
                "        This is a Test View This is a Test View This is\n" +
                "         a Test View This is a Test View This is a Test View "
        val url = "https://s1.ax1x.com/2020/07/24/UXTFpt.png"

        val ivDrawable = ContextCompat.getDrawable(this,R.drawable.logo)
        tv.text = text
        ivLogo.setImageDrawable(ivDrawable)
        val place = PlaceHolderDrawable(PlaceConfig(ivLogo2).setAttachDrawable(true))

        Glide.with(this).load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(place).into(ivLogo2)

        val placeHolderManager = PlaceHolderManager.getManager()
        placeHolderManager.patchViews(ivLogo,PlaceConfig(tv))

        ivLogo.postDelayed({
            placeHolderManager.clear()
        },4000)


    }

    override fun onBackPressed() {
        finish()
    }
}