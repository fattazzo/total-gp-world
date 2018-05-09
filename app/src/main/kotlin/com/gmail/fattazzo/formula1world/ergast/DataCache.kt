/*
 * Project: total-gp-world
 * File: DataCache.kt
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

package com.gmail.fattazzo.formula1world.ergast

import com.gmail.fattazzo.formula1world.domain.*
import org.androidannotations.annotations.EBean
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.ListUtils
import org.apache.commons.collections4.MapUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 29/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class DataCache {

    private val raceQualificationsCache = HashMap<F1Race, List<F1Qualification>>()
    private val raceResultsCache = HashMap<F1Race, List<F1Result>>()
    private val racePitStopsCache = HashMap<F1Race, List<F1PitStop>>()
    private var driversCache: MutableList<F1Driver> = ArrayList()
    private var constructorsCache: MutableList<F1Constructor> = ArrayList()
    private var racesCache: MutableList<F1Race> = ArrayList()
    private var driverStandingsCache: MutableList<F1DriverStandings> = ArrayList()
    private var constructorStandingsCache: MutableList<F1ConstructorStandings> = ArrayList()
    private val driverRaceResultsCache = HashMap<F1Driver, List<F1Result>>()
    private val constructorRaceResultsCache = HashMap<F1Constructor, List<F1Result>>()
    private val raceLapTimesCache = HashMap<F1Race, Map<F1Driver, List<F1LapTime>>>()
    private val constructorColorsCache = HashMap<F1Constructor, Int>()
    private val driverColorsCache = HashMap<F1Driver, Int>()

    var drivers: List<F1Driver>
        get() = ListUtils.unmodifiableList(driversCache)
        set(driversCache) {
            this.driversCache = ListUtils.emptyIfNull(driversCache)
        }

    var races: List<F1Race>
        get() = ListUtils.unmodifiableList(racesCache)
        set(racesCache) {
            this.racesCache = ListUtils.emptyIfNull(racesCache)
        }

    var driverStandings: List<F1DriverStandings>
        get() = ListUtils.unmodifiableList(driverStandingsCache)
        set(driverStandingsCache) {
            this.driverStandingsCache = ListUtils.emptyIfNull(driverStandingsCache)
        }

    var constructorStandings: List<F1ConstructorStandings>
        get() = ListUtils.unmodifiableList(constructorStandingsCache)
        set(constructorStandingsCache) {
            this.constructorStandingsCache = ListUtils.emptyIfNull(constructorStandingsCache)
        }

    var constructors: List<F1Constructor>
        get() = ListUtils.unmodifiableList(constructorsCache)
        set(constructors) {
            this.constructorsCache = ListUtils.emptyIfNull(constructors)
        }

    fun clearAll() {
        raceQualificationsCache.clear()
        raceResultsCache.clear()
        racePitStopsCache.clear()
        driversCache.clear()
        constructorsCache.clear()
        racesCache.clear()
        constructorStandingsCache.clear()
        driverStandingsCache.clear()
        driverRaceResultsCache.clear()
        constructorRaceResultsCache.clear()
        raceLapTimesCache.clear()
        constructorRaceResultsCache.clear()
        constructorColorsCache.clear()
        driverColorsCache.clear()
    }

    fun clearDriverStandings() {
        driverStandingsCache.clear()
    }

    fun clearConstructorStandings() {
        constructorStandingsCache.clear()
    }

    fun clearDrivers() {
        driversCache.clear()
    }

    fun clearConstructors() {
        constructorsCache.clear()
    }

    fun clearRaces() {
        racesCache.clear()
    }

    fun clearRaceResults(race: F1Race) {
        raceResultsCache.remove(race)
    }

    fun clearRaceQualifications(race: F1Race) {
        raceQualificationsCache.remove(race)
    }

    fun clearRacePitStops(race: F1Race) {
        racePitStopsCache.remove(race)
    }

    fun clearDriverRaceResults(driver: F1Driver) {
        driverRaceResultsCache.remove(driver)
    }

    fun clearConstructorRaceResults(constructor: F1Constructor) {
        constructorRaceResultsCache.remove(constructor)
    }

    internal fun clearRaceLapTimes(race: F1Race?, driver: F1Driver?) {
        if (race == null) {
            raceLapTimesCache.clear()
        } else {
            val raceLaps = MapUtils.emptyIfNull(raceLapTimesCache[race])
            if (driver == null) {
                raceLaps.clear()
            } else {
                CollectionUtils.emptyIfNull(raceLaps[driver]).clear()
            }
        }
    }

    fun clearConstructorColors() {
        constructorColorsCache.clear()
    }

    fun clearDriverColors() {
        driverColorsCache.clear()
    }

    // ------------------------------------------------------------------------

    fun getRaceResultsCache(race: F1Race): List<F1Result> {
        return MapUtils.emptyIfNull(raceResultsCache)[race].orEmpty()
    }

    fun setRaceResults(race: F1Race, results: List<F1Result>) {
        raceResultsCache.put(race, results)
    }

    fun getRaceQualificationsCache(race: F1Race): List<F1Qualification> {
        return MapUtils.emptyIfNull(raceQualificationsCache)[race].orEmpty()
    }

    fun setRaceQualifications(race: F1Race, qualifications: List<F1Qualification>) {
        raceQualificationsCache.put(race, qualifications)
    }

    fun getDriverRaceResults(driver: F1Driver): List<F1Result> {
        return MapUtils.emptyIfNull(driverRaceResultsCache)[driver].orEmpty()
    }

    fun setDriverRaceResults(driver: F1Driver, results: List<F1Result>) {
        driverRaceResultsCache.put(driver, results)
    }

    fun getConstructorRaceResults(constructor: F1Constructor): List<F1Result> {
        return MapUtils.emptyIfNull(constructorRaceResultsCache)[constructor].orEmpty()
    }

    fun setConstructorRaceResults(constructor: F1Constructor, results: List<F1Result>) {
        constructorRaceResultsCache.put(constructor, results)
    }

    fun getRacePitStops(race: F1Race): List<F1PitStop> {
        return MapUtils.emptyIfNull(racePitStopsCache)[race].orEmpty()
    }

    fun setRacePitStops(race: F1Race, pitStops: List<F1PitStop>) {
        racePitStopsCache.put(race, pitStops)
    }

    fun getRaceLapTimes(race: F1Race, driver: F1Driver): List<F1LapTime> {
        return MapUtils.emptyIfNull(raceLapTimesCache[race])[driver].orEmpty()
    }

    fun setRaceLapTimes(race: F1Race, driver: F1Driver, lapTimes: List<F1LapTime>) {
        val lap = HashMap<F1Driver, List<F1LapTime>>()
        lap.put(driver, ListUtils.emptyIfNull(lapTimes))

        raceLapTimesCache.put(race, MapUtils.emptyIfNull(lap))
    }

    fun getConstructorColor(constructor: F1Constructor): Int? {
        return constructorColorsCache[constructor]
    }

    fun setConstructorColor(constructor: F1Constructor, color: Int) {
        constructorColorsCache.put(constructor, color)
    }

    fun getDriverColor(driver: F1Driver): Int? {
        return driverColorsCache[driver]
    }

    fun setDriverColor(driver: F1Driver, color: Int) {
        driverColorsCache.put(driver, color)
    }
}
