package com.gmail.fattazzo.formula1world.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.LocalDBDataService;
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

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

    @Bean
    Ergast ergast;

    @Bean
    OnlineDataService onlineDataService;

    @Bean
    LocalDBDataService localDBDataService;

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
        if (ergast == null || ergast.getSeason() == Ergast.CURRENT_SEASON) {
            return onlineDataService;
        } else {
            return localDBDataService;
        }
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
}
