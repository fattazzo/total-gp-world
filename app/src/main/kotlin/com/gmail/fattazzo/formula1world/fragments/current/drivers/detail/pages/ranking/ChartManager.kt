package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Result
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerYValueView
import com.gmail.fattazzo.formula1world.view.chart.valueformatter.AlternateChartValueFormatter
import com.gmail.fattazzo.formula1world.view.chart.valueformatter.DefaultChartValueFormatter
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 23/04/17
 */
@EBean
internal open class ChartManager {

    @RootContext
    lateinit var context: Context

    @Bean
    lateinit var dataService: DataService

    private var textColor: Int = 0
    private var textSize: Float = 0.toFloat()

    @AfterInject
    fun init() {
        textColor = getThemeTextColor(context)
        textSize = getThemeTextSize(context)
    }

    private fun buildDataSet(results: List<F1Result>, type: DataSetType): LineDataSet {
        var driverName = ""
        val entries = ArrayList<Entry>()
        var color = -1
        var points = 0f
        var idx = 0
        for (result in results) {
            points = points + result.points

            val evenData = idx % 2 == 0
            when (type) {
                ChartManager.DataSetType.POSITIONS -> entries.add(Entry(result.race!!.round.toFloat(), result.positionOrder.toFloat(), evenData))
                ChartManager.DataSetType.POINTS -> entries.add(Entry(result.race!!.round.toFloat(), points, evenData))
            }
            if (color == -1) {
                color = dataService!!.loadContructorColor(result.constructor)
            }
            driverName = if (result.driver != null) result.driver!!.fullName else ""
            idx++
        }


        val dataSet = LineDataSet(entries, driverName)
        dataSet.setCircleColor(color)
        return dataSet
    }

    fun loadPointsChartData(pointsChart: LineChart, results: List<F1Result>, leaderResults: List<F1Result>) {

        val pointsDataSet = buildDataSet(results, DataSetType.POINTS)
        var leaderPointsDataSet: LineDataSet? = null
        if (!leaderResults.isEmpty()) {
            leaderPointsDataSet = buildDataSet(leaderResults, DataSetType.POINTS)
            leaderPointsDataSet.lineWidth = 4f
            var color: Int
            try {
                color = leaderPointsDataSet.getCircleColor(0)
            } catch (e: Exception) {
                color = getThemeBgInvertedColor(context)
            }

            leaderPointsDataSet.color = color
            leaderPointsDataSet.valueTextColor = textColor
            leaderPointsDataSet.valueTextSize = textSize
            if (leaderPointsDataSet.entryCount < 15) {
                leaderPointsDataSet.valueFormatter = DefaultChartValueFormatter()
            } else {
                leaderPointsDataSet.valueFormatter = AlternateChartValueFormatter()
            }
        }

        val pointsLineData: LineData
        if (leaderPointsDataSet == null || StringUtils.equals(pointsDataSet.label, leaderPointsDataSet.label)) {
            pointsLineData = LineData(pointsDataSet)
        } else {
            pointsLineData = LineData(pointsDataSet, leaderPointsDataSet)
        }
        pointsChart.data = pointsLineData
        configureDataSet(pointsDataSet, pointsChart.axisLeft.axisMinimum)
    }

    fun loadPositionsChartData(positionChart: LineChart, results: List<F1Result>) {

        val positionDataSet = buildDataSet(results, DataSetType.POSITIONS)
        val positionLineData = LineData(positionDataSet)
        positionChart.axisLeft.isInverted = true
        positionChart.legend.isEnabled = false
        positionChart.data = positionLineData
        configureDataSet(positionDataSet, positionChart.axisLeft.axisMaximum)
    }

    fun configureChart(chart: LineChart) {
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)

        if (chart.data != null) {
            chart.data.isHighlightEnabled = false
            chart.invalidate()
        }

        chart.setTouchEnabled(true)
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.setPinchZoom(false)

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

        chart.legend.textColor = textColor
        chart.legend.textSize = textSize

        chart.axisRight.isEnabled = false

        val marker = F1MarkerYValueView(context!!)
        //marker.setChartView(chart);
        chart.marker = marker
    }

    private fun configureDataSet(dataSet: LineDataSet, fillPosition: Float) {
        dataSet.color = textColor
        dataSet.valueTextSize = textSize
        dataSet.setDrawCircles(true)
        dataSet.lineWidth = 4f
        dataSet.valueTextColor = textColor
        dataSet.setDrawFilled(true)

        if (dataSet.entryCount < 15) {
            dataSet.valueFormatter = DefaultChartValueFormatter()
        } else {
            dataSet.valueFormatter = AlternateChartValueFormatter()
        }


        var colors: IntArray
        try {
            colors = intArrayOf(dataSet.getCircleColor(0), Color.parseColor("#00ffffff"))
        } catch (e: Exception) {
            colors = intArrayOf(Color.parseColor("#00ffffff"), Color.parseColor("#00ffffff"))
        }

        val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors)
        dataSet.fillDrawable = gd

        dataSet.fillFormatter = IFillFormatter { dataSet, dataProvider -> fillPosition }
    }

    private fun getThemeTextColor(context: Context?): Int {
        val value = TypedValue()
        context!!.theme.resolveAttribute(android.R.attr.textColor, value, true)
        return value.data
    }

    private fun getThemeTextSize(context: Context?): Float {
        val scaleRatio = ObjectUtils.defaultIfNull(context!!.resources.displayMetrics.density, 1f)
        val dimenPix = ObjectUtils.defaultIfNull(context.resources.getDimension(R.dimen.font_size_small), 16f)
        return dimenPix / scaleRatio
    }

    private fun getThemeBgInvertedColor(context: Context?): Int {
        val value = TypedValue()
        context!!.theme.resolveAttribute(R.attr.bgBackgroundInverted, value, true)
        return value.data
    }

    private enum class DataSetType {
        POSITIONS, POINTS
    }
}
