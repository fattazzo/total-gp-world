package com.gmail.fattazzo.formula1world.fragments.stats.constructors

import android.widget.FrameLayout

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment
import com.gmail.fattazzo.formula1world.utils.FragmentUtils

import org.androidannotations.annotations.EFragment

/**
 * @author fattazzo
 *
 *
 * date: 21/08/17
 */
@EFragment(R.layout.fragment_statistics)
open class StatisticsConstructorsFragment : AbstractStatisticsFragment() {
    override val isMultiSeasons: Boolean
        get() = true
    override val statsListResId: Int
        get() = R.array.statistics_constructors_entry

    override fun showStat(index: Int, seasonStart: Int?, seasonEnd: Int?, containerResId: Int, container: FrameLayout?) {
        when (index) {
            1 -> FragmentUtils.replace(activity, ConstructorsWinsStatsFragment_.builder().seasonStart(seasonStart!!).seasonEnd(seasonEnd!!).build(), containerResId)
            2 -> FragmentUtils.replace(activity, ConstructorsNationalityWinsStatsFragment_.builder().seasonStart(seasonStart!!).seasonEnd(seasonEnd!!).build(), containerResId)
            3 -> FragmentUtils.replace(activity, ConstructorsPodiumsStatsFragment_.builder().seasonStart(seasonStart!!).seasonEnd(seasonEnd!!).build(), containerResId)
            4 -> FragmentUtils.replace(activity, ConstructorsNumbersStatsFragment_.builder().seasonStart(seasonStart!!).seasonEnd(seasonEnd!!).build(), containerResId)
            else -> container!!.removeAllViews()
        }
    }

    companion object {

        val TAG = StatisticsConstructorsFragment::class.java.simpleName
    }
}
