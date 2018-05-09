/*
 * Project: total-gp-world
 * File: AbstractStatsChartFragment.kt
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

import android.graphics.Color
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsDataListAdapter
import com.gmail.fattazzo.formula1world.service.StatisticsService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.ThemeUtils
import org.androidannotations.annotations.*
import java.text.DecimalFormat
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
abstract class AbstractStatsChartFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener {

    @FragmentArg
    @JvmField protected var seasonStart: Int? = 0

    @FragmentArg
    @JvmField protected var seasonEnd: Int? = 0

    @Bean
    lateinit protected var statisticsService: StatisticsService

    @Bean
    lateinit protected var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit protected var themeUtils: ThemeUtils

    @ViewById
    @JvmField internal var chart: PieChart? = null

    @ViewById
    lateinit protected var chartLayout: RelativeLayout

    @ViewById
    @JvmField internal var percentageSwitch: Switch? = null

    @ViewById
    lateinit protected var dataListView: ListView
    @ViewById
    lateinit protected var headerListView: RelativeLayout

    private var lastData: Date? = null

    protected abstract val chartValueFormatter: IValueFormatter

    protected abstract val listValueFormat: DecimalFormat

    @AfterViews
    protected fun initViews() {
        configureChart()

        if (percentageSwitch != null) {
            percentageSwitch!!.setOnCheckedChangeListener(this)
        }

        dataListView.adapter = createListAdapter(ArrayList(), listValueFormat)

        headerListView.removeAllViews()
        val headerView = getHeaderListView()
        if (headerView != null) {
            headerListView.addView(headerView, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT))
            headerListView.invalidate()
            headerListView.forceLayout()
        }

        bindData()
    }

    @AfterInject
    protected fun afterInjenct() {
        lastData = statisticsService.lastRaceData
    }

    private fun configureChart() {
        if (chart != null) {
            chart!!.description.isEnabled = false

            chart!!.centerText = createChartCenterText()
            chart!!.setCenterTextColor(themeUtils.getThemeTextColor(context!!))
            chart!!.setCenterTextSize(themeUtils.getThemeTextSize(context!!, R.dimen.font_size_medium))

            chart!!.isDrawHoleEnabled = true
            chart!!.setHoleColor(Color.TRANSPARENT)

            chart!!.holeRadius = 58f
            chart!!.transparentCircleRadius = 58f

            chart!!.setDrawCenterText(true)

            chart!!.isRotationEnabled = false
            chart!!.isHighlightPerTapEnabled = true

            chart!!.maxAngle = 180f
            chart!!.rotationAngle = 180f
            chart!!.setCenterTextOffset(0f, -45f)

            chart!!.setEntryLabelColor(themeUtils.getThemeTextColor(context!!))
            chart!!.setDrawEntryLabels(false)

            val l = chart!!.legend
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(false)
            l.xEntrySpace = 7f
            l.yEntrySpace = 0f
            l.yOffset = 0f
            l.textColor = themeUtils.getThemeTextColor(context!!)
            l.textSize = themeUtils.getThemeTextSize(context!!, R.dimen.font_size_small)
            l.isEnabled = true

            chart!!.clear()
        }
    }

    private fun createChartCenterText(): SpannableString {
        val outFormat = android.text.format.DateFormat.getDateFormat(context)
        val seasonsTxt: String = if (seasonStart == seasonEnd) {
            getString(R.string.season) + "\n" + seasonStart
        } else {
            getString(R.string.seasons) + "\n" + seasonStart + " - " + seasonEnd
        }
        var text = SpannableString(seasonsTxt)

        val calendar = Calendar.getInstance()
        calendar.time = lastData
        if (seasonEnd!! >= calendar.get(Calendar.YEAR)) {
            text = SpannableString(seasonsTxt + "\n*" + getString(R.string.until) + " " + outFormat.format(lastData))
        }
        text.setSpan(RelativeSizeSpan(1.0f), 0, seasonsTxt.length, 0)
        text.setSpan(RelativeSizeSpan(0.65f), seasonsTxt.length, text.length, 0)
        return text
    }

    private fun setChartData(statsData: List<StatsData>) {
        val values = ArrayList<PieEntry>()

        var entryNr = 0
        for (stsdata in statsData) {
            values.add(PieEntry(stsdata.value, stsdata.label))
            entryNr++

            if (entryNr == 5) {
                break
            }
        }

        val dataSet = PieDataSet(values, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*preferenceManager.statisticsChartColorTheme.colors)

        val data = PieData(dataSet)
        data.setValueTextSize(themeUtils.getThemeTextSize(context!!, R.dimen.font_size_small))
        data.setValueTextColor(themeUtils.getThemeTextColor(context!!))
        chart!!.data = data
        applyChartValueFormatter()

        chart!!.invalidate()
        chart!!.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    private fun setListData(data: List<StatsData>) {
        val valueFormat = listValueFormat
        val adapter = createListAdapter(data, valueFormat)
        dataListView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun bindData() {
        val data = loadData()

        if (chart != null) {
            setChartData(data)
        }
        setListData(data)
    }

    private fun applyChartValueFormatter() {
        if (chart != null && chart!!.data != null) {
            chart!!.setUsePercentValues(percentageSwitch!!.isChecked)
            chart!!.data.setValueFormatter(if (percentageSwitch!!.isChecked) PercentFormatter() else chartValueFormatter)
            chart!!.invalidate()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        applyChartValueFormatter()
        chart!!.animateY(1400, Easing.EasingOption.EaseInOutQuad)
    }

    protected open fun createListAdapter(data: List<StatsData>, valueFormat: DecimalFormat): BaseAdapter {
        return StatsDataListAdapter(activity!!, data, valueFormat)
    }

    protected open fun getHeaderListView(): View? {
        return null
    }

    protected abstract fun loadData(): List<StatsData>

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.statistics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
