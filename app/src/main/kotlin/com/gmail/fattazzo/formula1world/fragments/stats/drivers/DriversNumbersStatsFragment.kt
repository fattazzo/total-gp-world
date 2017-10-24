package com.gmail.fattazzo.formula1world.fragments.stats.drivers

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsBarChartFragment

import org.androidannotations.annotations.EFragment

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_barchart)
open class DriversNumbersStatsFragment : AbstractStatsBarChartFragment() {

    public override fun loadData(): List<StatsData> {
        return statisticsService.loadDriversNumbers(this!!.seasonStart!!, this!!.seasonEnd!!)
    }
}
