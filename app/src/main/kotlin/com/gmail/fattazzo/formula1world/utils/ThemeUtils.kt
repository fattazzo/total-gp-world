/*
 * Project: total-gp-world
 * File: ThemeUtils.kt
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
