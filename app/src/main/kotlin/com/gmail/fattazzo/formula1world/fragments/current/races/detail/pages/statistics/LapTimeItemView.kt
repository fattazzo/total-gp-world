/*
 * Project: total-gp-world
 * File: LapTimeItemView.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ThemeUtils

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

@EViewGroup(R.layout.lap_time_item_list)
open class LapTimeItemView(context: Context) : ConstraintLayout(context) {

    @Bean
    lateinit internal var themeUtils: ThemeUtils

    @Bean
    lateinit internal var dataService: DataService

    @ViewById
    lateinit internal var driverName: TextView
    @ViewById
    lateinit internal var lap: TextView
    @ViewById
    lateinit internal var position: TextView
    @ViewById
    lateinit internal var time: TextView

    @ViewById
    lateinit internal var teamColor: ImageView

    fun bind(lapTime: F1LapTime, rowNumber: Int) {

        driverName!!.text = lapTime.driver!!.fullName
        lap!!.text = lapTime.lap.toString()
        position!!.text = lapTime.position.toString()
        time!!.text = lapTime.time

        setBackgroundColor(if (rowNumber % 2 == 0) themeUtils!!.getThemeEvenRowColor(context) else themeUtils!!.getThemeOddRowColor(context))

        val color = dataService!!.loadDriverColor(lapTime.driver)
        teamColor!!.setColorFilter(color)
    }
}