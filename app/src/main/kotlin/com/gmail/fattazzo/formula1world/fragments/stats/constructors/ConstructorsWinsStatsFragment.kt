package com.gmail.fattazzo.formula1world.fragments.stats.constructors

import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment

import org.androidannotations.annotations.EFragment

import java.text.DecimalFormat

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
open class ConstructorsWinsStatsFragment : AbstractStatsChartFragment() {
    override val chartValueFormatter: IValueFormatter
        get() = DefaultValueFormatter(0)
    override val listValueFormat: DecimalFormat
        get() = DecimalFormat("0")

    public override fun loadData(): List<StatsData> {
        return statisticsService.loadConstructorsWins(this!!.seasonStart!!, this!!.seasonEnd!!)
    }

}
