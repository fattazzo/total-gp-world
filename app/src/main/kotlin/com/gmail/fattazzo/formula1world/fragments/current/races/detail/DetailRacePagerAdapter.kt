/*
 * Project: total-gp-world
 * File: DetailRacePagerAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications.QualificationsRaceFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.StatisticsRaceFragment
import java.util.*

internal class DetailRacePagerAdapter(fragmentManager: FragmentManager, private val context: Context, private val race: F1Race, hasResults: Boolean, hasQualifications: Boolean) : FragmentPagerAdapter(fragmentManager) {

    private val detailFragments = ArrayList<Fragment>()

    init {

        initSection(hasResults, hasQualifications)
    }

    private fun initSection(hasResults: Boolean, hasQualifications: Boolean) {
        if (hasResults) {
            detailFragments.add(ResultsRaceFragment.newInstance(race))
            detailFragments.add(StatisticsRaceFragment.newInstance(race))
        }
        if (hasQualifications) {
            detailFragments.add(QualificationsRaceFragment.newInstance(race))
        }
        detailFragments.add(UrlViewerFragment.newInstance(race.circuit.url!!))
    }

    override fun getCount(): Int {
        return detailFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return detailFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString((detailFragments[position] as ITitledFragment).titleResId)
    }

}