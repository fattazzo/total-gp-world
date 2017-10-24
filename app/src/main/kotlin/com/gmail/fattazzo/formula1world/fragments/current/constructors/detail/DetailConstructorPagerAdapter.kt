package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress.ProgressConstructorFragment
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress.ProgressConstructorFragment_

internal class DetailConstructorPagerAdapter(fragmentManager: FragmentManager, private val context: Context, private val constructor: F1Constructor) : FragmentPagerAdapter(fragmentManager) {

    private var urlViewerFragment: UrlViewerFragment? = null
    private var progressConstructorFragment: ProgressConstructorFragment? = null

    private fun getUrlViewerFragment(): UrlViewerFragment {
        if (urlViewerFragment == null) {
            urlViewerFragment = UrlViewerFragment.newInstance(constructor.url!!)
        }
        return this!!.urlViewerFragment!!
    }

    private fun getProgressConstructorFragment(): ProgressConstructorFragment {
        if (progressConstructorFragment == null) {
            progressConstructorFragment = ProgressConstructorFragment.newInstance(constructor)
        }
        return progressConstructorFragment as ProgressConstructorFragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return getProgressConstructorFragment()
            1 -> return getUrlViewerFragment()
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.resources.getString(R.string.detail_driver_season_progress_tab_title)
            1 -> return context.resources.getString(R.string.info_fragment_title)
            else -> return null
        }
    }

}