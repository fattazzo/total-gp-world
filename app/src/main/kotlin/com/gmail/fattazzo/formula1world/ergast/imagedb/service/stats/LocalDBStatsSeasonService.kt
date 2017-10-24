package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

import android.database.Cursor

import com.activeandroid.ActiveAndroid

import org.androidannotations.annotations.EBean

/**
 * @author fattazzo
 *
 *
 * date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocalDBStatsSeasonService {

    fun loadComparison(season: Int): StatsSeasonComparisonData {

        val rounds = loadRounds(season)
        val racesCompleted = loadRacesCompleted(season)

        val drivers = loadDrivers(season)
        val constructors = loadConstructors(season)

        val winningDrivers = loadWinningDrivers(season)
        val winningConstructos = loadWinningConstructors(season)

        val podiumDrivers = loadPodiumDrivers(season)
        val podiumConstructos = loadPodiumConstructors(season)

        val winningDriverPoints = loadWinningDriverPoints(season)
        val pointsAssigned = loadPointsAssigned(season)

        val prevSeasonData = StatsSeasonData(season - 1, racesCompleted[0], rounds[0], drivers[0], constructors[0], winningDrivers[0],
                winningConstructos[0], podiumDrivers[0], podiumConstructos[0], winningDriverPoints[0], pointsAssigned[0])
        val seasonData = StatsSeasonData(season, racesCompleted[1], rounds[1], drivers[1], constructors[1], winningDrivers[1],
                winningConstructos[1], podiumDrivers[1], podiumConstructos[1], winningDriverPoints[1], pointsAssigned[1])
        val nextSeasonData = StatsSeasonData(season + 1, racesCompleted[2], rounds[2], drivers[2], constructors[2], winningDrivers[2],
                winningConstructos[2], podiumDrivers[2], podiumConstructos[2], winningDriverPoints[2], pointsAssigned[2])

        return StatsSeasonComparisonData(prevSeasonData, seasonData, nextSeasonData)
    }

    private fun loadRounds(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from races " +
                "where \"year\" >= ? and \"year\" <= ?"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadRacesCompleted(season: Int): IntArray {
        val sql = "select count(case race.\"year\" when ? then 1 else null end), " +
                "count(case race.\"year\" when ? then 1 else null end), " +
                "count(case race.\"year\" when ? then 1 else null end) " +
                "from results res inner join races race on res.raceId = race.Id and res.\"position\" = 1 " +
                "where race.\"year\" >= ? and race.\"year\" <= ?"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadDrivers(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct driverId,\"year\" from driversConstructors) " +
                "where \"year\" >= ? and \"year\" <= ?"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadConstructors(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct constructorId,\"year\" from driversConstructors) " +
                "where \"year\" >= ? and \"year\" <= ?"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadWinningDrivers(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.driverId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" = 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) "

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadPodiumDrivers(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.driverId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" <= 3 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) "

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadWinningConstructors(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.constructorId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" = 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) "

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadPodiumConstructors(season: Int): IntArray {
        val sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.constructorId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" <= 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) "

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeIntQuery(sql, args)
    }

    private fun loadWinningDriverPoints(season: Int): FloatArray {
        val sql = "select sum(val1),sum(val2),sum(val3) " +
                "from( " +
                "select case yyyy when ? then max(points) else 0 end as val1, " +
                "       case yyyy when ? then max(points) else 0 end as val2, " +
                "       case yyyy when ? then max(points) else 0 end as val3 " +
                "from( " +
                "select res.driverId as driverId,sum(res.points) as points,race.\"year\" as yyyy " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where (race.\"year\" >= ? and race.\"year\" <= ?) " +
                "group by res.driverId,race.\"year\" " +
                "order by race.\"year\",sum(res.points) desc) " +
                "group by yyyy)"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeFloatQuery(sql, args)
    }

    private fun loadPointsAssigned(season: Int): FloatArray {
        val sql = "select sum(val1),sum(val2),sum(val3) " +
                "from( " +
                "select case yyyy when ? then max(points) else 0 end as val1, " +
                "       case yyyy when ? then max(points) else 0 end as val2, " +
                "       case yyyy when ? then max(points) else 0 end as val3 " +
                "from( " +
                "select sum(res.points) as points,race.\"year\" as yyyy " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where (race.\"year\" >= ? and race.\"year\" <= ?) " +
                "group by race.\"year\" " +
                "order by race.\"year\",sum(res.points) desc) " +
                "group by yyyy)"

        val prevSeasonParam = (season - 1).toString()
        val seasonParam = season.toString()
        val nextSeasonParam = (season + 1).toString()
        val args = arrayOf(prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam)
        return executeFloatQuery(sql, args)
    }

    private fun executeIntQuery(sql: String, args: Array<String>): IntArray {
        val rounds = IntArray(3)

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, args).use { c ->
                c.moveToFirst()

                rounds[0] = c.getInt(0)
                rounds[1] = c.getInt(1)
                rounds[2] = c.getInt(2)

            }
        } catch (e: Exception) {
            rounds[0] = 0
            rounds[1] = 0
            rounds[2] = 0
        }

        return rounds
    }

    private fun executeFloatQuery(sql: String, args: Array<String>): FloatArray {
        val rounds = FloatArray(3)

        try {
            ActiveAndroid.getDatabase().rawQuery(sql, args).use { c ->
                c.moveToFirst()

                rounds[0] = c.getFloat(0)
                rounds[1] = c.getFloat(1)
                rounds[2] = c.getFloat(2)

            }
        } catch (e: Exception) {
            rounds[0] = 0f
            rounds[1] = 0f
            rounds[2] = 0f
        }

        return rounds
    }
}
