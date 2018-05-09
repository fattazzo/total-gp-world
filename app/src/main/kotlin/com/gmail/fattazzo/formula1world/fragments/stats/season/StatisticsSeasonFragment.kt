/*
 * Project: total-gp-world
 * File: StatisticsSeasonFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.stats.season

import android.widget.FrameLayout

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.ConstructorsPodiumsStatsFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.ConstructorsWinsStatsFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.DriversPodiumsStatsFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.DriversWinsStatsFragment_
import com.gmail.fattazzo.formula1world.utils.FragmentUtils

import org.androidannotations.annotations.EFragment

/**
 * @author fattazzo
 *
 *
 * date: 21/08/17
 */
@EFragment(R.layout.fragment_statistics)
open class StatisticsSeasonFragment : AbstractStatisticsFragment() {
    override val isMultiSeasons: Boolean
        get() = false
    override val statsListResId: Int
        get() = R.array.statistics_season_entry

    override fun showStat(index: Int, seasonStart: Int?, seasonEnd: Int?, containerResId: Int, container: FrameLayout?) {
        when (index) {
            1 -> FragmentUtils.replace(activity, SeasonComparisonStatsFragment_.builder().season(seasonEnd!!).build(), containerResId)
            2 -> FragmentUtils.replace(activity, DriversWinsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            3 -> FragmentUtils.replace(activity, ConstructorsWinsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            4 -> FragmentUtils.replace(activity, DriversPodiumsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            5 -> FragmentUtils.replace(activity, ConstructorsPodiumsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            6 -> FragmentUtils.replace(activity, SeasonDriversCircuitsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            7 -> FragmentUtils.replace(activity, SeasonConstructorsCircuitsStatsFragment_.builder().seasonStart(seasonEnd!!).seasonEnd(seasonEnd).build(), containerResId)
            else -> container!!.removeAllViews()
        }
    }

    companion object {

        val TAG = StatisticsSeasonFragment::class.java.simpleName
    }
}
