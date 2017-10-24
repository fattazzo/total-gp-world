package com.gmail.fattazzo.formula1world.utils

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue

import com.gmail.fattazzo.formula1world.R

import org.androidannotations.annotations.EBean
import org.apache.commons.lang3.ObjectUtils

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class ThemeUtils {

    fun getThemeTextColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(android.R.attr.textColor, value, true)
        return value.data
    }

    fun getThemeBGColor(context: Context): Int {
        try {
            val value = TypedValue()
            val resolved = context.theme.resolveAttribute(R.attr.bgBackground, value, true)
            return if (resolved) {
                value.data
            } else {
                android.R.color.transparent
            }
        } catch (e: Exception) {
            return android.R.color.transparent
        }

    }

    fun getThemeTextSize(context: Context, dimensionResId: Int): Float {
        val scaleRatio = ObjectUtils.defaultIfNull(context.resources.displayMetrics.density, 1f)
        val dimenPix = ObjectUtils.defaultIfNull(context.resources.getDimension(dimensionResId), 16f)
        return dimenPix / scaleRatio
    }

    fun getThemeEvenRowColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.evenRowColor, value, true)
        return value.data
    }

    fun getThemeOddRowColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.oddRowColor, value, true)
        return value.data
    }
}
