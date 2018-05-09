/*
 * Project: total-gp-world
 * File: QualificationsRaceFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.TableLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Qualification
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment
import com.gmail.fattazzo.formula1world.service.DataService
import org.androidannotations.annotations.*

@EFragment(R.layout.fragment_race_qualifications)
open class QualificationsRaceFragment : Fragment(), ITitledFragment, SwipeRefreshLayout.OnRefreshListener {
    override val titleResId: Int
        get() = R.string.detail_race_qualifications_tab_title

    @FragmentArg
    lateinit internal var race: F1Race

    @ViewById
    lateinit internal var table_Layout: TableLayout

    @ViewById
    lateinit internal var swipe_refresh_layout: SwipeRefreshLayout

    @Bean
    lateinit internal var dataService: DataService

    private var raceQualifications: List<F1Qualification>? = null

    @AfterViews
    internal fun init() {
        table_Layout!!.removeViews(1, table_Layout!!.childCount - 1)

        swipe_refresh_layout!!.setOnRefreshListener(this)

        load()
    }

    @UiThread
    internal open fun startLoad() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout!!.isRefreshing = true
        }
    }

    @Background
    internal open fun load() {
        startLoad()

        raceQualifications = dataService!!.loadQualification(race!!)
        updateUI()
    }

    @UiThread
    internal open fun updateUI() {
        try {
            if (table_Layout != null) {
                table_Layout!!.removeViews(1, table_Layout!!.childCount - 1)

                var rowNumber = 1
                for (result in raceQualifications!!) {
                    val row = QualificationsRaceItemView_.build(activity, result, rowNumber)
                    table_Layout!!.addView(row, TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT))
                    rowNumber++
                }
            }
        } finally {
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout!!.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        dataService!!.clearRaceQualifications(race!!)
        load()
    }

    companion object {

        fun newInstance(race: F1Race): QualificationsRaceFragment {
            val resultsFragment = QualificationsRaceFragment_()
            val args = Bundle()
            args.putSerializable("race", race)
            resultsFragment.arguments = args
            return resultsFragment
        }
    }
}