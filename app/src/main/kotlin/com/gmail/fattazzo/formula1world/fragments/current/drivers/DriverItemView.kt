/*
 * Project: total-gp-world
 * File: DriverItemView.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.drivers

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.StringUtils

@EViewGroup(R.layout.drivers_item_list)
open class DriverItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @ViewById(R.id.driver_item_number)
    lateinit internal var numberView: TextView

    @ViewById(R.id.driver_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.driver_item_dateOfBirth)
    lateinit internal var dateOfBirthView: TextView

    @ViewById(R.id.driver_item_flag)
    lateinit internal var flagImageView: ImageView

    @ViewById
    lateinit internal var driver_color: ImageView

    fun bind(driver: F1Driver) {
        numberView!!.text = driver.number.toString()
        nameView!!.text = driver.fullName

        var localeDateString: String
        try {
            val localDateFormat = android.text.format.DateFormat.getDateFormat(this.context.applicationContext)
            localeDateString = localDateFormat.format(driver.dateOfBirth)
        } catch (e: Exception) {
            localeDateString = ""
        }

        dateOfBirthView!!.text = (if (StringUtils.isNotBlank(driver.code)) driver.code!! + " " else "") + localeDateString

        val countryNationality = utils!!.getCountryNationality(driver.nationality)
        if (countryNationality != null) {
            flagImageView!!.setImageBitmap(imageUtils!!.getFlagForCountryCode(countryNationality.alpha2Code!!))
        }

        val color = dataService!!.loadDriverColor(driver)
        driver_color!!.setBackgroundColor(color)
    }
}