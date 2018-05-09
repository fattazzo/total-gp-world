/*
 * Project: total-gp-world
 * File: AbstractStatsCircuitsFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.stats

import android.view.View
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.RelativeLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsCircuitsDataListAdapter
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.service.StatisticsService
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.LocaleUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 09/10/17
 */
@EFragment(R.layout.fragment_stats_circuits)
abstract class AbstractStatsCircuitsFragment : BaseFragment() {

    @FragmentArg
    @JvmField protected var seasonStart: Int? = 0

    @FragmentArg
    @JvmField protected var seasonEnd: Int? = 0

    @Bean
    lateinit protected var statisticsService: StatisticsService

    @Bean
    lateinit protected var dataService: DataService

    @Bean
    lateinit protected var localeUtils: LocaleUtils

    @Bean
    lateinit protected var imageUtils: ImageUtils

    @Bean
    lateinit protected var utils: Utils

    @ViewById
    lateinit protected var dataListView: ExpandableListView

    @ViewById
    lateinit protected var headerListView: RelativeLayout

    private var lastData: Date? = null

    @AfterViews
    protected fun initViews() {

        dataListView!!.setAdapter(createListAdapter(ArrayList()))

        headerListView!!.removeAllViews()
        val headerView = getHeaderListView()
        if (headerView != null) {
            headerListView!!.addView(headerView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT))
            headerListView!!.invalidate()
            headerListView!!.forceLayout()
        }

        bindData()
    }

    @AfterInject
    protected fun afterInjenct() {
        lastData = statisticsService!!.lastRaceData
    }

    protected fun createListAdapter(data: List<StatsCircuitsData>): BaseExpandableListAdapter {
        return StatsCircuitsDataListAdapter(activity!!, data, dataService!!, localeUtils!!, imageUtils!!, utils!!)
    }

    internal fun bindData() {
        val data = loadData()

        setListData(data)
    }

    private fun setListData(data: List<StatsCircuitsData>) {
        val adapter = createListAdapter(data)
        dataListView!!.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    protected fun getHeaderListView(): View? {
        return null
    }

    protected abstract fun loadData(): List<StatsCircuitsData>
}
