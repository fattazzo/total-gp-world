package com.gmail.fattazzo.formula1world.fragments.stats

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.service.StatisticsService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.ThemeUtils
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_barchart)
abstract class AbstractStatsBarChartFragment : BaseFragment() {

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
    lateinit protected var chart: HorizontalBarChart

    @ViewById
    lateinit protected var title: TextView

    private var lastData: Date? = null

    @AfterViews
    protected fun initViews() {
        configureChart()

        bindData()

        title!!.text = createTitle()
    }

    @AfterInject
    protected fun afterInjenct() {
        lastData = statisticsService!!.lastRaceData
    }

    private fun createTitle(): SpannableString {
        val outFormat = android.text.format.DateFormat.getDateFormat(context)
        val seasonsTxt: String
        if (seasonStart == seasonEnd) {
            seasonsTxt = getString(R.string.season) + " " + seasonStart
        } else {
            seasonsTxt = getString(R.string.seasons) + " " + seasonStart + " - " + seasonEnd
        }
        var text = SpannableString(seasonsTxt)

        val calendar = Calendar.getInstance()
        calendar.time = lastData
        if (seasonEnd!! >= calendar.get(Calendar.YEAR)) {
            text = SpannableString(seasonsTxt + " *" + getString(R.string.until) + " " + outFormat.format(lastData))
        }
        text.setSpan(RelativeSizeSpan(1.0f), 0, seasonsTxt.length, 0)
        text.setSpan(RelativeSizeSpan(0.65f), seasonsTxt.length, text.length, 0)
        return text
    }

    private fun configureChart() {
        if (chart != null) {

            chart!!.setDrawBarShadow(false)

            chart!!.setDrawValueAboveBar(true)

            chart!!.setMaxVisibleValueCount(30)

            chart!!.description.isEnabled = false

            chart!!.setVisibleXRangeMaximum(30f)

            chart!!.setPinchZoom(false)

            chart!!.setDrawGridBackground(false)

            val xl = chart!!.xAxis
            xl.position = XAxis.XAxisPosition.BOTTOM
            xl.setDrawAxisLine(true)
            xl.setDrawGridLines(false)
            xl.granularity = 0f
            xl.textColor = themeUtils!!.getThemeTextColor(context)
            xl.textSize = themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small)

            val yl = chart!!.axisLeft
            yl.setDrawAxisLine(true)
            yl.setDrawGridLines(true)
            yl.axisMinimum = 0f
            yl.textColor = themeUtils!!.getThemeTextColor(context)
            yl.textSize = themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small)

            val yr = chart!!.axisRight
            yr.setDrawAxisLine(true)
            yr.setDrawGridLines(false)
            yr.axisMinimum = 1f
            yr.textColor = themeUtils!!.getThemeTextColor(context)
            yr.textSize = themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small)

            chart!!.legend.isEnabled = false

            chart!!.clear()
        }
    }

    private fun setChartData(statsData: List<StatsData>) {

        val values = ArrayList<BarEntry>()

        for (stsdata in statsData) {
            values.add(BarEntry(java.lang.Float.parseFloat(stsdata.label), stsdata.value))
        }

        val set1 = BarDataSet(values, "DataSet")

        set1.setColors(*preferenceManager!!.statisticsChartColorTheme.colors)
        set1.valueTextSize = themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small)
        set1.valueTextColor = themeUtils!!.getThemeTextColor(context)

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val data = BarData(dataSets)
        data.setValueTextSize(themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small))
        data.setValueTextColor(themeUtils!!.getThemeTextColor(context))
        chart!!.data = data

        chart!!.invalidate()

        chart!!.setFitBars(true)
        chart!!.animateY(1400)
    }

    internal fun bindData() {
        val data = loadData()

        if (chart != null) {
            setChartData(data)
        }
    }

    protected abstract fun loadData(): List<StatsData>

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.statistics, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
