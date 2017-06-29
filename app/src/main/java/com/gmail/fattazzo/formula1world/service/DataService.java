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
    ErgastDBImporter dbImporter;

    private List<Integer> availableSeasons;

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
        return getDataServiceImpl().loadDrivers();
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
        return getDataServiceImpl().loadDriverStandings();
    }

    @Nullable
    @Override
    public F1DriverStandings loadDriverLeader() {
        return getDataServiceImpl().loadDriverLeader();
    }

    @NonNull
    @Override
    public List<F1ConstructorStandings> loadConstructorStandings() {
        return getDataServiceImpl().loadConstructorStandings();
    }

    @NonNull
    @Override
    public List<F1Race> loadRaces() {
        return getDataServiceImpl().loadRaces();
    }

    @NonNull
    @Override
    public List<F1Result> loadRaceResult(F1Race race) {
        return getDataServiceImpl().loadRaceResult(race);
    }

    @NonNull
    @Override
    public List<F1Qualification> loadQualification(F1Race race) {
        return getDataServiceImpl().loadQualification(race);
    }
}
