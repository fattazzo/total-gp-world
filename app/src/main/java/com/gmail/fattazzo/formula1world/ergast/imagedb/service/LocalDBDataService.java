package com.gmail.fattazzo.formula1world.ergast.imagedb.service;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.imagedb.importer.ErgastDBImporter;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;
import com.gmail.fattazzo.formula1world.service.IDataService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocalDBDataService implements IDataService {

    private static final String TAG = LocalDBDataService.class.getSimpleName();

    @Bean
    Ergast ergast;

    @Bean
    ErgastDBImporter dbImporter;

    /**
     * Load the season.
     *
     * @param year year
     * @return season loaded
     */
    public Season loadSeason(int year) {
        return new Select().from(Season.class).where("Id = ?", year).executeSingle();
    }

    @NonNull
    @Override
    public List<F1Driver> loadDrivers() {
        List<F1Driver> drivers = new ArrayList<>();

        try {
            List<Driver> dbDrivers = new Select("drivers.*").distinct().from(Driver.class)
                    .innerJoin(DriverStandings.class).on("drivers.Id = driverStandings.driverId")
                    .innerJoin(Race.class).on("races.Id = driverStandings.raceId")
                    .where("races.year = ?", ergast.getSeason())
                    .orderBy("drivers.surname").execute();

            for (Driver dbDriver : dbDrivers) {
                drivers.add(dbDriver.toF1Driver());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            drivers = new ArrayList<>();
        }

        return drivers;
    }

    @NonNull
    @Override
    public List<F1Constructor> loadConstructors() {
        List<F1Constructor> constructors = new ArrayList<>();

        try {
            List<Constructor> dbConstructors = new Select("constructors.*").distinct().from(Constructor.class)
                    .innerJoin(ConstructorStandings.class).on("constructors.Id = constructorStandings.constructorId")
                    .innerJoin(Race.class).on("races.Id = constructorStandings.raceId")
                    .where("races.year = ?", ergast.getSeason())
                    .orderBy("constructors.name").execute();

            for (Constructor dbConstructor : dbConstructors) {
                constructors.add(dbConstructor.toF1Constructor());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            constructors = new ArrayList<>();
        }

        return constructors;
    }

    @NonNull
    @Override
    public List<F1Result> loadDriverRacesResult(String driverRef) {
        List<F1Result> results = new ArrayList<>();
        try {
            List<Result> dbResults = new Select("res.*").from(Result.class).as("res")
                    .innerJoin(Race.class).as("rac").on("rac.Id = res.raceId")
                    .innerJoin(Driver.class).as("dr").on("dr.Id = res.driverId")
                    .where("rac.year = ?", ergast.getSeason())
                    .where("dr.driverRef = ?", driverRef)
                    .execute();
            for (Result result : dbResults) {
                results.add(result.toF1Result());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            results = new ArrayList<>();
        }
        return results;
    }

    @NonNull
    @Override
    public List<F1Result> loadConstructorRacesResult(String constructorRef) {
        List<F1Result> results = new ArrayList<>();
        try {
            List<Result> dbResults = new Select("res.*").from(Result.class).as("res")
                    .innerJoin(Race.class).as("rac").on("rac.Id = res.raceId")
                    .innerJoin(Constructor.class).as("cs").on("cs.Id = res.constructorId")
                    .where("rac.year = ?", ergast.getSeason())
                    .where("cs.constructorRef = ?", constructorRef)
                    .execute();
            for (Result result : dbResults) {
                results.add(result.toF1Result());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            results = new ArrayList<>();
        }
        return results;
    }

    @Nullable
    @Override
    public F1Race loadCurrentSchedule() {
        return null;
    }

    @NonNull
    @Override
    public List<F1DriverStandings> loadDriverStandings() {
        List<F1DriverStandings> f1DriverStandings = new ArrayList<>();

        try {
            List<DriverStandings> dbDriverStandings = new Select("drs.*").distinct()
                    .from(DriverStandings.class).as("drs")
                    .innerJoin(Race.class).on("races.Id = drs.raceId")
                    .where("races.year = ?", ergast.getSeason())
                    .orderBy("drs.points desc").groupBy("drs.driverId").execute();

            for (com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings dbDriverStanding : dbDriverStandings) {
                f1DriverStandings.add(dbDriverStanding.toF1DriverStandings(ergast.getSeason()));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            f1DriverStandings = new ArrayList<>();
        }

        return f1DriverStandings;
    }

    @Nullable
    @Override
    public F1DriverStandings loadDriverLeader() {
        F1DriverStandings f1DriverStandings;
        try {
            DriverStandings dbDriverStandings = new Select("drs.*").distinct()
                    .from(DriverStandings.class).as("drs")
                    .innerJoin(Race.class).on("races.Id = drs.raceId")
                    .where("races.year = ?", ergast.getSeason())
                    .orderBy("drs.points desc").groupBy("drs.driverId").limit(1).executeSingle();

            f1DriverStandings = dbDriverStandings.toF1DriverStandings(ergast.getSeason());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            f1DriverStandings = null;
        }
        return f1DriverStandings;
    }

    @NonNull
    @Override
    public List<F1ConstructorStandings> loadConstructorStandings() {
        List<F1ConstructorStandings> f1ConstructorStandings = new ArrayList<>();

        try {
            List<ConstructorStandings> dbConstructorStandings = new Select("cs.*").distinct()
                    .from(ConstructorStandings.class).as("cs")
                    .innerJoin(Race.class).on("races.Id = cs.raceId")
                    .where("races.year = ?", ergast.getSeason())
                    .orderBy("cs.points desc").groupBy("cs.constructorId").execute();

            for (ConstructorStandings dbConstructorStanding : dbConstructorStandings) {
                f1ConstructorStandings.add(dbConstructorStanding.toF1ConstructorStandings());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            f1ConstructorStandings = new ArrayList<>();
        }

        return f1ConstructorStandings;
    }
}
