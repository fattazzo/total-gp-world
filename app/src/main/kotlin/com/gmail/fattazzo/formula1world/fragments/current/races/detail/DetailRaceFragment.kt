/*
 * Project: total-gp-world
 * File: DetailRaceFragment.kt
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

import android.support.v4.view.ViewPager
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment_
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*
import org.apache.commons.collections4.CollectionUtils


/**
 * @author fattazzo
 *
 *
 * date: 19/04/17
 */
@EFragment(R.layout.view_pager_fragment)
open class DetailRaceFragment : BaseFragment() {

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @FragmentArg
    lateinit internal var race: F1Race

    @ViewById(R.id.title)
    lateinit internal var driverNameView: TextView

    @ViewById(R.id.view_pager)
    lateinit internal var vpPager: ViewPager

    lateinit internal var adapterViewPager: DetailRacePagerAdapter

    private var hasResults = false
    private var hasQualifications = false

    @AfterViews
    internal fun initView() {
        driverNameView!!.text = race!!.name

        vpPager!!.setPageTransformer(true, preferenceManager!!.pagerTansactionAnimation)

        vpPager!!.clipToPadding = false
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager!!.setPadding(0, 0, 0, 0)
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager!!.pageMargin = 90

        init()
    }

    @Background
    internal open fun loadRacesData() {
        hasResults = CollectionUtils.isNotEmpty(dataService!!.loadRaceResult(race!!))
        hasQualifications = CollectionUtils.isNotEmpty(dataService!!.loadQualification(race!!))

        initPagerAdapter()
    }

    @UiThread
    internal open fun init() {
        loadRacesData()
    }

    @UiThread
    internal open fun initPagerAdapter() {
        adapterViewPager = DetailRacePagerAdapter(this.childFragmentManager, activity!!, race, hasResults, hasQualifications)
        vpPager!!.adapter = adapterViewPager
        vpPager!!.forceLayout()
    }

    override fun backPressed() {
        val fragmentManager = activity!!.supportFragmentManager

        if (fragmentManager.findFragmentByTag(CurrentRacesFragment.TAG) != null) {
            FragmentUtils.replace(activity, HomeFragment_())
        } else {
            FragmentUtils.replace(activity, CurrentRacesFragment_())
        }
    }

    companion object {

        val TAG = DetailRaceFragment::class.java.simpleName
    }
}
