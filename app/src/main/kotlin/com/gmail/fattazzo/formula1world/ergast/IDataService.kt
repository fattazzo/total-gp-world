package com.gmail.fattazzo.formula1world.ergast

import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.domain.F1PitStop
import com.gmail.fattazzo.formula1world.domain.F1Qualification
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.domain.F1Result
import com.gmail.fattazzo.formula1world.domain.F1Season

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
