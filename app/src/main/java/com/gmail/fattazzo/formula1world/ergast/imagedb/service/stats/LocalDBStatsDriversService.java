package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.activeandroid.ActiveAndroid;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocalDBStatsDriversService {

    private static final String TAG = LocalDBStatsDriversService.class.getSimpleName();

    public
    @NonNull
    List<StatsData> loadWins(int seasonStart, int seasonEnd) {
        List<StatsData> wins;

        String sql = "SELECT COUNT(res.Id) as value, dr.forename || ' ' || dr.surname as label " +
                "FROM results res " +
                "inner join drivers dr on res.driverId = dr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by dr.Id " +
                "order by COUNT(res.Id) desc ";

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, null)) {
            wins = new ArrayList<>();
            while (c.moveToNext()) {
                int value = c.getInt(c.getColumnIndex("value"));
                String label = c.getString(c.getColumnIndex("label"));
                wins.add(new StatsData(value, label));
            }
        } catch (Exception e) {
            wins = new ArrayList<>();
        }

        return wins;
    }

    public
    @NonNull
    List<StatsData> loadWinsNationality(int seasonStart, int seasonEnd) {
        List<StatsData> wins;

        String sql = "SELECT COUNT(res.Id) as value, dr.nationality as label " +
                "FROM results res " +
                "inner join drivers dr on res.driverId = dr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by dr.nationality " +
                "order by COUNT(res.Id) desc ";

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, null)) {
            wins = new ArrayList<>();
            while (c.moveToNext()) {
                int value = c.getInt(c.getColumnIndex("value"));
                String label = c.getString(c.getColumnIndex("label"));
                wins.add(new StatsData(value, label));
            }
        } catch (Exception e) {
            wins = new ArrayList<>();
        }

        return wins;
    }
}
