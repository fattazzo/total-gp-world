/*
 * Project: total-gp-world
 * File: DetailConstructorPagerAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress.ProgressConstructorFragment

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