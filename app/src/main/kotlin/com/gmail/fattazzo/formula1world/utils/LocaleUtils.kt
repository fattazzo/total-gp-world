/*
 * Project: total-gp-world
 * File: LocaleUtils.kt
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
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocaleUtils {

    @RootContext
    lateinit internal var context: Context

    private var countries: MutableMap<String, String>? = null

    /**
     * Translate english country name to current locale.
     *
     * @param enCountryName name to translate
     * @return name translated, enCountryName on error
     */
    fun getLocaleCountryName(enCountryName: String): String {

        var i18nCountryName: String
        try {
            val code = getCountryCode(enCountryName)

            val loc = Locale(Locale.getDefault().isO3Language, code)

            i18nCountryName = loc.displayCountry
        } catch (e: Exception) {
            i18nCountryName = enCountryName
        }

        return i18nCountryName
    }

    /**
     * Get country code from country name.
     *
     * @param enCountryName country name
     * @return country code
     */
    fun getCountryCode(enCountryName: String): String {
        var code: String?
        try {
            code = getCountries()[enCountryName]
            if (code == null) {
                code = enCountryName
            }
        } catch (e: Exception) {
            code = "zz"
        }

        return code!!
    }

    /**
     * @return cached countries map
     */
    private fun getCountries(): Map<String, String> {
        if (countries == null) {
            countries = HashMap()
            for (iso in Locale.getISOCountries()) {
                val l = Locale("en", iso)
                countries!!.put(l.getDisplayCountry(l), iso)
            }
            // non iso country variants
            countries!!.put("UK", "GB")
            countries!!.put("USA", "US")
            countries!!.put("UAE", "AE")
            countries!!.put("Korea", "KR")
        }
        return countries!!
    }
}
