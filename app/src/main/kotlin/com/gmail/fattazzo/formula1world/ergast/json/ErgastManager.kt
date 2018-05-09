/*
 * Project: total-gp-world
 * File: ErgastManager.kt
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

package com.gmail.fattazzo.formula1world.ergast.json

import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.ergast.json.exceptions.SeasonException
import com.gmail.fattazzo.formula1world.ergast.json.objects.*
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.apache.commons.collections4.CollectionUtils


/**
 * Date: 08.03.17
 *
 * @author olerom
 */
@EBean(scope = EBean.Scope.Singleton)
open class ErgastManager {

    @Bean
    lateinit var ergast: Ergast
        internal set

    @Bean internal
    lateinit var ergastConnection: ErgastConnection

    /**
     * @return list of drivers that satisfy your query.
     */
    val drivers: List<Driver>
        get() {
            val json = ergastConnection!!.getJson(ergast!!, DRIVERS, Ergast.NO_ROUND)
            return Parser(json, arrayOf("DriverTable", "Drivers"), Driver::class.java).parse()
        }

    /**
     * @return list of circuits that satisfy your query.
     */
    val circuits: List<Circuit>
        get() {
            val json = ergastConnection!!.getJson(ergast!!, CIRCUITS, Ergast.NO_ROUND)!!.replace("long", "lng")
            return Parser(json, arrayOf("CircuitTable", "Circuits"), Circuit::class.java).parse()
        }

    /**
     * @return list of seasons that satisfy your query.
     */
    val seasons: List<Season>
        get() {
            val json = ergastConnection!!.getJson(ergast!!, SEASONS, Ergast.NO_ROUND)
            return Parser(json, arrayOf("SeasonTable", "Seasons"), Season::class.java).parse()
        }

    /**
     * @return list of constructors that satisfy your query.
     */
    val constructors: List<Constructor>
        get() {
            val json = ergastConnection!!.getJson(ergast!!, CONSTRUCTORS, Ergast.NO_ROUND)
            return Parser(json, arrayOf("ConstructorTable", "constructors"), Constructor::class.java).parse()
        }

    /**
     * @return list of schedules that satisfy your query.
     */
    val schedules: List<Schedule>
        get() {
            val json = ergastConnection!!.getJson(ergast!!, SCHEDULE, Ergast.NO_ROUND)
            return Parser(json, arrayOf("RaceTable", "Races"), Schedule::class.java).parse()
        }

    val driverLeader: DriverStandings?
        get() {
            val json = ergastConnection!!.getJson(ergast!!, DRIVER_STANDINGS + "/1", Ergast.NO_ROUND)
            val parser = Parser(json, arrayOf("StandingsTable", "StandingsLists", "DriverStandings"), DriverStandings::class.java)
            val standingsList = parser.parse()
            return if (CollectionUtils.isEmpty(standingsList)) null else standingsList[0]
        }

    /**
     * @return list of drivers that satisfy your query.
     */
    fun getDriverRacersResult(driverId: String): List<RaceResults> {
        val json = ergastConnection!!.getJson(ergast!!, "$DRIVERS/$driverId/$RESULTS", Ergast.NO_ROUND)
        return Parser(json, arrayOf("RaceTable", "Races"), RaceResults::class.java).parse()
    }

    /**
     * @return list of constructor that satisfy your query.
     */
    fun getConstructorRacersResult(constructorId: String): List<RaceResults> {
        val json = ergastConnection!!.getJson(ergast!!, "$CONSTRUCTORS/$constructorId/$RESULTS", Ergast.NO_ROUND)
        return Parser(json, arrayOf("RaceTable", "Races"), RaceResults::class.java).parse()
    }

    /**
     * @param round is a round which you want to get.
     * @return list of race results that satisfy your query.
     */
    fun getRaceResults(round: Int): List<RaceResult> {
        if (ergast!!.season == Ergast.CURRENT_SEASON) {
            throw SeasonException("Race results requires season to be mentioned")
        }

        val json = ergastConnection!!.getJson(ergast!!, RESULTS, round)
        return Parser(json, arrayOf("RaceTable", "Races", "results"), RaceResult::class.java).parse()
    }

    /**
     * @param round is a round which you want to get.
     * @return list of qualification results that satisfy your query.
     */
    fun getQualificationResults(round: Int): List<Qualification> {
        if (ergast!!.season == Ergast.CURRENT_SEASON) {
            throw SeasonException("Qualification results requires season to be mentioned")
        }

        val json = ergastConnection!!.getJson(ergast!!, QUALIFYING, round)
        return Parser(json, arrayOf("RaceTable", "Races", "QualifyingResults"), Qualification::class.java).parse()
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of driver standings that satisfy your query.
     */
    fun getDriverStandings(round: Int): List<DriverStandings> {
        //if (this.season == CURRENT_SEASON) {
        //    throw new SeasonException("Driver standing requires season to be mentioned");
        //}

        val json = ergastConnection!!.getJson(ergast!!, DRIVER_STANDINGS, round)
        return Parser(json, arrayOf("StandingsTable", "StandingsLists", "DriverStandings"), DriverStandings::class.java).parse()
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of constructor standings that satisfy your query.
     */
    fun getConstructorStandings(round: Int): List<ConstructorStandings> {
        //if (this.season == CURRENT_SEASON) {
        //    throw new SeasonException("Constructor standing requires season to be mentioned");
        //}

        val json = ergastConnection!!.getJson(ergast!!, CONSTRUCTOR_STANDINGS, round)
        return Parser(json, arrayOf("StandingsTable", "StandingsLists", "ConstructorStandings"), ConstructorStandings::class.java).parse()
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of finishing statuses standings that satisfy your query.
     */
    fun getFinishingstatuses(round: Int): List<FinishingStatus> {
        if (ergast!!.season == Ergast.CURRENT_SEASON && round != Ergast.NO_ROUND) {
            throw SeasonException("Finishing status request requires season to be mentioned if you mention round")
        }

        val json = ergastConnection!!.getJson(ergast!!, FINISHING_STATUS, round)
        return Parser(json, arrayOf("StatusTable", "Status"), FinishingStatus::class.java).parse()
    }

    /**
     * @param round is a round which you want to get.
     * @return list of lap times that satisfy your query.
     */
    fun getLapTimes(round: Int, driverRef: String?): List<LapTimes> {
        if (ergast!!.season == Ergast.CURRENT_SEASON || round == Ergast.NO_ROUND) {
            throw SeasonException("Finishing status request requires season and round to be mentioned")
        }

        val json = ergastConnection!!.getJson(ergast!!, (if (driverRef != null) "$DRIVERS/$driverRef/" else "") + LAP_TIMES, round)
        return Parser(json, arrayOf("RaceTable", "Races"), LapTimes::class.java).parse()
    }

    /**
     * @param round is a round which you want to get.
     * @return list of pit stops that satisfy your query.
     */
    fun getRacePitStops(round: Int): List<RacePitStops> {
        if (ergast!!.season == Ergast.CURRENT_SEASON || round == Ergast.NO_ROUND) {
            throw SeasonException("Race pit stops request requires season and round to be mentioned")
        }

        val json = ergastConnection!!.getJson(ergast!!, PIT_STOPS, round)
        return Parser(json, arrayOf("RaceTable", "Races"), RacePitStops::class.java).parse()
    }

    companion object {

        private val DRIVERS = "drivers"
        private val CIRCUITS = "circuits"
        private val CONSTRUCTORS = "constructors"
        private val SEASONS = "seasons"
        private val SCHEDULE = ""
        private val RESULTS = "results"
        private val QUALIFYING = "qualifying"
        private val DRIVER_STANDINGS = "driverStandings"
        private val CONSTRUCTOR_STANDINGS = "constructorStandings"
        private val FINISHING_STATUS = "status"
        private val LAP_TIMES = "laps"
        private val PIT_STOPS = "pitstops"
    }

}
