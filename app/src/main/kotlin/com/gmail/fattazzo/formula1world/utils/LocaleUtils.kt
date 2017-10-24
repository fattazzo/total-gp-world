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
