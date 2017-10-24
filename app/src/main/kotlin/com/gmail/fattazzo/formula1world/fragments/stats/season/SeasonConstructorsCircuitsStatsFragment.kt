package com.gmail.fattazzo.formula1world.fragments.stats.season

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsCircuitsFragment

import org.androidannotations.annotations.EFragment

/**
 * @author fattazzo
 *
 *
 * date: 09/10/17
 */
@EFragment(R.layout.fragment_stats_circuits)
open class SeasonConstructorsCircuitsStatsFragment : AbstractStatsCircuitsFragment() {

    override fun loadData(): List<StatsCircuitsData> {
        return statisticsService.loadCircuitsWinner(this!!.seasonStart!!, StatsCircuitsData.Type.CONSTRUCTORS_WINNER)
    }
}
