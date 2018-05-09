/*
 * Project: total-gp-world
 * File: CurrentRacesFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races

import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ListView
import android.widget.RelativeLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.DetailRaceFragment_
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 26/05/17
 */
@EFragment(R.layout.fragment_swipe_listview)
open class CurrentRacesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var racesListAdapter: RacesListAdapter

    @ViewById(R.id.list_view)
    lateinit internal var listView: ListView

    @ViewById(R.id.swipe_refresh_layout)
    lateinit internal var swipeRefreshLayout: SwipeRefreshLayout

    @ViewById(R.id.details_fragment_container)
    @JvmField internal var detailsRaceFragmentContainer: RelativeLayout? = null

    @AfterViews
    internal fun init() {
        listView!!.adapter = racesListAdapter
        swipeRefreshLayout!!.setOnRefreshListener(this)
        if (racesListAdapter!!.isEmpty) {
            loadRaces()
        }
    }

    @Background
    open fun loadRaces() {
        var result: List<F1Race>? = null
        try {
            startLoad()
            result = dataService!!.loadRaces()
        } finally {
            updateUI(result)
        }
    }

    @UiThread
    internal open fun startLoad() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout!!.isRefreshing = true
        }
    }

    @UiThread
    internal open fun updateUI(result: List<F1Race>?) {
        try {
            racesListAdapter!!.clearItems()
            racesListAdapter!!.setRaces(result!!)
            racesListAdapter!!.notifyDataSetChanged()
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
    }

    @ItemClick(R.id.list_view)
    fun itemClicked(position: Int) {
        val race = racesListAdapter!!.getItem(position)

        val container: Int
        if (detailsRaceFragmentContainer == null) {
            container = R.id.container
        } else {
            container = R.id.details_fragment_container
        }

        FragmentUtils.replace(activity, DetailRaceFragment_.builder().race(race).build(), container)

    }

    override fun onRefresh() {
        dataService!!.clearRacesCache()
        loadRaces()
    }

    companion object {

        val TAG = CurrentRacesFragment::class.java.simpleName
    }
}
