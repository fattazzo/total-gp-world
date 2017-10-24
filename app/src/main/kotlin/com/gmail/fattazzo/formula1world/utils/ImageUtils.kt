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
