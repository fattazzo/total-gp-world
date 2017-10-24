package com.gmail.fattazzo.formula1world.ergast.json.service

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
import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.ergast.IDataService
import com.gmail.fattazzo.formula1world.ergast.json.ErgastManager
import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings
import com.gmail.fattazzo.formula1world.ergast.json.objects.Lap
import com.gmail.fattazzo.formula1world.ergast.json.objects.LapTimes
import com.gmail.fattazzo.formula1world.ergast.json.objects.Qualification
import com.gmail.fattazzo.formula1world.ergast.json.objects.RacePitStops
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResult
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResults
import com.gmail.fattazzo.formula1world.ergast.json.objects.Schedule

import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

import java.util.ArrayList

/**
 * @author fattazzo
 *
 *
 * date: 11/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class OnlineDataService : IDataService {

    @Bean
    lateinit internal var ergastManager: ErgastManager

    @AfterInject
    internal fun afterInjenction() {
        ergastManager!!.ergast.season = Ergast.CURRENT_SEASON
    }

    override fun loadSeason(year: Int): F1Season {
        val f1Season = F1Season()
        f1Season.year = ergastManager!!.ergast.season
        f1Season.current = true
        return f1Season
    }

    override fun updateSeason(season: F1Season) {
        // Nothing to do
    }

    override fun loadDrivers(): List<F1Driver> {
        var drivers: MutableList<F1Driver> = ArrayList()
        try {
            val jsonDrivers = ergastManager!!.drivers
            for (jsonDriver in jsonDrivers) {
                drivers.add(jsonDriver.toF1Driver())
            }
        } catch (e: Exception) {
            drivers = ArrayList()
        }

        return drivers
    }

    /**
     * Load all constructors of the current season.
     *
     * @return constructors
     */
    override fun loadConstructors(): List<F1Constructor> {
        var constructors: MutableList<F1Constructor> = ArrayList()
        try {
            val jsonConstructors = ergastManager!!.constructors
            for (jsonConstructor in jsonConstructors) {
                constructors.add(jsonConstructor.toF1Constructor())
            }
        } catch (e: Exception) {
            constructors = ArrayList()
        }

        return constructors
    }

    override fun loadDriverRacesResult(driver: F1Driver): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val jsonResults = ergastManager!!.getDriverRacersResult(driver.driverRef!!)
            for (jsonresult in jsonResults) {
                results.addAll(jsonresult.toF1Result())
            }
        } catch (e: Exception) {
            results = ArrayList()
        }

        return results
    }

    override fun loadConstructorRacesResult(constructor: F1Constructor): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val jsonResults = ergastManager!!.getConstructorRacersResult(constructor.constructorRef!!)
            for (jsonresult in jsonResults) {
                results.addAll(jsonresult.toF1Result())
            }
        } catch (e: Exception) {
            results = ArrayList()
        }

        return results
    }

    override fun loadDriverStandings(): List<F1DriverStandings> {
        var f1DriverStandings: MutableList<F1DriverStandings> = ArrayList()
        try {
            val jsonDriverStandings = ergastManager!!.getDriverStandings(Ergast.NO_ROUND)
            for (jsonDriverStanding in jsonDriverStandings) {
                f1DriverStandings.add(jsonDriverStanding.toF1DriverStandings())
            }
        } catch (e: Exception) {
            f1DriverStandings = ArrayList()
        }

        return f1DriverStandings
    }

    override fun loadDriverLeader(): F1DriverStandings? {

        val driverStandings = ergastManager!!.driverLeader
        return driverStandings?.toF1DriverStandings()
    }

    override fun loadConstructorStandings(): List<F1ConstructorStandings> {
        var f1ConstructorStandings: MutableList<F1ConstructorStandings> = ArrayList()
        try {
            val jsonConstructorStandings = ergastManager!!.getConstructorStandings(Ergast.NO_ROUND)
            for (jsonConstructorStanding in jsonConstructorStandings) {
                f1ConstructorStandings.add(jsonConstructorStanding.toF1ConstructorStandings())
            }
        } catch (e: Exception) {
            f1ConstructorStandings = ArrayList()
        }

        return f1ConstructorStandings
    }

    override fun loadRaces(): List<F1Race> {
        var schedules: List<Schedule>
        try {
            schedules = ergastManager!!.schedules
        } catch (e: Exception) {
            schedules = ArrayList()
        }

        val f1Races = ArrayList<F1Race>()

        for (schedule in schedules) {
            f1Races.add(schedule.toF1Race())
        }

        return f1Races
    }

    override fun loadRaceResult(race: F1Race): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val jsonResults = ergastManager!!.getRaceResults(race.round)
            for (jsonresult in jsonResults) {
                results.add(jsonresult.toF1Result())
            }
        } catch (e: Exception) {
            results = ArrayList()
        }

        return results
    }

    override fun loadQualification(race: F1Race): List<F1Qualification> {
        var results: MutableList<F1Qualification> = ArrayList()

        try {
            val jsonResults = ergastManager!!.getQualificationResults(race.round)
            for (jsonQualification in jsonResults) {
                results.add(jsonQualification.toF1Qualification(race))
            }
        } catch (e: Exception) {
            results = ArrayList()
        }

        return results
    }

    override fun loadPitStops(race: F1Race): List<F1PitStop> {
        var results: MutableList<F1PitStop> = ArrayList()

        val drivers = loadDrivers()

        try {
            val jsonResults = ergastManager!!.getRacePitStops(race.round)
            for (jsonPit in jsonResults) {
                results.addAll(jsonPit.toF1PitStop(race, drivers))
            }
        } catch (e: Exception) {
            results = ArrayList()
        }

        return results
    }

    override fun loadLaps(race: F1Race, driver: F1Driver): List<F1LapTime> {
        var results: MutableList<F1LapTime> = ArrayList()

        val oldLimit = ergastManager!!.ergast.getLimit()
        try {
            // increase limit for load all race laptimes
            ergastManager!!.ergast.setLimit(150)
            val jsonLaps = ergastManager!!.getLapTimes(race.round, driver.driverRef)
            for (jsonLaps1 in jsonLaps) {
                for (lap in jsonLaps1.getLaps()) {
                    val f1LapTime = lap.toF1LapTime(race, driver)
                    results.add(f1LapTime)
                }
            }
        } catch (e: Exception) {
            results = ArrayList()
        } finally {
            // restore old limit
            ergastManager!!.ergast.setLimit(oldLimit)
        }
        return results
    }
}
