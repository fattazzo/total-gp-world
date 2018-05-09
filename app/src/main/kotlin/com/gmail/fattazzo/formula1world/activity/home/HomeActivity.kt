/*
 * Project: total-gp-world
 * File: HomeActivity.kt
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

package com.gmail.fattazzo.formula1world.activity.home

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.about.AboutActivity_
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity
import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.collaborate.CollaborateFragment_
import com.gmail.fattazzo.formula1world.fragments.current.constructors.CurrentConstructorsFragment_
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment_
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment_
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_
import com.gmail.fattazzo.formula1world.fragments.news.NewsFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.StatisticsConstructorsFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.StatisticsDriversFragment_
import com.gmail.fattazzo.formula1world.fragments.stats.season.StatisticsSeasonFragment_
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.DBUtils
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*

@EActivity(R.layout.activity_home)
open class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Bean
    lateinit internal var ergast: Ergast

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @ViewById
    lateinit internal var nav_view: NavigationView

    @ViewById
    lateinit internal var drawer_layout: DrawerLayout

    @ViewById
    lateinit internal var toolbar: Toolbar

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var dbutils: DBUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(preferenceManager.appTheme)

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            dbutils.downloadDBIfNeeded()

            Log.d(TAG, "Clear cache")
            dataService!!.clearCache()
            // Instanzio il fragment se savedInstanceState == null altrimenti (ad es. girando il dispositivo)
            // viene rimesso anche se quello attivo è un altro
            FragmentUtils.replace(this, HomeFragment_.builder().build())
        }

        val metrics = resources.displayMetrics
        val densityDpi = (metrics.density * 160f).toInt()
        Log.d("DENSITY", metrics.density.toString())
        Log.d("DPI", densityDpi.toString())
    }

    @OnActivityResult(PREF_ACTIVITY_RESULT)
    fun onPrefThemeChanged(resultCode: Int) {
        if (SettingsActivity.RESULT_CODE_THEME_UPDATED == resultCode) {
            setTheme(preferenceManager.appTheme)
            val handler = Handler()
            handler.post { recreate() }
        }
    }

    @AfterViews
    protected fun init() {

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.itemIconTintList = null

        val seasons = dataService.getAvailableSeasons()

        val adapter = ArrayAdapter(this, R.layout.home_seasons_spinner_item, seasons.toTypedArray())
        adapter.setDropDownViewResource(R.layout.home_seasons_spinner_dropdown_item)

        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.app_name) + " " + ergast.season + "▼"
        }
        toolbar.setOnClickListener { showSeasonsSelectionDialog() }
    }

    private fun showSeasonsSelectionDialog() {

        val seasons = dataService.getAvailableSeasons()

        val d = Dialog(this)
        d.setTitle(getString(R.string.seasons))

        d.setContentView(R.layout.seasons_selection_dialog_layout)
        val b1: Button = d.findViewById(R.id.buttonSelect)
        val b2: Button = d.findViewById(R.id.buttonCancel)
        val np: NumberPicker = d.findViewById(R.id.numberPicker)
        np.maxValue = seasons[0]
        np.minValue = seasons[seasons.size - 1]
        np.value = ergast.season
        np.wrapSelectorWheel = true
        b1.setOnClickListener {
            val oldSeason = ergast.season

            val selectedSeason = np.value
            ergast.season = selectedSeason
            if (supportActionBar != null) {
                supportActionBar!!.title = getString(R.string.app_name) + " " + np.value + "▼"
            }
            drawer_layout.closeDrawer(GravityCompat.START)

            if (oldSeason != ergast.season) {
                dataService.clearCache()

                FragmentUtils.replace(this@HomeActivity, HomeFragment_.builder().build())
            }
            d.dismiss()
        }
        b2.setOnClickListener {
            d.dismiss() // dismiss the dialog
        }
        d.show()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        val fragments = fragmentManager.fragments.orEmpty();

        val li = fragments.listIterator(fragments.size);
        while(li.hasPrevious()) {
            val fragment = li.previous();
            if (fragment != null && fragment is BaseFragment) {
                fragment.backPressed()
            }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_current_season_driver -> FragmentUtils.replace(this, CurrentDriversFragment_.builder().build())
            R.id.nav_current_season_constructor -> FragmentUtils.replace(this, CurrentConstructorsFragment_.builder().build())
            R.id.nav_current_season_race -> FragmentUtils.replace(this, CurrentRacesFragment_.builder().build())
            R.id.nav_news -> FragmentUtils.replace(this, NewsFragment_.builder().build())
            R.id.nav_collaborate -> FragmentUtils.replace(this, CollaborateFragment_.builder().build())
            R.id.nav_stats_drivers -> FragmentUtils.replace(this, StatisticsDriversFragment_.builder().build())
            R.id.nav_stats_constructors -> FragmentUtils.replace(this, StatisticsConstructorsFragment_.builder().build())
            R.id.nav_stats_season -> FragmentUtils.replace(this, StatisticsSeasonFragment_.builder().build())
            R.id.nav_about -> AboutActivity_.intent(this).start()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {

        const val PREF_ACTIVITY_RESULT = 1
        private val TAG = HomeActivity::class.java.simpleName
    }
}
