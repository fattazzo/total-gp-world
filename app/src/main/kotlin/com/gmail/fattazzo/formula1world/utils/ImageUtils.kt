/*
 * Project: total-gp-world
 * File: ImageUtils.kt
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

package com.gmail.fattazzo.formula1world.utils

import android.content.Context
import android.graphics.*
import com.gmail.fattazzo.formula1world.config.Config
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.lang3.StringUtils

/**
 * @author fattazzo
 *
 *
 * date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class ImageUtils {

    @RootContext
    lateinit internal var context: Context

    /**
     * Retrieve bitmap of flag by country code.
     *
     * @param countryCode country code
     * @return bitmap, `null` if not exist
     */
    fun getFlagForCountryCode(countryCode: String): Bitmap? {
        var image: Bitmap?
        try {
            val `is` = context!!.assets.open(Config.PATH_FLAGS + "/" + StringUtils.defaultString(countryCode).toLowerCase() + ".png")
            image = BitmapFactory.decodeStream(`is`)
        } catch (e: Exception) {
            image = null
        }

        return image
    }

    /**
     * Retrieve bitmap of circuit by his code.
     *
     * @param circuitCode circuit code
     * @return bitmap, `null` if not exist
     */
    fun getCircuitForCode(circuitCode: String): Bitmap? {
        var image: Bitmap?
        try {
            val `is` = context!!.assets.open(Config.PATH_CIRCUITS + "/" + StringUtils.defaultString(circuitCode).toLowerCase() + ".png")
            image = BitmapFactory.decodeStream(`is`)
        } catch (e: Exception) {
            image = null
        }

        return image
    }

    /**
     * Invert bitmap color.
     *
     * @param src bitmap src
     * @return bitmap inverted
     */
    fun invertColor(src: Bitmap): Bitmap {
        val colorMatrix_Inverted = ColorMatrix(floatArrayOf(-1f, 0f, 0f, 0f, 255f, 0f, -1f, 0f, 0f, 255f, 0f, 0f, -1f, 0f, 255f, 0f, 0f, 0f, 1f, 0f))

        val ColorFilter_Sepia = ColorMatrixColorFilter(
                colorMatrix_Inverted)

        val bitmap = Bitmap.createBitmap(src.width, src.height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()

        paint.colorFilter = ColorFilter_Sepia
        canvas.drawBitmap(src, 0f, 0f, paint)

        return bitmap
    }
}
