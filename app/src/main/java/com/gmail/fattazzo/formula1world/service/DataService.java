package com.gmail.fattazzo.formula1world.service;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.gmail.fattazzo.formula1world.R;
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
import com.gmail.fattazzo.formula1world.ergast.DataCache;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.IDataService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.LocalDBDataService;
import com.gmail.fattazzo.formula1world.ergast.json.service.OnlineDataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

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
    Utils utils;

    @Bean
    Ergast ergast;

    @Bean
    OnlineDataService onlineDataService;

    @Bean
    LocalDBDataService localDBDataService;

    @Bean
    DataCache dataCache;

    @Bean
    ApplicationPreferenceManager preferenceManager;

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

    public void clearConstructorsCache() {
        dataCache.clearConstructors();
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

    public void clearDriverRaceResultsCache(F1Driver driver) {
        dataCache.clearDriverRaceResults(driver);
    }

    public void clearConstructorRaceResultsCache(F1Constructor constructor) {
        dataCache.clearConstructorRaceResults(constructor);
    }

    public void clearRacePitStopsCache(F1Race race) {
        dataCache.clearRacePitStops(race);
    }

    public void clearColorsCache() {
        dataCache.clearConstructorColors();
        dataCache.clearDriverColors();
    }
    // ----------------------------------------------------------


    @Override
    public F1Season loadSeason(int year) {
        return getDataServiceImpl().loadSeason(year);
    }

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

    @Override
    public void updateSeason(F1Season season) {
        getDataServiceImpl().updateSeason(season);
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

    public int getSelectedSeasons() {
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
    public synchronized List<F1Driver> loadDrivers() {
        List<F1Driver> drivers = dataCache.getDrivers();
        if (CollectionUtils.isEmpty(drivers)) {
            drivers = localDBDataService.loadDrivers();
            dataCache.setDrivers(drivers);
        }
        return drivers;
    }

    @NonNull
    @Override
    public synchronized List<F1Constructor> loadConstructors() {
        List<F1Constructor> constructors = dataCache.getConstructors();
        if (CollectionUtils.isEmpty(constructors)) {
            constructors = localDBDataService.loadConstructors();
            dataCache.setConstructors(constructors);
        }
        return constructors;
    }

    @NonNull
    @Override
    public synchronized List<F1Result> loadDriverRacesResult(F1Driver driver) {
        List<F1Result> results = dataCache.getDriverRaceResults(driver);
        if (CollectionUtils.isEmpty(results)) {
            results = getDataServiceImpl().loadDriverRacesResult(driver);
            dataCache.setDriverRaceResults(driver, results);
        }
        return results;
    }

    @NonNull
    @Override
    public synchronized List<F1Result> loadConstructorRacesResult(F1Constructor constructor) {
        List<F1Result> results = dataCache.getConstructorRaceResults(constructor);
        if (CollectionUtils.isEmpty(results)) {
            results = getDataServiceImpl().loadConstructorRacesResult(constructor);
            dataCache.setConstructorRaceResults(constructor, results);
        }
        return results;
    }

    /**
     * Load current race scheduled.
     *
     * @return current race
     */
    @Nullable
    public synchronized F1Race loadCurrentSchedule() {
        List<F1Race> races = loadRaces();

        Calendar currentDateEnd = Calendar.getInstance();
        currentDateEnd.add(Calendar.HOUR_OF_DAY, 2);
        String currentDate = DateFormatUtils.format(currentDateEnd, "yyyy-MM-dd'T'HH:mm:ss");

        for (F1Race race : CollectionUtils.emptyIfNull(races)) {
            String scheudleDateUTC = race.date + "T" + race.time;
            String scheduleDateLocal = utils.convertUTCDateToLocal(scheudleDateUTC, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss");

            if (scheduleDateLocal.compareTo(currentDate) >= 0) {
                return race;
            }
        }

        return null;
    }

    @NonNull
    @Override
    public synchronized List<F1DriverStandings> loadDriverStandings() {
        List<F1DriverStandings> driverStandings = dataCache.getDriverStandings();
        if (CollectionUtils.isEmpty(driverStandings)) {
            driverStandings = getDataServiceImpl().loadDriverStandings();
            dataCache.setDriverStandings(driverStandings);
        }
        return driverStandings;
    }

    @Nullable
    @Override
    public synchronized F1DriverStandings loadDriverLeader() {

        // First get from cache
        List<F1DriverStandings> standings = loadDriverStandings();
        for (F1DriverStandings standing : standings) {
            if (standing.position == 1) {
                return standing;
            }
        }
        return getDataServiceImpl().loadDriverLeader();
    }

    @NonNull
    @Override
    public synchronized List<F1ConstructorStandings> loadConstructorStandings() {
        List<F1ConstructorStandings> constructorStandings = dataCache.getConstructorStandings();
        if (CollectionUtils.isEmpty(constructorStandings)) {
            constructorStandings = getDataServiceImpl().loadConstructorStandings();
            dataCache.setConstructorStandings(constructorStandings);
        }
        return constructorStandings;
    }

    @NonNull
    @Override
    public synchronized List<F1Race> loadRaces() {
        List<F1Race> races = dataCache.getRaces();
        if (CollectionUtils.isEmpty(races)) {
            races = localDBDataService.loadRaces();
            dataCache.setRaces(races);
        }
        return races;
    }

    @NonNull
    @Override
    public synchronized List<F1Result> loadRaceResult(F1Race race) {
        List<F1Result> results = dataCache.getRaceResultsCache(race);
        if (CollectionUtils.isEmpty(results)) {
            results = localDBDataService.loadRaceResult(race);
            if (CollectionUtils.isEmpty(results)) {
                results = onlineDataService.loadRaceResult(race);
            }
            dataCache.setRaceResults(race, results);
        }
        return results;
    }

    @NonNull
    @Override
    public synchronized List<F1Qualification> loadQualification(F1Race race) {
        List<F1Qualification> qualifications = dataCache.getRaceQualificationsCache(race);
        if (CollectionUtils.isEmpty(qualifications)) {
            qualifications = localDBDataService.loadQualification(race);
            if (CollectionUtils.isEmpty(qualifications)) {
                qualifications = onlineDataService.loadQualification(race);
            }
            dataCache.setRaceQualifications(race, qualifications);
        }
        return qualifications;
    }

    @NonNull
    @Override
    public List<F1PitStop> loadPitStops(F1Race race) {
        List<F1PitStop> pitStops = dataCache.getRacePitStops(race);
        if (CollectionUtils.isEmpty(pitStops)) {
            pitStops = localDBDataService.loadPitStops(race);
            if (CollectionUtils.isEmpty(pitStops)) {
                pitStops = onlineDataService.loadPitStops(race);
            }
            dataCache.setRacePitStops(race, pitStops);
        }
        return pitStops;
    }

    @Override
    public List<F1LapTime> loadLaps(@NonNull F1Race race, @NonNull F1Driver driver) {
        List<F1LapTime> lapTimes = dataCache.getRaceLapTimes(race, driver);
        if (CollectionUtils.isEmpty(lapTimes)) {
            if (!hasLocalLapsData(race)) {
                lapTimes = onlineDataService.loadLaps(race, driver);
            } else {
                lapTimes = localDBDataService.loadLaps(race, driver);
            }
            if (CollectionUtils.isNotEmpty(lapTimes)) {
                dataCache.setRaceLapTimes(race, driver, lapTimes);
            }
        }
        return lapTimes;
    }

    public int loadContructorColor(F1Constructor constructor) {
        Integer color = dataCache.getConstructorColor(constructor);
        if (constructor != null && color == null) {
            // try from db
            color = localDBDataService.loadContructorColor(constructor);
            if (color == null) {
                // try from standard
                try {
                    int colorId = context.getResources().getIdentifier(constructor.constructorRef, "color", context.getPackageName());
                    color = Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(colorId)));
                } catch (Exception e) {
                    color = null;
                }
            }

            if (color != null) {
                dataCache.setConstructorColor(constructor, color);
            }
        }

        return ObjectUtils.defaultIfNull(color, android.R.color.transparent);
    }

    public int loadDriverColor(F1Driver driver) {
        Integer color = dataCache.getDriverColor(driver);
        if (driver != null && color == null) {
            // try from db
            color = localDBDataService.loadDriverColor(driver);
            if (color == null) {
                // try from standard
                try {
                    F1Constructor constructor = localDBDataService.loadConstructor(driver);
                    int colorId = context.getResources().getIdentifier(constructor.constructorRef, "color", context.getPackageName());
                    color = Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(colorId)));
                } catch (Exception e) {
                    color = null;
                }
            }

            if (color != null) {
                dataCache.setDriverColor(driver, color);
            }
        }

        return ObjectUtils.defaultIfNull(color, ContextCompat.getColor(context,R.color.background_color_dark));
    }

    public boolean hasLocalLapsData(F1Race race) {
        return localDBDataService.hasLocalLapsData(race);
    }
}
