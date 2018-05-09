/*
 * Project: total-gp-world
 * File: IDataService.kt
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

/**
 * @author fattazzo
 *
 *
 * date: 03/06/17
 */
interface IDataService {

    /**
     * Load the season.
     *
     * @param year year
     * @return season loaded
     */
    fun loadSeason(year: Int): F1Season?

    /**
     * Update season data.
     *
     * @param season season
     */
    fun updateSeason(season: F1Season)

    /**
     * Load all driver of the season.
     *
     * @return drivers
     */
    fun loadDrivers(): List<F1Driver>

    /**
     * Load all constructors of the season.
     *
     * @return constructors
     */
    fun loadConstructors(): List<F1Constructor>

    /**
     * @param driver driver
     * @return race results
     */
    fun loadDriverRacesResult(driver: F1Driver): List<F1Result>

    /**
     * @param constructor constructor
     * @return race results
     */
    fun loadConstructorRacesResult(constructor: F1Constructor): List<F1Result>

    /**
     * Load season driver standings.
     *
     * @return current standings
     */
    fun loadDriverStandings(): List<F1DriverStandings>

    /**
     * Load the season leader.
     *
     * @return driver leader information
     */
    fun loadDriverLeader(): F1DriverStandings?

    /**
     * Load current season constructor standings.
     *
     * @return current standings
     */
    fun loadConstructorStandings(): List<F1ConstructorStandings>

    /**
     * Load all races for season
     *
     * @return races
     */
    fun loadRaces(): List<F1Race>

    /**
     * load race results
     *
     * @param race race
     * @return results
     */
    fun loadRaceResult(race: F1Race): List<F1Result>

    /**
     * Load race qualifications
     *
     * @param race race
     * @return qualifications
     */
    fun loadQualification(race: F1Race): List<F1Qualification>

    /**
     * Load race pit stops
     *
     * @param race race
     * @return pit stops
     */
    fun loadPitStops(race: F1Race): List<F1PitStop>

    /**
     * Load all race laps for driver.
     *
     * @param race race
     * @param driver driver
     * @return laps
     */
    fun loadLaps(race: F1Race, driver: F1Driver): List<F1LapTime>
}
