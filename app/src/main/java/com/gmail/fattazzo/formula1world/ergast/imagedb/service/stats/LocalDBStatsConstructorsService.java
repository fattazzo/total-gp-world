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
public class LocalDBStatsConstructorsService {

    public
    @NonNull
    List<StatsData> loadWins(int seasonStart, int seasonEnd) {
        List<StatsData> wins;

        String sql = "SELECT COUNT(res.Id) as value, constr.name as label " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by constr.Id " +
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

        String sql = "SELECT COUNT(res.Id) as value, constr.nationality as label " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position = 1 " +
                "group by constr.nationality " +
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
    List<StatsData> loadPodiums(int seasonStart, int seasonEnd) {
        List<StatsData> podiums;

        String sql = "SELECT constr.name as label, " +
                "COUNT(res.Id) as value, " +
                "sum(case when res.position = 1 then 1 else 0 end) as value2, " +
                "sum(case when res.position = 2 then 1 else 0 end) as value3, " +
                "sum(case when res.position = 3 then 1 else 0 end) as value4 " +
                "FROM results res " +
                "inner join constructors constr on res.constructorId = constr.Id " +
                "inner join races race on race.Id = res.raceId " +
                "where race.year >= " + seasonStart + " and race.year <= " + seasonEnd +
                " and res.position <= 3 " +
                "group by constr.Id " +
                "order by COUNT(res.Id) desc, sum(case when res.position = 1 then 1 else 0 end) desc," +
                "       sum(case when res.position = 2 then 1 else 0 end) desc," +
                "       sum(case when res.position = 3 then 1 else 0 end) desc ";

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, null)) {
            podiums = new ArrayList<>();
            while (c.moveToNext()) {
                String label = c.getString(c.getColumnIndex("label"));
                int value = c.getInt(c.getColumnIndex("value"));
                int value2 = c.getInt(c.getColumnIndex("value2"));
                int value3 = c.getInt(c.getColumnIndex("value3"));
                int value4 = c.getInt(c.getColumnIndex("value4"));

                podiums.add(new StatsData(value, value2, value3, value4, label));
            }
        } catch (Exception e) {
            podiums = new ArrayList<>();
        }

        return podiums;
    }
}
