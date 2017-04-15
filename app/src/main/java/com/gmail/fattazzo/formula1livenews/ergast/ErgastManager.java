package com.gmail.fattazzo.formula1livenews.ergast;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1livenews.ergast.exceptions.SeasonException;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Circuit;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Constructor;
import com.gmail.fattazzo.formula1livenews.ergast.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;
import com.gmail.fattazzo.formula1livenews.ergast.objects.FinishingStatus;
import com.gmail.fattazzo.formula1livenews.ergast.objects.LapTimes;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Qualification;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RacePitStops;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResult;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Schedule;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Season;
import com.gmail.fattazzo.formula1livenews.ergast.parser.Parser;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import static com.gmail.fattazzo.formula1livenews.ergast.Ergast.CURRENT_SEASON;
import static com.gmail.fattazzo.formula1livenews.ergast.Ergast.NO_ROUND;


/**
 * Date: 08.03.17
 *
 * @author olerom
 */
@EBean
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
        String json = ergastConnection.getJson(ergast, DRIVERS, NO_ROUND);
        return new Parser<>(json, new String[]{"DriverTable", "Drivers"}, Driver.class).parse();
    }

    /**
     * @return list of circuits that satisfy your query.
     */
    public List<Circuit> getCircuits() {
        String json = ergastConnection.getJson(ergast, CIRCUITS, NO_ROUND).replace("long", "lng");
        return new Parser<>(json, new String[]{"CircuitTable", "Circuits"}, Circuit.class).parse();
    }

    /**
     * @return list of seasons that satisfy your query.
     */
    public List<Season> getSeasons() {
        String json = ergastConnection.getJson(ergast, SEASONS, NO_ROUND);
        return new Parser<>(json, new String[]{"SeasonTable", "Seasons"}, Season.class).parse();
    }

    /**
     * @return list of constructors that satisfy your query.
     */
    public List<Constructor> getConstructors() {
        String json = ergastConnection.getJson(ergast, CONSTRUCTORS, NO_ROUND);
        return new Parser<>(json, new String[]{"ConstructorTable", "constructors"}, Constructor.class).parse();
    }

    /**
     * @return list of schedules that satisfy your query.
     */
    @NonNull
    public List<Schedule> getSchedules() {
        String json = ergastConnection.getJson(ergast, SCHEDULE, NO_ROUND);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, Schedule.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of race results that satisfy your query.
     */
    public List<RaceResult> getRaceResults(int round) {
        if (ergast.getSeason() == CURRENT_SEASON) {
            throw new SeasonException("Race results requires season to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, RESULTS, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races", "Results"}, RaceResult.class).parse();
    }

    /**
     * @param round is a round which you want to get.
     * @return list of qualification results that satisfy your query.
     */
    public List<Qualification> getQualificationResults(int round) {
        if (ergast.getSeason() == CURRENT_SEASON) {
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
        if (ergast.getSeason() == CURRENT_SEASON && round != NO_ROUND) {
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
        if (ergast.getSeason() == CURRENT_SEASON || round == NO_ROUND) {
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
        if (ergast.getSeason() == CURRENT_SEASON || round == NO_ROUND) {
            throw new SeasonException("Race pit stops request requires season and round to be mentioned");
        }

        String json = ergastConnection.getJson(ergast, PIT_STOPS, round);
        return new Parser<>(json, new String[]{"RaceTable", "Races"}, RacePitStops.class).parse();
    }

}
