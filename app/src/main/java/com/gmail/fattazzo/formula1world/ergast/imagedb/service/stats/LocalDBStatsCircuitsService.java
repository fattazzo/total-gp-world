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
 *         date: 10/10/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocalDBStatsCircuitsService {

    public
    @NonNull
    List<StatsCircuitsData> loadCount(int seasonStart, int seasonEnd) {
        List<StatsCircuitsData> counts;

        String sql = "select cir.Id," +
                "   cir.name, " +
                "   count(race.Id) " +
                "from races race inner join circuits cir on race.circuitId = cir.Id " +
                "where \"year\" >= ? and \"year\" <= ?) " +
                "group by race.circuitId " +
                "order by cir.country,cir.location";

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, new String[]{String.valueOf(seasonStart), String.valueOf(seasonEnd)})) {
            counts = new ArrayList<>();
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int count = c.getInt(2);

                counts.add(new StatsCircuitsData(id, name, count));
            }
        } catch (Exception e) {
            counts = new ArrayList<>();
        }

        return counts;
    }

    public
    @NonNull
    List<StatsCircuitsData> loadWinner(int season, StatsCircuitsData.Type type) {
        List<StatsCircuitsData> circuits;

        String sql = "select cir.Id, " +
                "   cir.name, " +
                "   dr.Id, " +
                "   dr.forename || ' ' || dr.surname, " +
                "   cs.Id, " +
                "   cs.name, " +
                "   cs.constructorRef " +
                "from races race inner join circuits cir on race.circuitId = cir.Id  " +
                "                left join results res on res.raceId = race.Id and res.\"position\" = 1 " +
                "                left join drivers dr on dr.Id = res.driverId " +
                "                left join constructors cs on cs.Id = res.constructorId " +
                "where \"year\" = ? " +
                "group by race.circuitId " +
                "order by race.round";

        try (Cursor c = ActiveAndroid.getDatabase().rawQuery(sql, new String[]{String.valueOf(season)})) {
            circuits = new ArrayList<>();
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int driverId = c.getInt(2);
                String driverName = c.getString(3);
                int constructorId = c.getInt(4);
                String constructorName = c.getString(5);
                String constructorRef = c.getString(6);

                circuits.add(new StatsCircuitsData(id, name, driverId, driverName, constructorId, constructorName, constructorRef, type));
            }
        } catch (Exception e) {
            circuits = new ArrayList<>();
        }

        return circuits;
    }
}
