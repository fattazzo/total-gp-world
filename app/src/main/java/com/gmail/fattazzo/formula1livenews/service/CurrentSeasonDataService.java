package com.gmail.fattazzo.formula1livenews.service;

import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1livenews.ergast.Ergast;
import com.gmail.fattazzo.formula1livenews.ergast.ErgastManager;
import com.gmail.fattazzo.formula1livenews.ergast.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Schedule;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class CurrentSeasonDataService {

    @Bean
    ErgastManager ergastManager;

    @AfterInject
    void afterInjenction() {
        ergastManager.getErgast().setSeason(Ergast.CURRENT_SEASON);
    }

    /**
     * Load all driver of the current season.
     *
     * @return drivers
     */
    @NonNull
    public List<Driver> loadDrivers() {
        List<Driver> drivers;
        try {
            drivers = ergastManager.getDrivers();
        } catch (Exception e) {
            drivers = new ArrayList<>();
        }
        return drivers;
    }

    /**
     * Load current season race scheduled.
     *
     * @return current race
     */
    @Nullable
    public Schedule loadCurrentSchedule() {
        List<Schedule> schedules;
        try {
            schedules = ergastManager.getSchedules();
        } catch (Exception e) {
            schedules = new ArrayList<>();
        }

        String currentDate = DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd");

        for (Schedule schedule : schedules) {
            if (schedule.getDate().compareTo(currentDate) > 0) {
                return schedule;
            }
        }

        return null;
    }

    /**
     * Load current season driver standings.
     *
     * @return current standings
     */
    @NonNull
    public List<DriverStandings> loadDriverStandings() {
        List<DriverStandings> driverStandingsList;
        try {
            driverStandingsList = ergastManager.getDriverStandings(Ergast.NO_ROUND);
        } catch (Exception e) {
            driverStandingsList = new ArrayList<>();
        }

        return driverStandingsList;
    }

    /**
     * Load current season constructor standings.
     *
     * @return current standings
     */
    @NonNull
    public List<ConstructorStandings> loadConstructorStandings() {
        List<ConstructorStandings> constructorStandingsList;
        try {
            constructorStandingsList = ergastManager.getConstructorStandings(Ergast.NO_ROUND);
        } catch (Exception e) {
            constructorStandingsList = new ArrayList<>();
        }

        return constructorStandingsList;
    }
}
