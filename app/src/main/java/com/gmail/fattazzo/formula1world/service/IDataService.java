package com.gmail.fattazzo.formula1world.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.domain.F1Season;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/06/17
 */
public interface IDataService {

    /**
     * Load the season.
     *
     * @param year year
     * @return season loaded
     */
    F1Season loadSeason(int year);

    /**
     * Update season data.
     *
     * @param season season
     */
    void updateSeason(F1Season season);

    /**
     * Load all driver of the season.
     *
     * @return drivers
     */
    @NonNull
    List<F1Driver> loadDrivers();

    /**
     * Load all constructors of the season.
     *
     * @return constructors
     */
    @NonNull
    List<F1Constructor> loadConstructors();

    /**
     * @param driver driver
     * @return race results
     */
    @NonNull
    List<F1Result> loadDriverRacesResult(F1Driver driver);

    /**
     * @param constructor constructor
     * @return race results
     */
    @NonNull
    List<F1Result> loadConstructorRacesResult(F1Constructor constructor);

    /**
     * Load season driver standings.
     *
     * @return current standings
     */
    @NonNull
    List<F1DriverStandings> loadDriverStandings();

    /**
     * Load the season leader.
     *
     * @return driver leader information
     */
    @Nullable
    F1DriverStandings loadDriverLeader();

    /**
     * Load current season constructor standings.
     *
     * @return current standings
     */
    @NonNull
    List<F1ConstructorStandings> loadConstructorStandings();

    /**
     * Load all races for season
     *
     * @return races
     */
    @NonNull
    List<F1Race> loadRaces();

    /**
     * load race results
     *
     * @param race race
     * @return results
     */
    @NonNull
    List<F1Result> loadRaceResult(F1Race race);

    /**
     * Load race qualifications
     *
     * @param race race
     * @return qualifications
     */
    @NonNull
    List<F1Qualification> loadQualification(F1Race race);

    /**
     * Load race pit stops
     *
     * @param race race
     * @return pit stops
     */
    @NonNull
    List<F1PitStop> loadPitStops(F1Race race);

    /**
     * Load all race laps for driver.
     *
     * @param race race
     * @param driver driver
     * @return laps
     */
    List<F1LapTime> loadLaps(@NonNull F1Race race, @NonNull F1Driver driver);
}
