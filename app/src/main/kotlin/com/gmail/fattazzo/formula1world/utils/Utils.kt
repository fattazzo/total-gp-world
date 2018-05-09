/*
 * Project: total-gp-world
 * File: Utils.kt
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

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.lang3.StringUtils
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class Utils {

    @RootContext
    lateinit internal var context: Context

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    private var countriesNationalitiesMap: MutableMap<String, CountryNationality>? = null

    /**
     * Open link in external activity.
     *
     * @param link link to open
     */
    fun openLink(link: String?) {
        var link = link
        if (StringUtils.isNotBlank(link)) {
            link = getLocalizedLink(link)
            val i = Intent(Intent.ACTION_VIEW)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.data = Uri.parse(link)
            context!!.startActivity(i)
        }
    }

    fun getLocalizedLink(link: String?): String? {
        return StringUtils.replaceOnce(link, "en.wikipedia.org", Locale.getDefault().language + ".wikipedia.org")
    }

    fun openCoordinates(latitude: Float, longitude: Float) {
        val uri = String.format(Locale.ENGLISH, "https://www.google.com/maps/search/?api=1&query=%f,%f", latitude, longitude)

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.`package` = "com.google.android.apps.maps"
            context!!.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            try {
                openLink(uri)
            } catch (innerEx: ActivityNotFoundException) {
                Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show()
            }

        }

    }

    /**
     * Convert UTC date with the specific pattern in local date.
     *
     * @param dateUTCString    UTC string date
     * @param dateUTCPattern   UTC pattern
     * @param dateLocalPattern Local pattern
     * @return local date, empty on error
     */
    fun convertUTCDateToLocal(dateUTCString: String, dateUTCPattern: String, dateLocalPattern: String): String {
        var dateLocal: String

        try {
            val utcFormat = SimpleDateFormat(dateUTCPattern, Locale.getDefault())
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")

            val date = utcFormat.parse(dateUTCString)

            val pstFormat = SimpleDateFormat(dateLocalPattern, Locale.getDefault())
            pstFormat.timeZone = TimeZone.getDefault()

            dateLocal = pstFormat.format(date)
        } catch (e: Exception) {
            dateLocal = ""
        }

        return dateLocal
    }

    /**
     * [CountryNationality] by given nationality
     *
     * @param nationality nationality
     * @return CountryNationality, `null` if doesnt exist
     */
    fun getCountryNationality(nationality: String?): CountryNationality? {
        return getCountriesNationalitiesMap()[nationality]
    }

    private fun getCountriesNationalitiesMap(): Map<String, CountryNationality> {
        if (countriesNationalitiesMap == null) {
            countriesNationalitiesMap = HashMap()
            try {
                context!!.assets.open("countries-nationalities.json").use { `is` ->
                    InputStreamReader(`is`).use { isr ->
                        val type = object : TypeToken<Collection<CountryNationality>>() {

                        }.type
                        val gson = Gson()
                        val reader = JsonReader(isr)
                        val cn = gson.fromJson<Collection<CountryNationality>>(reader, type)
                        for (countryNationality in cn) {
                            val nationalities = StringUtils.split(countryNationality.nationality, ",")
                            for (nat in nationalities) {
                                countriesNationalitiesMap!!.put(StringUtils.trim(nat), countryNationality)
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                countriesNationalitiesMap = HashMap()
            }

        }
        return countriesNationalitiesMap.orEmpty()
    }

    companion object {

        open fun getHtmlText(html: String): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(html)
            }
        }
    }
}
