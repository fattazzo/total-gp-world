package com.gmail.fattazzo.formula1world.activity.fullscreen

import android.app.Activity
import android.os.Bundle
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.view.ZoomableImageView
import org.androidannotations.annotations.*

@EActivity(R.layout.activity_layout_full)
open class FullScreenImageActivity : Activity() {

    @Extra
    lateinit internal var circuit_id: String

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @ViewById
    lateinit internal var imgDisplay: ZoomableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(preferenceManager!!.appTheme)
        super.onCreate(savedInstanceState)
    }

    @AfterViews
    internal fun init() {
        var circuitImage = imageUtils!!.getCircuitForCode(circuit_id!!)
        if (preferenceManager!!.isBitmapInvertedForCurrentTheme) {
            circuitImage = imageUtils!!.invertColor(circuitImage!!)
        }

        imgDisplay!!.setImageBitmap(circuitImage)
    }

    @Click
    internal fun btnCloseClicked() {
        this@FullScreenImageActivity.finish()
    }

    companion object {

        val EXTRA_CIRCUIT_ID = "circuit_id"
    }
}