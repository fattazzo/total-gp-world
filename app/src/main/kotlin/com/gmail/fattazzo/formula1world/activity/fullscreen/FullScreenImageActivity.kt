/*
 * Project: total-gp-world
 * File: FullScreenImageActivity.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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