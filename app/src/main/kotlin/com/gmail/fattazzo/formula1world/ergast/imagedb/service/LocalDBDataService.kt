package com.gmail.fattazzo.formula1world.ergast.imagedb.service

import android.database.Cursor
import android.graphics.Color
import android.util.Log
import com.activeandroid.ActiveAndroid
import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.domain.*
import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.ergast.IDataService
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.*
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 03/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocalDBDataService : IDataService {

    @Bean
    lateinit internal var ergast: Ergast

    /**
     * Load the season.
     *
     * @param year year
     * @return season loaded
     */
    override fun loadSeason(year: Int): F1Season? {
        var f1Season: F1Season? = null

        try {
            val season = Select().from(Season::class.java).where("Id = ?", year).executeSingle<Season>()
            if (season != null) {
                f1Season = season.toF1Season()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            f1Season = null
        }

        return f1Season
    }

    override fun updateSeason(season: F1Season) {
        val dbSeason = Select().from(Season::class.java).where("Id = ?", season.year!!).executeSingle<Season>()
        if (dbSeason != null) {
            dbSeason.url = season.url
            dbSeason.description = season.description
            dbSeason.save()
        }
    }

    override fun loadDrivers(): List<F1Driver> {
        var drivers: MutableList<F1Driver> = ArrayList()

        try {
            val dbDrivers = Select("drivers.*").distinct().from(Driver::class.java)
                    .innerJoin(DriverStandings::class.java).on("drivers.Id = driverStandings.driverId")
                    .innerJoin(Race::class.java).on("races.Id = driverStandings.raceId")
                    .where("races.year = ?", ergast.season)
                    .orderBy("drivers.surname").execute<Driver>()

            dbDrivers.mapTo(drivers) { it.toF1Driver() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            drivers = ArrayList()
        }

        return drivers
    }

    override fun loadConstructors(): List<F1Constructor> {
        var constructors: MutableList<F1Constructor> = ArrayList()

        try {
            val dbConstructors = Select("constructors.*").distinct().from(Constructor::class.java)
                    .innerJoin(ConstructorStandings::class.java).on("constructors.Id = constructorStandings.constructorId")
                    .innerJoin(Race::class.java).on("races.Id = constructorStandings.raceId")
                    .where("races.year = ?", ergast.season)
                    .orderBy("constructors.name").execute<Constructor>()

            dbConstructors.mapTo(constructors) { it.toF1Constructor() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            constructors = ArrayList()
        }

        return constructors
    }

    override fun loadDriverRacesResult(driver: F1Driver): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val dbResults = Select("res.*").from(Result::class.java).`as`("res")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = res.raceId")
                    .innerJoin(Driver::class.java).`as`("dr").on("dr.Id = res.driverId")
                    .where("rac.year = ?", ergast.season)
                    .where("dr.driverRef = ?", driver.driverRef!!)
                    .execute<Result>()
            dbResults.mapTo(results) { it.toF1Result() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    override fun loadConstructorRacesResult(constructor: F1Constructor): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val dbResults = Select("res.*").from(Result::class.java).`as`("res")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = res.raceId")
                    .innerJoin(Constructor::class.java).`as`("cs").on("cs.Id = res.constructorId")
                    .where("rac.year = ?", ergast.season)
                    .where("cs.constructorRef = ?", constructor.constructorRef!!)
                    .execute<Result>()
            dbResults.mapTo(results) { it.toF1Result() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    override fun loadDriverStandings(): List<F1DriverStandings> {
        var f1DriverStandings: MutableList<F1DriverStandings> = ArrayList()

        try {
            val dbDriverStandings = Select("drs.*").distinct()
                    .from(DriverStandings::class.java).`as`("drs")
                    .innerJoin(Race::class.java).on("races.Id = drs.raceId")
                    .where("races.year = ?", ergast.season)
                    .orderBy("drs.points desc").groupBy("drs.driverId").execute<DriverStandings>()

            dbDriverStandings.mapTo(f1DriverStandings) { it.toF1DriverStandings(ergast.season) }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            f1DriverStandings = ArrayList()
        }

        return f1DriverStandings
    }

    override fun loadDriverLeader(): F1DriverStandings? {
        val f1DriverStandings: F1DriverStandings?
        f1DriverStandings = try {
            val dbDriverStandings = Select("drs.*").distinct()
                    .from(DriverStandings::class.java).`as`("drs")
                    .innerJoin(Race::class.java).on("races.Id = drs.raceId")
                    .where("races.year = ?", ergast.season)
                    .orderBy("drs.points desc").groupBy("drs.driverId").limit(1).executeSingle<DriverStandings>()

            dbDriverStandings.toF1DriverStandings(ergast.season)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            null
        }

        return f1DriverStandings
    }

    override fun loadConstructorStandings(): List<F1ConstructorStandings> {
        var f1ConstructorStandings: MutableList<F1ConstructorStandings> = ArrayList()

        try {
            val dbConstructorStandings = Select("cs.*").distinct()
                    .from(ConstructorStandings::class.java).`as`("cs")
                    .innerJoin(Race::class.java).on("races.Id = cs.raceId")
                    .where("races.year = ?", ergast.season)
                    .orderBy("cs.points desc").groupBy("cs.constructorId").execute<ConstructorStandings>()

            dbConstructorStandings.mapTo(f1ConstructorStandings) { it.toF1ConstructorStandings() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            f1ConstructorStandings = ArrayList()
        }

        return f1ConstructorStandings
    }

    override fun loadRaces(): List<F1Race> {
        var f1Races: MutableList<F1Race> = ArrayList()

        try {
            val dbRaces = Select("race.*")
                    .from(Race::class.java).`as`("race")
                    .where("race.year = ?", ergast.season)
                    .orderBy("race.round").execute<Race>()

            dbRaces.mapTo(f1Races) { it.toF1Race() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            f1Races = ArrayList()
        }

        return f1Races
    }

    override fun loadRaceResult(race: F1Race): List<F1Result> {
        var results: MutableList<F1Result> = ArrayList()
        try {
            val dbResults = Select("res.*").from(Result::class.java).`as`("res")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = res.raceId")
                    .where("rac.round = ?", race.round)
                    .where("rac.year = ?", ergast.season)
                    .execute<Result>()
            dbResults.mapTo(results) { it.toF1Result() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    override fun loadQualification(race: F1Race): List<F1Qualification> {
        var results: MutableList<F1Qualification> = ArrayList()
        try {
            val dbResults = Select("qual.*").from(Qualification::class.java).`as`("qual")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = qual.raceId")
                    .where("rac.round = ?", race.round)
                    .where("rac.year = ?", ergast.season)
                    .execute<Qualification>()
            dbResults.mapTo(results) { it.toF1Qualification() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    override fun loadPitStops(race: F1Race): List<F1PitStop> {
        var results: MutableList<F1PitStop> = ArrayList()

        try {
            val dbResult = Select("pits.*").from(PitStop::class.java).`as`("pits")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = pits.raceId")
                    .leftJoin(Driver::class.java).`as`("dr").on("dr.id = pits.driverId")
                    .where("rac.round = ?", race.round)
                    .where("rac.year = ?", ergast.season)
                    .execute<PitStop>()
            dbResult.mapTo(results) { it.f1PitStop() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    override fun loadLaps(race: F1Race, driver: F1Driver): List<F1LapTime> {
        var results: MutableList<F1LapTime> = ArrayList()

        try {
            val dbResults = Select("laps.*").from(LapTime::class.java).`as`("laps")
                    .innerJoin(Race::class.java).`as`("rac").on("rac.Id = laps.raceId")
                    .innerJoin(Driver::class.java).`as`("dr").on("dr.id = laps.driverId")
                    .where("dr.driverRef = ?", driver.driverRef!!)
                    .where("rac.round = ?", race.round)
                    .where("rac.year = ?", ergast.season)
                    .execute<LapTime>()
            dbResults.mapTo(results) { it.toF1LapTime() }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            results = ArrayList()
        }

        return results
    }

    fun loadContructorColor(constructor: F1Constructor): Int? {
        var color: Int? = null

        try {
            val constrColor = Select("cc.*").from(ConstructorColors::class.java).`as`("cc")
                    .innerJoin(Constructor::class.java).`as`("cs").on("cs.Id = cc.constructorId")
                    .where("cc.year = ?", ergast.season)
                    .where("cs.constructorRef = ?", constructor.constructorRef!!)
                    .executeSingle<ConstructorColors>()
            if (constrColor != null) {
                color = Color.parseColor(constrColor.hex)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            color = null
        }

        return color
    }

    fun loadDriverColor(driver: F1Driver): Int? {
        var color: Int? = null

        try {
            val constrColor = Select("cc.*").from(ConstructorColors::class.java).`as`("cc")
                    .innerJoin(Driver::class.java).`as`("dr").on("dr.Id = cc.driverId")
                    .where("cc.year = ?", ergast.season)
                    .where("dr.driverRef = ?", driver.driverRef!!)
                    .executeSingle<ConstructorColors>()
            if (constrColor != null) {
                color = Color.parseColor(constrColor.hex)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            color = null
        }

        return color
    }

    fun loadConstructor(driver: F1Driver): F1Constructor? {
        var constructor: F1Constructor? = null

        try {
            val dbConstructor = Select("constr.*").distinct().from(Constructor::class.java).`as`("constr")
                    .innerJoin(DriverConstructor::class.java).`as`("dc").on("constr.Id = dc.constructorId")
                    .innerJoin(Driver::class.java).`as`("driver").on("driver.Id = dc.driverId")
                    .where("dc.year = ?", ergast.season)
                    .where("driver.driverRef = ?", driver.driverRef!!)
                    .executeSingle<Constructor>()
            if (dbConstructor != null) {
                constructor = dbConstructor.toF1Constructor()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            constructor = null
        }

        return constructor
    }

    fun hasLocalLapsData(race: F1Race): Boolean {
        var localData: Boolean

        val sql = "SELECT COUNT(lapTimes.Id) as lapsData FROM lapTimes " +
                "inner join races on lapTimes.raceId = races.Id " +
                "where races.year = " + race.year +
                " and races.round = " + race.round

        var c: Cursor? = null
        try {
            c = ActiveAndroid.getDatabase().rawQuery(sql, null)
            c!!.moveToFirst()
            val total = c.getInt(c.getColumnIndex("lapsData"))
            localData = total > 0
        } catch (e: Exception) {
            localData = false
        } finally {
            if (c != null) {
                c.close()
            }
        }
        return localData
    }

    fun lastSeason(): Int? = Select()
            .from(Season::class.java)
            .orderBy("Id desc")
            .limit(1).executeSingle<Season>()?.id?.toInt()

    companion object {

        private val TAG = LocalDBDataService::class.java.simpleName
    }
}
