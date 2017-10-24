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
            val anim: Int
            when (prefs!!.fragmentTransitionAnimation().get()) {
                "fade" -> anim = android.R.anim.fade_in
                "slide" -> anim = android.R.anim.slide_in_left
                else -> anim = -1
            }

            return anim
        }

    /**
     * Fragment transaction exit animation
     *
     * @return -1 for none animation
     */
    val fragmentTransactionExitAnimation: Int
        @Deprecated("")
        get() {
            val anim: Int
            when (prefs!!.fragmentTransitionAnimation().get()) {
                "fade" -> anim = android.R.anim.fade_out
                "slide" -> anim = android.R.anim.slide_out_right
                else -> anim = -1
            }

            return anim
        }

    /**
     * @return application theme
     */
    val appTheme: Int
        get() = getAppTheme(prefs!!.appTheme().get())

    val isBitmapInvertedForCurrentTheme: Boolean
        get() {
            val theme = appTheme

            when (theme) {
                R.style.AppTheme -> return true
                else -> return false
            }
        }

    /**
     * Pager transaction animation
     *
     * @return configured transformer for animation
     */
    val pagerTansactionAnimation: ABaseTransformer
        get() {
            val transformer: ABaseTransformer

            when (prefs!!.pagerTransitionAnimation().get()) {
                "zoomOutSlide" -> transformer = ZoomOutSlideTransformer()
                "cubeOut" -> transformer = CubeOutTransformer()
                "cubeIn" -> transformer = CubeInTransformer()
                "accordion" -> transformer = AccordionTransformer()
                "flipHorizontal" -> transformer = FlipHorizontalTransformer()
                "flipVertical" -> transformer = FlipVerticalTransformer()
                else -> transformer = ZoomOutSlideTransformer()
            }

            return transformer
        }

    val statisticsChartColorTheme: ChartColorTheme
        get() {
            val idxPrefs = StringUtils.defaultString(prefs!!.statisticsChartColorTheme().get(), "3")

            var theme: ChartColorTheme
            try {
                val idx = Integer.parseInt(idxPrefs)
                theme = ChartColorTheme.values()[idx]
            } catch (e: Exception) {
                theme = ChartColorTheme.COLORFUL
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
        val theme: Int
        when (code) {
            "dark" -> theme = R.style.AppTheme_Dark
            "light" -> theme = R.style.AppTheme
            else -> theme = R.style.AppTheme_Dark
        }
        return theme
    }

    fun newsLanguage(): String {

        var newsLanguage = prefs!!.newsLanguage().get()

        if (StringUtils.isBlank(newsLanguage)) {
            newsLanguage = Locale.getDefault().language
        }

        return newsLanguage
    }
}
