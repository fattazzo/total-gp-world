package com.gmail.fattazzo.formula1world.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/06/17
 */
public interface IDataService {

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
     * @param driverRef driver ref
     * @return race results
     */
    @NonNull
    List<F1Result> loadDriverRacesResult(String driverRef);

    /**
     * @param constructorRef constructor ref
     * @return race results
     */
    @NonNull
    List<F1Result> loadConstructorRacesResult(String constructorRef);

    /**
     * Load current race scheduled.
     *
     * @return current race
     */
    @Nullable
    F1Race loadCurrentSchedule();

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
}
