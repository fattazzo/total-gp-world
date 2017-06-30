package com.gmail.fattazzo.formula1world.ergast.json.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.json.ErgastManager;
import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResult;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResults;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Schedule;
import com.gmail.fattazzo.formula1world.service.IDataService;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class OnlineDataService implements IDataService {

    @Bean
    ErgastManager ergastManager;

    @AfterInject
    void afterInjenction() {
        ergastManager.getErgast().setSeason(Ergast.CURRENT_SEASON);
    }

    @Override
    @NonNull
    public List<F1Driver> loadDrivers() {
        List<F1Driver> drivers = new ArrayList<>();
        try {
            List<com.gmail.fattazzo.formula1world.ergast.json.objects.Driver> jsonDrivers = ergastManager.getDrivers();
            for (com.gmail.fattazzo.formula1world.ergast.json.objects.Driver jsonDriver : jsonDrivers) {
                drivers.add(jsonDriver.toF1Driver());
            }
        } catch (Exception e) {
            drivers = new ArrayList<>();
        }
        return drivers;
    }

    /**
     * Load all constructors of the current season.
     *
     * @return constructors
     */
    @Override
    @NonNull
    public List<F1Constructor> loadConstructors() {
        List<F1Constructor> constructors = new ArrayList<>();
        try {
            List<com.gmail.fattazzo.formula1world.ergast.json.objects.Constructor> jsonConstructors = ergastManager.getConstructors();
            for (com.gmail.fattazzo.formula1world.ergast.json.objects.Constructor jsonConstructor : jsonConstructors) {
                constructors.add(jsonConstructor.toF1Constructor());
            }
        } catch (Exception e) {
            constructors = new ArrayList<>();
        }
        return constructors;
    }

    @Override
    @NonNull
    public List<F1Result> loadDriverRacesResult(F1Driver driver) {
        List<F1Result> results = new ArrayList<>();
        try {
            List<RaceResults> jsonResults = ergastManager.getDriverRacersResult(driver.driverRef);
            for (RaceResults jsonresult : jsonResults) {
                results.addAll(jsonresult.toF1Result());
            }
        } catch (Exception e) {
            results = new ArrayList<>();
        }
        return results;
    }

    @Override
    @NonNull
    public List<F1Result> loadConstructorRacesResult(F1Constructor constructor) {
        List<F1Result> results = new ArrayList<>();
        try {
            List<RaceResults> jsonResults = ergastManager.getConstructorRacersResult(constructor.constructorRef);
            for (RaceResults jsonresult : jsonResults) {
                results.addAll(jsonresult.toF1Result());
            }
        } catch (Exception e) {
            results = new ArrayList<>();
        }
        return results;
    }

    @Override
    @NonNull
    public List<F1DriverStandings> loadDriverStandings() {
        List<F1DriverStandings> f1DriverStandings = new ArrayList<>();
        try {
            List<DriverStandings> jsonDriverStandings = ergastManager.getDriverStandings(Ergast.NO_ROUND);
            for (DriverStandings jsonDriverStanding : jsonDriverStandings) {
                f1DriverStandings.add(jsonDriverStanding.toF1DriverStandings());
            }
        } catch (Exception e) {
            f1DriverStandings = new ArrayList<>();
        }

        return f1DriverStandings;
    }

    @Override
    @Nullable
    public F1DriverStandings loadDriverLeader() {

        DriverStandings driverStandings = ergastManager.getDriverLeader();
        if (driverStandings != null) {
            return driverStandings.toF1DriverStandings();
        }
        return null;
    }

    @Override
    @NonNull
    public List<F1ConstructorStandings> loadConstructorStandings() {
        List<F1ConstructorStandings> f1ConstructorStandings = new ArrayList<>();
        try {
            List<ConstructorStandings> jsonConstructorStandings = ergastManager.getConstructorStandings(Ergast.NO_ROUND);
            for (ConstructorStandings jsonConstructorStanding : jsonConstructorStandings) {
                f1ConstructorStandings.add(jsonConstructorStanding.toF1ConstructorStandings());
            }
        } catch (Exception e) {
            f1ConstructorStandings = new ArrayList<>();
        }

        return f1ConstructorStandings;
    }

    @NonNull
    @Override
    public List<F1Race> loadRaces() {
        List<Schedule> schedules;
        try {
            schedules = ergastManager.getSchedules();
        } catch (Exception e) {
            schedules = new ArrayList<>();
        }

        List<F1Race> f1Races = new ArrayList<>();

        for (Schedule schedule : schedules) {
            f1Races.add(schedule.toF1Race());
        }

        return f1Races;
    }

    @NonNull
    @Override
    public List<F1Result> loadRaceResult(F1Race race) {
        List<F1Result> results = new ArrayList<>();
        try {
            List<RaceResult> jsonResults = ergastManager.getRaceResults(race.round);
            for (RaceResult jsonresult : jsonResults) {
                results.add(jsonresult.toF1Result());
            }
        } catch (Exception e) {
            results = new ArrayList<>();
        }
        return results;
    }

    @NonNull
    @Override
    public List<F1Qualification> loadQualification(F1Race race) {
        List<F1Qualification> results = new ArrayList<>();

        try {
            List<Qualification> jsonResults = ergastManager.getQualificationResults(race.round);
            for (Qualification jsonQualification : jsonResults) {
                results.add(jsonQualification.toF1Qualification(race));
            }
        } catch (Exception e) {
            results = new ArrayList<>();
        }

        return results;
    }
}
