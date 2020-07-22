package com.show.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.show.placeholder.PlaceConfig
import com.show.placeholder.PlaceHolderManager
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val text = "This is a Test View This is a Test View \n" +
                "        This is a Test View This is a Test View This is\n" +
                "         a Test View This is a Test View This is a Test View "

        val ivDrawable = ContextCompat.getDrawable(this,R.drawable.logo)

        val placeHolderManager = PlaceHolderManager.getManager()


        placeHolderManager.patchViews(ivLogo,PlaceConfig(tv).setPlaceHolderSize(320,100))

        ivLogo.postDelayed({
            tv.text = text
            ivLogo.setImageDrawable(ivDrawable)
            placeHolderManager.clear()
        },4000)

    }

    override fun onBackPressed() {
        finish()
    }
}