/*
 * Project: total-gp-world
 * File: DetailDriverPagerAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking.RankingDriverFragment

internal class DetailDriverPagerAdapter(fragmentManager: FragmentManager, private val context: Context, private val driver: F1Driver) : FragmentPagerAdapter(fragmentManager) {
    private var urlViewerFragment: UrlViewerFragment? = null
    private var progressDriverFragment: ProgressDriverFragment? = null
    private var rankingDriverFragment: RankingDriverFragment? = null

    fun getUrlViewerFragment(): UrlViewerFragment {
        if (urlViewerFragment == null) {
            urlViewerFragment = UrlViewerFragment.newInstance(driver.url!!)
        }
        return this!!.urlViewerFragment!!
    }

    fun getProgressDriverFragment(): ProgressDriverFragment {
        if (progressDriverFragment == null) {
            progressDriverFragment = ProgressDriverFragment.newInstance(driver)
        }
        return this!!.progressDriverFragment!!
    }

    fun getRankingDriverFragment(): RankingDriverFragment {
        if (rankingDriverFragment == null) {
            rankingDriverFragment = RankingDriverFragment.newInstance(driver)
        }
        return this!!.rankingDriverFragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return getProgressDriverFragment()
            1 -> return getRankingDriverFragment()
            2 -> return getUrlViewerFragment()
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.resources.getString(R.string.detail_driver_season_progress_tab_title)
            1 -> return context.resources.getString(R.string.detail_driver_ranking_tab_title)
            2 -> return context.resources.getString(R.string.info_fragment_title)
            else -> return null
        }
    }

}