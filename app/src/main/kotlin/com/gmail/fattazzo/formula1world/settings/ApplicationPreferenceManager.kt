/*
 * Project: total-gp-world
 * File: ApplicationPreferenceManager.kt
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

package com.gmail.fattazzo.formula1world.settings

import com.ToxicBakery.viewpager.transforms.*
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.preference.chartColorThemePicker.ChartColorTheme
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.sharedpreferences.Pref
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 26/05/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class ApplicationPreferenceManager {

    @Pref
    lateinit var prefs: ApplicationPreference_

    /**
     * Fragment transaction enter animation
     *
     * @return -1 for none animation
     */
    val fragmentTransactionEnterAnimation: Int
        @Deprecated("")
        get() {
            return when (prefs.fragmentTransitionAnimation().get()) {
                "fade" -> android.R.anim.fade_in
                "slide" -> android.R.anim.slide_in_left
                else -> -1
            }
        }

    /**
     * Fragment transaction exit animation
     *
     * @return -1 for none animation
     */
    val fragmentTransactionExitAnimation: Int
        @Deprecated("")
        get() {
            return when (prefs.fragmentTransitionAnimation().get()) {
                "fade" -> android.R.anim.fade_out
                "slide" -> android.R.anim.slide_out_right
                else -> -1
            }
        }

    /**
     * @return application theme
     */
    val appTheme: Int
        get() = getAppTheme(prefs.appTheme().get())

    val isBitmapInvertedForCurrentTheme: Boolean
        get() {
            val theme = appTheme

            return when (theme) {
                R.style.AppTheme -> true
                else -> false
            }
        }

    /**
     * Pager transaction animation
     *
     * @return configured transformer for animation
     */
    val pagerTansactionAnimation: ABaseTransformer
        get() {
            return when (prefs.pagerTransitionAnimation().get()) {
                "zoomOutSlide" -> ZoomOutSlideTransformer()
                "cubeOut" -> CubeOutTransformer()
                "cubeIn" -> CubeInTransformer()
                "accordion" -> AccordionTransformer()
                "flipHorizontal" -> FlipHorizontalTransformer()
                "flipVertical" -> FlipVerticalTransformer()
                else -> ZoomOutSlideTransformer()
            }
        }

    val statisticsChartColorTheme: ChartColorTheme
        get() {
            val idxPrefs = StringUtils.defaultString(prefs.statisticsChartColorTheme().get(), "3")

            val theme: ChartColorTheme
            theme = try {
                val idx = Integer.parseInt(idxPrefs)
                ChartColorTheme.values()[idx]
            } catch (e: Exception) {
                ChartColorTheme.COLORFUL
            }

            return theme
        }

    /**
     * Application theme.
     *
     * @param code theme code
     * @return theme
     */
    private fun getAppTheme(code: String): Int {
        return when (code) {
            "dark" -> R.style.AppTheme_Dark
            "light" -> R.style.AppTheme
            else -> R.style.AppTheme_Dark
        }
    }

    fun newsLanguage(): String {

        var newsLanguage = prefs.newsLanguage().get()

        if (StringUtils.isBlank(newsLanguage)) {
            newsLanguage = Locale.getDefault().language
        }

        return newsLanguage
    }

    // DB Preferences

    fun getLastVersionDBFilesImported(): Int = prefs.lastVersionDBFilesImported().getOr(0)

    fun isDBImportEnabled(): Boolean = prefs.dbImportEnabled().getOr(true)
}
