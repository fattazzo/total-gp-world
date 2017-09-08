package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.activeandroid.ActiveAndroid;

import org.androidannotations.annotations.EBean;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocalDBStatsSeasonService {

    public
    @NonNull
    StatsSeasonComparisonData loadComparison(int season) {

        int[] rounds = loadRounds(season);
        int[] racesCompleted = loadRacesCompleted(season);

        int[] drivers = loadDrivers(season);
        int[] constructors = loadConstructors(season);

        int[] winningDrivers = loadWinningDrivers(season);
        int[] winningConstructos = loadWinningConstructors(season);

        int[] podiumDrivers = loadPodiumDrivers(season);
        int[] podiumConstructos = loadPodiumConstructors(season);

        float[] winningDriverPoints = loadWinningDriverPoints(season);
        float[] pointsAssigned = loadPointsAssigned(season);

        StatsSeasonData prevSeasonData = new StatsSeasonData(season - 1, racesCompleted[0], rounds[0], drivers[0], constructors[0], winningDrivers[0],
                winningConstructos[0],podiumDrivers[0],podiumConstructos[0],winningDriverPoints[0],pointsAssigned[0]);
        StatsSeasonData seasonData = new StatsSeasonData(season, racesCompleted[1], rounds[1], drivers[1], constructors[1], winningDrivers[1],
                winningConstructos[1],podiumDrivers[1],podiumConstructos[1],winningDriverPoints[1],pointsAssigned[1]);
        StatsSeasonData nextSeasonData = new StatsSeasonData(season + 1, racesCompleted[2], rounds[2], drivers[2], constructors[2], winningDrivers[2],
                winningConstructos[2],podiumDrivers[2],podiumConstructos[2],winningDriverPoints[2],pointsAssigned[2]);

        return new StatsSeasonComparisonData(prevSeasonData, seasonData, nextSeasonData);
    }

    private int[] loadRounds(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from races " +
                "where \"year\" >= ? and \"year\" <= ?";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadRacesCompleted(int season) {
        String sql = "select count(case race.\"year\" when ? then 1 else null end), " +
                "count(case race.\"year\" when ? then 1 else null end), " +
                "count(case race.\"year\" when ? then 1 else null end) " +
                "from results res inner join races race on res.raceId = race.Id and res.\"position\" = 1 " +
                "where race.\"year\" >= ? and race.\"year\" <= ?";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadDrivers(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct driverId,\"year\" from driversConstructors) " +
                "where \"year\" >= ? and \"year\" <= ?";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadConstructors(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct constructorId,\"year\" from driversConstructors) " +
                "where \"year\" >= ? and \"year\" <= ?";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadWinningDrivers(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.driverId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" = 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) ";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadPodiumDrivers(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.driverId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" <= 3 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) ";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadWinningConstructors(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.constructorId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" = 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) ";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private int[] loadPodiumConstructors(int season) {
        String sql = "select count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end), " +
                "count(case \"year\" when ? then 1 else null end) " +
                "from (select distinct res.constructorId, race.\"year\" " +
                "from results res inner join races race on race.Id = res.raceId " +
                "where \"position\" <= 1 and " +
                "race.\"year\" >= ? and race.\"year\" <= ?) ";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeIntQuery(sql, args);
    }

    private float[] loadWinningDriverPoints(int season) {
        String sql = "select sum(val1),sum(val2),sum(val3) " +
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
                "group by yyyy)";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeFloatQuery(sql, args);
    }

    private float[] loadPointsAssigned(int season) {
        String sql = "select sum(val1),sum(val2),sum(val3) " +
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
                "group by yyyy)";

        String prevSeasonParam = String.valueOf(season - 1);
        String seasonParam = String.valueOf(season);
        String nextSeasonParam = String.valueOf(season + 1);
        String[] args = new String[]{prevSeasonParam, seasonParam, nextSeasonParam, prevSeasonParam, nextSeasonParam};
        return executeFloatQuery(sql, args);
    }

    private int[] executeIntQuery(String sql, String[] args) {
        int[] rounds = new int[3];

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, args)) {
            c.moveToFirst();

            rounds[0] = c.getInt(0);
            rounds[1] = c.getInt(1);
            rounds[2] = c.getInt(2);

        } catch (Exception e) {
            rounds[0] = 0;
            rounds[1] = 0;
            rounds[2] = 0;
        }

        return rounds;
    }

    private float[] executeFloatQuery(String sql, String[] args) {
        float[] rounds = new float[3];

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, args)) {
            c.moveToFirst();

            rounds[0] = c.getFloat(0);
            rounds[1] = c.getFloat(1);
            rounds[2] = c.getFloat(2);

        } catch (Exception e) {
            rounds[0] = 0f;
            rounds[1] = 0f;
            rounds[2] = 0f;
        }

        return rounds;
    }
}
