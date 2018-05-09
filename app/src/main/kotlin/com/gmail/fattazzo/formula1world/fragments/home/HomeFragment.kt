/*
 * Project: total-gp-world
 * File: HomeFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments.home

import android.view.MenuItem
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity.Companion.PREF_ACTIVITY_RESULT
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity_
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.home.circuit.CurrentCircuitTask
import com.gmail.fattazzo.formula1world.fragments.home.constructorstandings.CurrentConstructorStandingsTask
import com.gmail.fattazzo.formula1world.fragments.home.driverstandings.CurrentDriverStandingsTask
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.IssueReporter
import com.gmail.fattazzo.formula1world.utils.Utils
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 15/04/17
 */
@OptionsMenu(R.menu.home)
@EFragment(R.layout.content_home)
open class HomeFragment : BaseFragment() {

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var currentCircuitTask: CurrentCircuitTask

    @Bean
    lateinit internal var currentDriverStandingsTask: CurrentDriverStandingsTask

    @Bean
    lateinit internal var currentConstructorStandingsTask: CurrentConstructorStandingsTask

    @Bean
    lateinit internal var dataService: DataService

    @AfterViews
    internal fun init() {
        currentCircuitTask!!.loadCurrentSchedule()
        currentDriverStandingsTask!!.loadCurrentStandings()
        currentConstructorStandingsTask!!.loadCurrentStandings()
    }

    @Click
    internal fun fabClicked() {
        dataService!!.clearColorsCache()

        currentCircuitTask!!.loadCurrentSchedule(true)
        currentDriverStandingsTask!!.loadCurrentStandings(true)
        currentConstructorStandingsTask!!.loadCurrentStandings(true)
    }

    override fun backPressed() {
        LovelyStandardDialog(activity)
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.app_name)
                .setMessage(R.string.app_exit_comfirmation)
                .setPositiveButton(android.R.string.ok) { activity?.finish() }
                .setNegativeButton(android.R.string.no, null)
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        when (id) {
            R.id.action_settings -> {
                SettingsActivity_.intent(context).startForResult(PREF_ACTIVITY_RESULT)
                return true
            }
            R.id.action_report_bug -> {
                IssueReporter.openReportIssue(activity!!, null, null, true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val TAG = HomeFragment::class.java.simpleName
    }
}
