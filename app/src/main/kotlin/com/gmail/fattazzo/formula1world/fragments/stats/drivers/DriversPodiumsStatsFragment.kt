/*
 * Project: total-gp-world
 * File: DriversPodiumsStatsFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.stats.drivers

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsDataPodiumsListAdapter
import org.androidannotations.annotations.EFragment
import java.text.DecimalFormat

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
open class DriversPodiumsStatsFragment : AbstractStatsChartFragment() {
    override val chartValueFormatter: IValueFormatter
        get() = DefaultValueFormatter(0)
    override val listValueFormat: DecimalFormat
        get() = DecimalFormat("0")

    public override fun loadData(): List<StatsData> {
        return statisticsService.loadDriversPodiums(this.seasonStart!!, this.seasonEnd!!)
    }

    override fun createListAdapter(data: List<StatsData>, valueFormat: DecimalFormat): BaseAdapter {
        return StatsDataPodiumsListAdapter(activity!!, data, valueFormat)
    }

    override fun getHeaderListView(): View? {
        val inflater = activity?.layoutInflater
        val header = inflater?.inflate(R.layout.stats_four_values_row, null, false) as ViewGroup
        (header.findViewById<TextView>(R.id.textLeft)).text = getString(R.string.stats_pos)
        (header.findViewById<TextView>(R.id.textLabel)).text = getString(R.string.drivers)
        (header.findViewById<TextView>(R.id.textValueOne)).text = getString(R.string.stats_total)
        (header.findViewById<TextView>(R.id.textValueTwo)).text = getString(R.string.stats_first)
        (header.findViewById<TextView>(R.id.textValueThree)).text = getString(R.string.stats_second)
        (header.findViewById<TextView>(R.id.textValueFour)).text = getString(R.string.stats_third)
        return header
    }
}
