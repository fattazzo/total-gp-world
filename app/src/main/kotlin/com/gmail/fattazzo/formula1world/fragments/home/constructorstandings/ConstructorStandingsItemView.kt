/*
 * Project: total-gp-world
 * File: ConstructorStandingsItemView.kt
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

package com.gmail.fattazzo.formula1world.fragments.home.constructorstandings

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings
import com.gmail.fattazzo.formula1world.service.DataService

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.ObjectUtils

@EViewGroup(R.layout.standings_item_list)
open class ConstructorStandingsItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var dataService: DataService

    @ViewById(R.id.standings_item_points)
    lateinit internal var pointsView: TextView

    @ViewById(R.id.standings_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.standings_item_color)
    lateinit internal var teamColorView: ImageView

    fun bind(standings: F1ConstructorStandings) {
        val points = ObjectUtils.defaultIfNull(standings.points, 0f)
        val hasDecimals = points!! % 1 != 0f
        if (hasDecimals) {
            pointsView!!.text = points.toString()
        } else {
            pointsView!!.text = points.toInt().toString()
        }

        if (standings.constructor != null) {
            nameView!!.text = standings.constructor!!.name
        }

        val color = dataService!!.loadContructorColor(standings.constructor)
        teamColorView!!.setColorFilter(color)
    }
}