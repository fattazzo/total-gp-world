/*
 * Project: total-gp-world
 * File: RaceStatLapsTimeFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.lapstime

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.AbstractRaceLapTimeFragment
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerLapTimeView
import org.androidannotations.annotations.EFragment
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 18/09/17
 */
@EFragment(R.layout.fragment_race_stat_chart_drivers)
open class RaceStatLapsTimeFragment : AbstractRaceLapTimeFragment() {

    override fun buildDataSet(results: List<F1LapTime>, driver: F1Driver): LineDataSet {

        Collections.sort(results) { o1, o2 -> o1.lap - o2.lap }

        val entries = ArrayList<Entry>()
        for (lapTime in results) {
            lapTime.driver = driver
            entries.add(Entry(lapTime.lap.toFloat(), lapTime.milliseconds.toFloat(), lapTime))
        }

        val dataSet = LineDataSet(entries, driver.fullName)
        dataSet.lineWidth = 4f
        val color = dataService.loadDriverColor(driver)
        dataSet.color = color
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawCircles(true)
        dataSet.setDrawValues(false)

        if (color == themeUtils.getThemeBGColor(context!!)) {
            dataSet.enableDashedLine(10f, 5f, 0f)
            dataSet.color = textColor
        }
        return dataSet
    }

    override fun configureChart(chart: LineChart?) {
        chart!!.description.isEnabled = false
        chart.setDrawGridBackground(false)

        if (chart.data != null) {
            chart.data.isHighlightEnabled = false
            chart.invalidate()
        }

        chart.xAxis.textColor = textColor
        chart.xAxis.textSize = textSize
        chart.xAxis.axisLineColor = textColor
        chart.xAxis.granularity = 1.0f
        chart.xAxis.isGranularityEnabled = true
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawGridLines(false)

        chart.axisLeft.textColor = textColor
        chart.axisLeft.textSize = textSize
        chart.axisLeft.axisLineColor = textColor
        chart.axisLeft.granularity = 1f
        chart.axisLeft.isGranularityEnabled = true
        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.setDrawLabels(false)

        chart.legend.isEnabled = false

        chart.axisRight.isEnabled = false

        val marker = F1MarkerLapTimeView(context!!)
        chart.marker = marker
    }

    override fun sortLapTimesForList(lapsListView: List<F1LapTime>) {
        Collections.sort(lapsListView) { o1, o2 ->
            if (o1.lap - o2.lap == 0) {
                o1.milliseconds - o2.milliseconds
            } else {
                o1.lap - o2.lap
            }
        }
    }
}
