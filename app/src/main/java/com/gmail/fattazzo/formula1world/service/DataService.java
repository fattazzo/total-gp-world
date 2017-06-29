package com.gmail.fattazzo.formula1world.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.activity.dbimport.DBImportActivity_;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.imagedb.importer.ErgastDBImporter;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.LocalDBDataService;
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataService implements IDataService {

    @RootContext
    Context context;

    @Bean
    Ergast ergast;

    @Bean
    OnlineDataService onlineDataService;

    @Bean
    LocalDBDataService localDBDataService;

    @Bean
    DataCache dataCache;

    @Bean
    ErgastDBImporter dbImporter;

    private List<Integer> availableSeasons;

    // ------------------- Data Cache Actions -------------------
    public void clearCache() {
        dataCache.clearAll();
    }

    public void clearDriverStandingsCache() {
        dataCache.clearDriverStandings();
    }

    public void clearConstructorStandingsCache() {
        dataCache.clearConstructorStandings();
    }

    public void clearDriversCache() {
        dataCache.clearDrivers();
    }

    public void clearRacesCache() {
        dataCache.clearRaces();
    }

    public void clearRaceResultsCache(F1Race race) {
        dataCache.clearRaceResults(race);
    }

    public void clearRaceQualifications(F1Race race) {
        dataCache.clearRaceQualifications(race);
    }

    // ----------------------------------------------------------

    public List<Integer> getAvailableSeasons() {
        if (availableSeasons == null) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            availableSeasons = new ArrayList<>();
            for (int i = currentYear; i >= 1950; i--) {
                availableSeasons.add(i);
            }
        }
        return availableSeasons;
    }

    private IDataService getDataServiceImpl() {

        int season = getSelectedSeasons();
        boolean dbSeasonFound = localDBDataService.loadSeason(season) != null;

        // local db seasons win vs online seasons
        if (dbSeasonFound) {
            return localDBDataService;
        } else {
            return onlineDataService;
        }
    }

    public void importDBIfNecessary() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int selectedSeason = getSelectedSeasons();
        boolean dbSeasonFound = localDBDataService.loadSeason(selectedSeason) != null;

        if (selectedSeason < currentYear && !dbSeasonFound) {
            Intent i = new Intent(context, DBImportActivity_.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

            dbImporter.importDBImage();
        }
    }

    private int getSelectedSeasons() {
        int season;
        try {
            if (ergast.getSeason() == Ergast.CURRENT_SEASON) {
                season = Calendar.getInstance().get(Calendar.YEAR);
            } else {
                season = ergast.getSeason();
            }
        } catch (Exception e) {
            season = -1;
        }
        return season;
    }

    @NonNull
    @Override
    public List<F1Driver> loadDrivers() {
        List<F1Driver> drivers = dataCache.getDrivers();
        if (CollectionUtils.isEmpty(drivers)) {
            drivers = getDataServiceImpl().loadDrivers();
            dataCache.setDrivers(drivers);
        }
        return drivers;
    }

    @NonNull
    @Override
    public List<F1Constructor> loadConstructors() {
        return getDataServiceImpl().loadConstructors();
    }

    @NonNull
    @Override
    public List<F1Result> loadDriverRacesResult(String driverRef) {
        return getDataServiceImpl().loadDriverRacesResult(driverRef);
    }

    @NonNull
    @Override
    public List<F1Result> loadConstructorRacesResult(String constructorId) {
        return getDataServiceImpl().loadConstructorRacesResult(constructorId);
    }

    @Nullable
    @Override
    public F1Race loadCurrentSchedule() {
        return getDataServiceImpl().loadCurrentSchedule();
    }

    @NonNull
    @Override
    public List<F1DriverStandings> loadDriverStandings() {
        List<F1DriverStandings> driverStandings = dataCache.getDriverStandings();
        if (CollectionUtils.isEmpty(driverStandings)) {
            driverStandings = getDataServiceImpl().loadDriverStandings();
            dataCache.setDriverStandings(driverStandings);
        }
        return driverStandings;
    }

    @Nullable
    @Override
    public F1DriverStandings loadDriverLeader() {
        return getDataServiceImpl().loadDriverLeader();
    }

    @NonNull
    @Override
    public List<F1ConstructorStandings> loadConstructorStandings() {
        List<F1ConstructorStandings> constructorStandings = dataCache.getConstructorStandings();
        if (CollectionUtils.isEmpty(constructorStandings)) {
            constructorStandings = getDataServiceImpl().loadConstructorStandings();
            dataCache.setConstructorStandings(constructorStandings);
        }
        return constructorStandings;
    }

    @NonNull
    @Override
    public List<F1Race> loadRaces() {
        List<F1Race> races = dataCache.getRaces();
        if (CollectionUtils.isEmpty(races)) {
            races = getDataServiceImpl().loadRaces();
            dataCache.setRaces(races);
        }
        return races;
    }

    @NonNull
    @Override
    public List<F1Result> loadRaceResult(F1Race race) {
        List<F1Result> results = dataCache.getRaceResultsCache(race);
        if (CollectionUtils.isEmpty(results)) {
            results = getDataServiceImpl().loadRaceResult(race);
            dataCache.setRaceResults(race, results);
        }
        return results;
    }

    @NonNull
    @Override
    public List<F1Qualification> loadQualification(F1Race race) {
        List<F1Qualification> qualifications = dataCache.getRaceQualificationsCache(race);
        if (CollectionUtils.isEmpty(qualifications)) {
            qualifications = getDataServiceImpl().loadQualification(race);
            dataCache.setRaceQualifications(race, qualifications);
        }
        return qualifications;
    }
}
