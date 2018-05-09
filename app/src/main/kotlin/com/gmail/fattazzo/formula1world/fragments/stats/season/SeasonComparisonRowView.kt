/*
 * Project: total-gp-world
 * File: SeasonComparisonRowView.kt
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

package com.gmail.fattazzo.formula1world.fragments.stats.season

import android.content.Context
import android.content.res.ColorStateList
import android.support.constraint.ConstraintLayout
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

import java.text.DecimalFormat

/**
 * @author fattazzo
 *
 *
 * date: 07/09/17
 */
@EViewGroup(R.layout.season_comparison_row)
open class SeasonComparisonRowView : ConstraintLayout {

    private val titleResId: Int

    private val prevValue: Float
    private val value: Float
    private val nextValue: Float

    @ViewById
    lateinit internal var seasonComparisonTitle: TextView

    @ViewById
    lateinit internal var prevSeasonDataTV: TextView
    @ViewById
    lateinit internal var seasonDataTV: TextView
    @ViewById
    lateinit internal var nextSeasonDataTV: TextView

    constructor(context: Context, titleResId: Int, prevValue: Int, value: Int, nextValue: Int) : super(context) {
        this.titleResId = titleResId
        this.prevValue = prevValue.toFloat()
        this.value = value.toFloat()
        this.nextValue = nextValue.toFloat()
    }

    constructor(context: Context, titleResId: Int, prevValue: Float, value: Float, nextValue: Float) : super(context) {
        this.titleResId = titleResId
        this.prevValue = prevValue
        this.value = value
        this.nextValue = nextValue
    }

    @AfterViews
    internal fun bind() {
        seasonComparisonTitle!!.text = context.getString(titleResId)

        val format = DecimalFormat("0.##")
        prevSeasonDataTV!!.text = format.format(prevValue.toDouble())
        seasonDataTV!!.text = format.format(value.toDouble())
        nextSeasonDataTV!!.text = format.format(nextValue.toDouble())

        val prevDiff = value - prevValue
        prevSeasonDataTV!!.setTextColor(getColor(prevDiff))

        val nextDiff = value - nextValue
        nextSeasonDataTV!!.setTextColor(getColor(nextDiff))
    }

    private fun getColor(diff: Float): ColorStateList? {
        val result: Int
        if (diff > 0) {
            result = R.color.red
        } else if (diff < 0) {
            result = R.color.green
        } else {
            result = R.color.gray
        }
        return resources.getColorStateList(result)
    }
}
