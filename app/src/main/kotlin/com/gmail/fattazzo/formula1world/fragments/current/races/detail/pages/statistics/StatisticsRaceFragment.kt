/*
 * Project: total-gp-world
 * File: StatisticsRaceFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import android.widget.Spinner
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.lapstime.RaceStatLapsTimeFragment_
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.RaceStatPitStopFragment_
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.RaceStatPositionsFragment_
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.FragmentArg
import org.androidannotations.annotations.ItemSelect
import org.androidannotations.annotations.ViewById

/**
 * @author fattazzo
 *
 *
 * date: 03/07/17
 */
@EFragment(R.layout.fragment_race_statistics)
open class StatisticsRaceFragment : Fragment(), ITitledFragment {
    override val titleResId: Int
        get() = R.string.detail_race_statistics_tab_title

    @FragmentArg
    lateinit internal var race: F1Race

    @ViewById
    lateinit internal var statistics_spinner: Spinner

    @ViewById
    lateinit internal var race_statistics_container: FrameLayout

    @ItemSelect
    fun statistics_spinner(selected: Boolean, position: Int) {
        val fm = fragmentManager
        when (position) {
            1 -> fm!!.beginTransaction().replace(R.id.race_statistics_container, RaceStatPitStopFragment_.builder().race(race).build()).commit()
            2 -> fm!!.beginTransaction().replace(R.id.race_statistics_container, RaceStatPositionsFragment_.builder().race(race).build()).commit()
            3 -> fm!!.beginTransaction().replace(R.id.race_statistics_container, RaceStatLapsTimeFragment_.builder().race(race).build()).commit()
            else -> race_statistics_container!!.removeAllViews()
        }
    }

    companion object {

        fun newInstance(race: F1Race): StatisticsRaceFragment {
            val fragment = StatisticsRaceFragment_()
            val args = Bundle()
            args.putSerializable("race", race)
            fragment.arguments = args
            return fragment
        }
    }
}
