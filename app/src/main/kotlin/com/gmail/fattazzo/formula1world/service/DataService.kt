/*
 * Project: total-gp-world
 * File: DataService.kt
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

package com.gmail.fattazzo.formula1world.service

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.*
import com.gmail.fattazzo.formula1world.ergast.DataCache
import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.ergast.IDataService
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.LocalDBDataService
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.time.DateFormatUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 03/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class DataService : IDataService {

    @RootContext
    lateinit internal var context: Context

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var ergast: Ergast

    @Bean
    lateinit internal var onlineDataService: OnlineDataService

    @Bean
    lateinit internal var localDBDataService: LocalDBDataService

    @Bean
    lateinit internal var dataCache: DataCache

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    private var availableSeasons: MutableList<Int>? = null

    private// local db seasons win vs online seasons
    val dataServiceImpl: IDataService
        get() {

            val season = selectedSeasons
            val dbSeasonFound = localDBDataService.loadSeason(season) != null
            return if (dbSeasonFound) {
                localDBDataService
            } else {
                onlineDataService
            }
        }

    val selectedSeasons: Int
        get() {
            return try {
                if (ergast.season == Ergast.CURRENT_SEASON) {
                    Calendar.getInstance().get(Calendar.YEAR)
                } else {
                    ergast.season
                }
            } catch (e: Exception) {
                -1
            }
        }

    // ------------------- Data Cache Actions -------------------
    fun clearCache() {
        dataCache.clearAll()
    }

    fun clearDriverStandingsCache() {
        dataCache.clearDriverStandings()
    }

    fun clearConstructorStandingsCache() {
        dataCache.clearConstructorStandings()
    }

    fun clearDriversCache() {
        dataCache.clearDrivers()
    }

    fun clearConstructorsCache() {
        dataCache.clearConstructors()
    }

    fun clearRacesCache() {
        dataCache.clearRaces()
    }

    fun clearRaceResultsCache(race: F1Race) {
        dataCache.clearRaceResults(race)
    }

    fun clearRaceQualifications(race: F1Race) {
        dataCache.clearRaceQualifications(race)
    }

    fun clearDriverRaceResultsCache(driver: F1Driver) {
        dataCache.clearDriverRaceResults(driver)
    }

    fun clearConstructorRaceResultsCache(constructor: F1Constructor) {
        dataCache.clearConstructorRaceResults(constructor)
    }

    fun clearRacePitStopsCache(race: F1Race) {
        dataCache.clearRacePitStops(race)
    }

    fun clearColorsCache() {
        dataCache.clearConstructorColors()
        dataCache.clearDriverColors()
    }
    // ----------------------------------------------------------


    override fun loadSeason(year: Int): F1Season? = dataServiceImpl.loadSeason(year)

    fun getAvailableSeasons(): List<Int> {
        if (availableSeasons == null) {
            var lastYear = localDBDataService.lastSeason()
            if (lastYear == null) {
                lastYear = Calendar.getInstance().get(Calendar.YEAR)
            }
            lastYear+=1

            availableSeasons = ArrayList()
            for (i in lastYear downTo 1950) {
                availableSeasons!!.add(i)
            }
        }
        return availableSeasons!!
    }

    override fun updateSeason(season: F1Season) {
        dataServiceImpl!!.updateSeason(season)
    }

    @Synchronized override fun loadDrivers(): List<F1Driver> {
        var drivers = dataCache.drivers
        if (CollectionUtils.isEmpty(drivers)) {
            drivers = localDBDataService.loadDrivers()
            if (CollectionUtils.isEmpty(drivers)) {
                drivers = onlineDataService.loadDrivers()
            }
            dataCache.drivers = drivers
        }
        return drivers
    }

    @Synchronized override fun loadConstructors(): List<F1Constructor> {
        var constructors = dataCache.constructors
        if (CollectionUtils.isEmpty(constructors)) {
            constructors = localDBDataService.loadConstructors()
            if (CollectionUtils.isEmpty(constructors)) {
                constructors = onlineDataService.loadConstructors()
            }
            dataCache.constructors = constructors
        }
        return constructors
    }

    @Synchronized override fun loadDriverRacesResult(driver: F1Driver): List<F1Result> {
        var results = dataCache.getDriverRaceResults(driver)
        if (CollectionUtils.isEmpty(results)) {
            results = dataServiceImpl!!.loadDriverRacesResult(driver)
            dataCache.setDriverRaceResults(driver, results)
        }
        return results
    }

    @Synchronized override fun loadConstructorRacesResult(constructor: F1Constructor): List<F1Result> {
        var results = dataCache.getConstructorRaceResults(constructor)
        if (CollectionUtils.isEmpty(results)) {
            results = dataServiceImpl!!.loadConstructorRacesResult(constructor)
            dataCache.setConstructorRaceResults(constructor, results)
        }
        return results
    }

    /**
     * Load current race scheduled.
     *
     * @return current race
     */
    @Synchronized
    fun loadCurrentSchedule(): F1Race? {
        val races = loadRaces()

        val currentDateEnd = Calendar.getInstance()
        currentDateEnd.add(Calendar.HOUR_OF_DAY, 2)
        val currentDate = DateFormatUtils.format(currentDateEnd, "yyyy-MM-dd'T'HH:mm:ss")

        for (race in CollectionUtils.emptyIfNull(races)) {
            val scheudleDateUTC = race.date + "T" + race.time
            val scheduleDateLocal = utils.convertUTCDateToLocal(scheudleDateUTC, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss")

            if (scheduleDateLocal >= currentDate) {
                return race
            }
        }

        return null
    }

    @Synchronized override fun loadDriverStandings(): List<F1DriverStandings> {
        var driverStandings = dataCache.driverStandings
        if (CollectionUtils.isEmpty(driverStandings)) {
            driverStandings = dataServiceImpl!!.loadDriverStandings()
            dataCache.driverStandings = driverStandings
        }
        return driverStandings
    }

    @Synchronized override fun loadDriverLeader(): F1DriverStandings? {

        // First get from cache
        val standings = loadDriverStandings()
        for (standing in standings) {
            if (standing.position == 1) {
                return standing
            }
        }
        return dataServiceImpl!!.loadDriverLeader()
    }

    @Synchronized override fun loadConstructorStandings(): List<F1ConstructorStandings> {
        var constructorStandings = dataCache.constructorStandings
        if (CollectionUtils.isEmpty(constructorStandings)) {
            constructorStandings = dataServiceImpl!!.loadConstructorStandings()
            dataCache.constructorStandings = constructorStandings
        }
        return constructorStandings
    }

    @Synchronized override fun loadRaces(): List<F1Race> {
        var races = dataCache.races
        if (CollectionUtils.isEmpty(races)) {
            races = localDBDataService.loadRaces()
            if (CollectionUtils.isEmpty(races)) {
                races = onlineDataService.loadRaces()
            }
            dataCache.races = races
        }
        return races
    }

    @Synchronized override fun loadRaceResult(race: F1Race): List<F1Result> {
        var results = dataCache.getRaceResultsCache(race)
        if (CollectionUtils.isEmpty(results)) {
            results = localDBDataService.loadRaceResult(race)
            if (CollectionUtils.isEmpty(results)) {
                results = onlineDataService.loadRaceResult(race)
            }
            dataCache.setRaceResults(race, results)
        }
        return results
    }

    @Synchronized override fun loadQualification(race: F1Race): List<F1Qualification> {
        var qualifications = dataCache.getRaceQualificationsCache(race)
        if (CollectionUtils.isEmpty(qualifications)) {
            qualifications = localDBDataService.loadQualification(race)
            if (CollectionUtils.isEmpty(qualifications)) {
                qualifications = onlineDataService.loadQualification(race)
            }
            dataCache.setRaceQualifications(race, qualifications)
        }
        return qualifications
    }

    override fun loadPitStops(race: F1Race): List<F1PitStop> {
        var pitStops = dataCache.getRacePitStops(race)
        if (CollectionUtils.isEmpty(pitStops)) {
            pitStops = localDBDataService.loadPitStops(race)
            if (CollectionUtils.isEmpty(pitStops)) {
                pitStops = onlineDataService.loadPitStops(race)
            }
            dataCache.setRacePitStops(race, pitStops)
        }
        return pitStops
    }

    override fun loadLaps(race: F1Race, driver: F1Driver): List<F1LapTime> {
        var lapTimes = dataCache.getRaceLapTimes(race, driver)
        if (CollectionUtils.isEmpty(lapTimes)) {
            lapTimes = if (!hasLocalLapsData(race)) {
                onlineDataService.loadLaps(race, driver)
            } else {
                localDBDataService.loadLaps(race, driver)
            }
            if (CollectionUtils.isNotEmpty(lapTimes)) {
                dataCache.setRaceLapTimes(race, driver, lapTimes)
            }
        }
        return lapTimes
    }

    fun loadContructorColor(constructor: F1Constructor?): Int {
        var color = dataCache.getConstructorColor(constructor!!)
        if (constructor != null && color == null) {
            // try from db
            color = localDBDataService.loadContructorColor(constructor)
            if (color == null) {
                // try from standard
                color = try {
                    val colorId = context.resources.getIdentifier(constructor.constructorRef, "color", context.packageName)
                    Color.parseColor("#" + Integer.toHexString(context.resources.getColor(colorId)))
                } catch (e: Exception) {
                    null
                }

            }

            if (color != null) {
                dataCache.setConstructorColor(constructor, color)
            }
        }

        return ObjectUtils.defaultIfNull<Int>(color, android.R.color.transparent)
    }

    fun loadDriverColor(driver: F1Driver?): Int {
        var color = dataCache.getDriverColor(driver!!)
        if (driver != null && color == null) {
            // try from db
            color = localDBDataService.loadDriverColor(driver)
            if (color == null) {
                // try from standard
                color = try {
                    val constructor = localDBDataService.loadConstructor(driver)
                    val colorId = context.resources.getIdentifier(constructor!!.constructorRef, "color", context.packageName)
                    Color.parseColor("#" + Integer.toHexString(context.resources.getColor(colorId)))
                } catch (e: Exception) {
                    null
                }

            }

            if (color != null) {
                dataCache.setDriverColor(driver, color)
            }
        }

        return ObjectUtils.defaultIfNull<Int>(color, ContextCompat.getColor(context, R.color.background_color_dark))
    }

    fun hasLocalLapsData(race: F1Race): Boolean = localDBDataService.hasLocalLapsData(race)
}
