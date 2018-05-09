/*
 * Project: total-gp-world
 * File: RaceItemView.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.LocaleUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.time.DateFormatUtils
import java.util.*

@EViewGroup(R.layout.race_item_list)
open class RaceItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var localeUtils: LocaleUtils

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @ViewById(R.id.race_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.race_item_info)
    lateinit internal var infoView: TextView

    @ViewById(R.id.race_item_flag)
    lateinit internal var flagImageView: ImageView

    fun bind(f1Race: F1Race) {
        nameView.text = f1Race.name

        val currentDateEnd = Calendar.getInstance()
        currentDateEnd.add(Calendar.HOUR_OF_DAY, 2)
        val currentDate = DateFormatUtils.format(currentDateEnd, "yyyy-MM-dd")

        var localeDateString: String? = null
        try {
            if(f1Race.date != null) {
                localeDateString = utils.convertUTCDateToLocal(f1Race.date!!, "yyyy-MM-dd", "dd/MM/yyyy")
            }
        } catch (e: Exception) {
            localeDateString = ""
        }

        infoView.text = localeDateString.orEmpty()

        val countryCode = localeUtils.getCountryCode(f1Race.circuit.location!!.country!!)
        flagImageView.setImageBitmap(imageUtils.getFlagForCountryCode(countryCode))

        val matrix = ColorMatrix()
        matrix.setSaturation(1f)
        if (f1Race.date != null && currentDate < f1Race.date!!) {
            matrix.setSaturation(0f)
        }
        val filter = ColorMatrixColorFilter(matrix)
        flagImageView.colorFilter = filter
    }
}