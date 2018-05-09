/*
 * Project: total-gp-world
 * File: DriversSpinnerAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import java.util.*

@EBean
open class DriversSpinnerAdapter : BaseAdapter() {

    @RootContext
    lateinit internal var context: Context

    private var drivers: MutableList<DriverSpinnerModel>? = null

    val items: List<DriverSpinnerModel>?
        get() = drivers

    fun setDrivers(drivers: Collection<DriverSpinnerModel>) {
        this.drivers = ArrayList()
        CollectionUtils.addAll(this.drivers, CollectionUtils.emptyIfNull(drivers))

        Collections.sort(this.drivers!!) { o1, o2 ->
            val constr = o1.constructor!!.constructorRef!!.compareTo(o2.constructor!!.constructorRef!!)
            if (constr != 0) {
                constr
            } else {
                o1.driver!!.driverRef!!.compareTo(o2.driver!!.driverRef!!)
            }
        }
    }

    @AfterInject
    internal fun initAdapter() {
        drivers = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val driverItemView: DriverItemView
        if (convertView == null) {
            driverItemView = DriverItemView_.build(context)
        } else {
            driverItemView = convertView as DriverItemView
        }

        driverItemView.bind(getItem(position))

        return driverItemView
    }

    override fun getCount(): Int {
        return drivers!!.size
    }

    override fun getItem(position: Int): DriverSpinnerModel {
        return drivers!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    internal fun clearItems() {
        drivers!!.clear()
    }
}