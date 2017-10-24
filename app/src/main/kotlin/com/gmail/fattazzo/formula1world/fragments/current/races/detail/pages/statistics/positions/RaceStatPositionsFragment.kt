package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.AbstractRaceLapTimeFragment
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerEntryDataView

import org.androidannotations.annotations.EFragment

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 11/07/17
 */
@EFragment(R.layout.fragment_race_stat_chart_drivers)
open class RaceStatPositionsFragment : AbstractRaceLapTimeFragment() {

    override fun buildDataSet(results: List<F1LapTime>, driver: F1Driver): LineDataSet {

        Collections.sort(results) { o1, o2 -> o1.lap - o2.lap }

        val entries = ArrayList<Entry>()
        for (lapTime in results) {
            entries.add(Entry(lapTime.lap.toFloat(), lapTime.position.toFloat(), driver.fullName))
        }

        val dataSet = LineDataSet(entries, driver.fullName)
        dataSet.lineWidth = 4f
        val color = dataService.loadDriverColor(driver)
        dataSet.color = color
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)

        if (color == themeUtils.getThemeBGColor(context)) {
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
        chart.axisLeft.granularity = 1.0f
        chart.axisLeft.isGranularityEnabled = true
        chart.axisLeft.setDrawGridLines(false)
        chart.axisLeft.isInverted = true

        chart.legend.isEnabled = false

        chart.axisRight.isEnabled = false

        val marker = F1MarkerEntryDataView(context, null, resources.getString(R.string.detail_driver_position))
        chart.marker = marker
    }

    override fun sortLapTimesForList(lapsListView: List<F1LapTime>) {
        Collections.sort(lapsListView) { o1, o2 ->
            if (o1.lap - o2.lap == 0) {
                o1.position - o2.position
            } else {
                o1.lap - o2.lap
            }
        }
    }
}
