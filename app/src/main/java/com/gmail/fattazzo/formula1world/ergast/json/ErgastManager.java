package com.gmail.fattazzo.formula1world.ergast.json;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.json.exceptions.SeasonException;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Circuit;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.FinishingStatus;
import com.gmail.fattazzo.formula1world.ergast.json.objects.LapTimes;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RacePitStops;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResult;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResults;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Schedule;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Season;
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;


/**
 * Date: 08.03.17
 *
 * @author olerom
 */
@EBean(scope = EBean.Scope.Singleton)
public class ErgastManager {

    private final static String DRIVERS = "drivers";
    private final static String CIRCUITS = "circuits";
    private final static String CONSTRUCTORS = "constructors";
    private final static String SEASONS = "seasons";
    private final static String SCHEDULE = "";
    private final static String RESULTS = "results";
    private final static String QUALIFYING = "qualifying";
    private final static String DRIVER_STANDINGS = "driverStandings";
    private final static String CONSTRUCTOR_STANDINGS = "constructorStandings";
    private final static String FINISHING_STATUS = "status";
    private final static String LAP_TIMES = "laps";
    private final static String PIT_STOPS = "pitstops";

    @Bean
    Ergast ergast;

    @Bean
    ErgastConnection ergastConnection;

    public ErgastManager() {
    }

    public Ergast getErgast() {
        return ergast;
    }

    /**
     * @return list of drivers that satisfy your query.
     */
    @NonNull
    public List<Driver> getDrivers() {
        String json = ergastConnection.getJson(ergast, DRIVERS, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"DriverTable", "Drivers"}, Driver.class).parse();
    }

    /**
     * @return list of drivers that satisfy your query.
     */
    @NonNull
    public List<RaceResults> getDriverRacersResult(@NonNull String driverId) {
        String json = ergastConnection.getJson(ergast, DRIVERS + "/" + driverId + "/" + RESULTS, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, RaceResults.class).parse();
    }

    /**
     * @return list of constructor that satisfy your query.
     */
    @NonNull
    public List<RaceResults> getConstructorRacersResult(@NonNull String constructorId) {
        String json = ergastConnection.getJson(ergast, CONSTRUCTORS + "/" + constructorId + "/" + RESULTS, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, RaceResults.class).parse();
    }

    /**
     * @return list of circuits that satisfy your query.
     */
    public List<Circuit> getCircuits() {
        String json = ergastConnection.getJson(ergast, CIRCUITS, Ergast.NO_ROUND).replace("long", "lng");
        return new Parser<>(json, new String[]{"CircuitTable", "Circuits"}, Circuit.class).parse();
    }

    /**
     * @return list of seasons that satisfy your query.
     */
    public List<Season> getSeasons() {
        String json = ergastConnection.getJson(ergast, SEASONS, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"SeasonTable", "Seasons"}, Season.class).parse();
    }

    /**
     * @return list of constructors that satisfy your query.
     */
    public List<Constructor> getConstructors() {
        String json = ergastConnection.getJson(ergast, CONSTRUCTORS, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"ConstructorTable", "constructors"}, Constructor.class).parse();
    }

    /**
     * @return list of schedules that satisfy your query.
     */
    @NonNull
    public List<Schedule> getSchedules() {
        String json = ergastConnection.getJson(ergast, SCHEDULE, Ergast.NO_ROUND);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, Schedule.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of race results that satisfy your query.
     */
    public List<RaceResult> getRaceResults(int round) {
        if (ergast.getSeason() == Ergast.CURRENT_SEASON) {
            throw new SeasonException("Race results requires season to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, RESULTS, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races", "results"}, RaceResult.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of qualification results that satisfy your query.
     */
    public List<Qualification> getQualificationResults(int round) {
        if (ergast.getSeason() == Ergast.CURRENT_SEASON) {
            throw new SeasonException("Qualification results requires season to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, QUALIFYING, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races", "QualifyingResults"}, Qualification.class).parse();
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of driver standings that satisfy your query.
     */
    @NonNull
    public List<DriverStandings> getDriverStandings(int round) {
        //if (this.season == CURRENT_SEASON) {
        //    throw new SeasonException("Driver standing requires season to be mentioned");
        //}

        String json = ergastConnection.getJson(ergast, DRIVER_STANDINGS, round);
        return new Parser<>(json, new String[]{"StandingsTable", "StandingsLists", "DriverStandings"}, DriverStandings.class).parse();
    }

    public DriverStandings getDriverLeader() {
        String json = ergastConnection.getJson(ergast, DRIVER_STANDINGS + "/1", Ergast.NO_ROUND);
        Parser parser = new Parser<>(json, new String[]{"StandingsTable", "StandingsLists", "DriverStandings"}, DriverStandings.class);
        List<DriverStandings> standingsList = parser.parse();
        return CollectionUtils.isEmpty(standingsList) ? null : standingsList.get(0);
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of constructor standings that satisfy your query.
     */
    @NonNull
    public List<ConstructorStandings> getConstructorStandings(int round) {
        //if (this.season == CURRENT_SEASON) {
        //    throw new SeasonException("Constructor standing requires season to be mentioned");
        //}

        String json = ergastConnection.getJson(ergast, CONSTRUCTOR_STANDINGS, round);
        return new Parser<>(json, new String[]{"StandingsTable", "StandingsLists", "ConstructorStandings"}, ConstructorStandings.class).parse();
    }

    /**
     * @param round is a round which you want to get, (-1) if you want to get whole season.
     * @return list of finishing statuses standings that satisfy your query.
     */
    public List<FinishingStatus> getFinishingstatuses(int round) {
        if (ergast.getSeason() == Ergast.CURRENT_SEASON && round != Ergast.NO_ROUND) {
            throw new SeasonException("Finishing status request requires season to be mentioned if you mention round");
        }

        String json = ergastConnection.getJson(ergast, FINISHING_STATUS, round);
        return new Parser<>(json, new String[]{"StatusTable", "Status"}, FinishingStatus.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of lap times that satisfy your query.
     */
    public List<LapTimes> getLapTimes(int round) {
        if (ergast.getSeason() == Ergast.CURRENT_SEASON || round == Ergast.NO_ROUND) {
            throw new SeasonException("Finishing status request requires season and round to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, LAP_TIMES, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, LapTimes.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of pit stops that satisfy your query.
     */
    public List<RacePitStops> getRacePitStops(int round) {
        if (ergast.getSeason() == Ergast.CURRENT_SEASON || round == Ergast.NO_ROUND) {
            throw new SeasonException("Race pit stops request requires season and round to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, PIT_STOPS, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, RacePitStops.class).parse();
    }

}
