/*
 * Project: total-gp-world
 * File: LapTimeListAdapter.kt
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
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

@EBean
open class LapTimeListAdapter : BaseAdapter() {

    @RootContext
    lateinit internal var context: Context

    private var lapTimes: MutableList<F1LapTime>? = null

    fun setLapTimes(lapTimes: MutableList<F1LapTime>) {
        this.lapTimes = lapTimes
    }

    @AfterInject
    internal fun initAdapter() {
        lapTimes = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val lapTimeItemView: LapTimeItemView
        if (convertView == null) {
            lapTimeItemView = LapTimeItemView_.build(context)
        } else {
            lapTimeItemView = convertView as LapTimeItemView
        }

        lapTimeItemView.bind(getItem(position), position)

        return lapTimeItemView
    }

    override fun getCount(): Int {
        return lapTimes!!.size
    }

    override fun getItem(position: Int): F1LapTime {
        return lapTimes!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        lapTimes!!.clear()
    }

    internal fun removeItems(driver: F1Driver) {
        val iterator = lapTimes!!.iterator()
        while (iterator.hasNext()) {
            val lapTime = iterator.next()
            if (lapTime.driver!!.driverRef == driver.driverRef) {
                iterator.remove()
            }
        }
    }
}