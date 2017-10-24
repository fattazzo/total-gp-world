package com.gmail.fattazzo.formula1world.fragments.stats.constructors

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsBarChartFragment

import org.androidannotations.annotations.EFragment

/**
 * @author fattazzo
 *
 *
 * date: 08/09/17
 */
@EFragment(R.layout.fragment_stats_barchart)
open class ConstructorsNumbersStatsFragment : AbstractStatsBarChartFragment() {

    public override fun loadData(): List<StatsData> {
        return statisticsService.loadConstructorsNumbers(this!!.seasonStart!!, this!!.seasonEnd!!)
    }
}
