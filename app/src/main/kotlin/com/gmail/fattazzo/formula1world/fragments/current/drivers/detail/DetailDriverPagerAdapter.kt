package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment_
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking.RankingDriverFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking.RankingDriverFragment_

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