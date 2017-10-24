package com.gmail.fattazzo.formula1world.view.progressbar

import android.content.res.Resources

object Utils {

    internal fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    internal fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }
}