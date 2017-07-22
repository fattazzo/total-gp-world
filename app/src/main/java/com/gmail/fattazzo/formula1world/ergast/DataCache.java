package com.gmail.fattazzo.formula1world.ergast;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;

import org.androidannotations.annotations.EBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fattazzo
 *         <p/>
 *         date: 29/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataCache {

    private Map<F1Race, List<F1Qualification>> raceQualificationsCache = new HashMap<>();
    private Map<F1Race, List<F1Result>> raceResultsCache = new HashMap<>();
    private Map<F1Race, List<F1PitStop>> racePitStopsCache = new HashMap<>();
    private List<F1Driver> driversCache = new ArrayList<>();
    private List<F1Constructor> constructorsCache = new ArrayList<>();
    private List<F1Race> racesCache = new ArrayList<>();
    private List<F1DriverStandings> driverStandingsCache = new ArrayList<>();
    private List<F1ConstructorStandings> constructorStandingsCache = new ArrayList<>();
    private Map<F1Driver, List<F1Result>> driverRaceResultsCache = new HashMap<>();
    private Map<F1Constructor, List<F1Result>> constructorRaceResultsCache = new HashMap<>();
    private Map<F1Race, Map<F1Driver, List<F1LapTime>>> raceLapTimesCache = new HashMap<>();
    private Map<F1Constructor, Integer> constructorColorsCache = new HashMap<>();
    private Map<F1Driver, Integer> driverColorsCache = new HashMap<>();

    public void clearAll() {
        raceQualificationsCache.clear();
        raceResultsCache.clear();
        racePitStopsCache.clear();
        driversCache.clear();
        constructorsCache.clear();
        racesCache.clear();
        constructorStandingsCache.clear();
        driverStandingsCache.clear();
        driverRaceResultsCache.clear();
        constructorRaceResultsCache.clear();
        raceLapTimesCache.clear();
        constructorRaceResultsCache.clear();
        constructorColorsCache.clear();
        driverColorsCache.clear();
    }

    public void clearDriverStandings() {
        driverStandingsCache.clear();
    }

    public void clearConstructorStandings() {
        constructorStandingsCache.clear();
    }

    public void clearDrivers() {
        driversCache.clear();
    }

    public void clearConstructors() {
        constructorsCache.clear();
    }

    public void clearRaces() {
        racesCache.clear();
    }

    public void clearRaceResults(F1Race race) {
        raceResultsCache.remove(race);
    }

    public void clearRaceQualifications(F1Race race) {
        raceQualificationsCache.remove(race);
    }

    public void clearRacePitStops(F1Race race) {
        racePitStopsCache.remove(race);
    }

    public void clearDriverRaceResults(F1Driver driver) {
        driverRaceResultsCache.remove(driver);
    }

    public void clearConstructorRaceResults(F1Constructor constructor) {
        constructorRaceResultsCache.remove(constructor);
    }

    void clearRaceLapTimes(F1Race race, F1Driver driver) {
        if (race == null) {
            raceLapTimesCache.clear();
        } else {
            Map<F1Driver, List<F1LapTime>> raceLaps = MapUtils.emptyIfNull(raceLapTimesCache.get(race));
            if (driver == null) {
                raceLaps.clear();
            } else {
                CollectionUtils.emptyIfNull(raceLaps.get(driver)).clear();
            }
        }
    }

    public void clearConstructorColors() {
        constructorColorsCache.clear();
    }

    public void clearDriverColors() {
        driverColorsCache.clear();
    }

    // ------------------------------------------------------------------------

    public List<F1Result> getRaceResultsCache(F1Race race) {
        return MapUtils.emptyIfNull(raceResultsCache).get(race);
    }

    public void setRaceResults(F1Race race, List<F1Result> results) {
        raceResultsCache.put(race, results);
    }

    public List<F1Qualification> getRaceQualificationsCache(F1Race race) {
        return MapUtils.emptyIfNull(raceQualificationsCache).get(race);
    }

    public void setRaceQualifications(F1Race race, List<F1Qualification> qualifications) {
        raceQualificationsCache.put(race, qualifications);
    }

    public List<F1Driver> getDrivers() {
        return ListUtils.unmodifiableList(driversCache);
    }

    public void setDrivers(List<F1Driver> driversCache) {
        this.driversCache = ListUtils.emptyIfNull(driversCache);
    }

    public List<F1Race> getRaces() {
        return ListUtils.unmodifiableList(racesCache);
    }

    public void setRaces(List<F1Race> racesCache) {
        this.racesCache = ListUtils.emptyIfNull(racesCache);
    }

    public List<F1DriverStandings> getDriverStandings() {
        return ListUtils.unmodifiableList(driverStandingsCache);
    }

    public void setDriverStandings(List<F1DriverStandings> driverStandingsCache) {
        this.driverStandingsCache = ListUtils.emptyIfNull(driverStandingsCache);
    }

    public List<F1ConstructorStandings> getConstructorStandings() {
        return ListUtils.unmodifiableList(constructorStandingsCache);
    }

    public void setConstructorStandings(List<F1ConstructorStandings> constructorStandingsCache) {
        this.constructorStandingsCache = ListUtils.emptyIfNull(constructorStandingsCache);
    }

    public List<F1Result> getDriverRaceResults(F1Driver driver) {
        return MapUtils.emptyIfNull(driverRaceResultsCache).get(driver);
    }

    public void setDriverRaceResults(F1Driver driver, List<F1Result> results) {
        driverRaceResultsCache.put(driver, results);
    }

    public List<F1Constructor> getConstructors() {
        return ListUtils.unmodifiableList(constructorsCache);
    }

    public void setConstructors(List<F1Constructor> constructors) {
        this.constructorsCache = ListUtils.emptyIfNull(constructors);
    }

    public List<F1Result> getConstructorRaceResults(F1Constructor constructor) {
        return MapUtils.emptyIfNull(constructorRaceResultsCache).get(constructor);
    }

    public void setConstructorRaceResults(F1Constructor constructor, List<F1Result> results) {
        constructorRaceResultsCache.put(constructor, results);
    }

    public List<F1PitStop> getRacePitStops(F1Race race) {
        return MapUtils.emptyIfNull(racePitStopsCache).get(race);
    }

    public void setRacePitStops(F1Race race, List<F1PitStop> pitStops) {
        racePitStopsCache.put(race, pitStops);
    }

    public List<F1LapTime> getRaceLapTimes(F1Race race, F1Driver driver) {
        return MapUtils.emptyIfNull(raceLapTimesCache.get(race)).get(driver);
    }

    public void setRaceLapTimes(F1Race race, F1Driver driver, List<F1LapTime> lapTimes) {
        Map<F1Driver, List<F1LapTime>> lap = new HashMap<>();
        lap.put(driver, ListUtils.emptyIfNull(lapTimes));

        raceLapTimesCache.put(race, MapUtils.emptyIfNull(lap));
    }

    public Integer getConstructorColor(F1Constructor constructor) {
        return constructorColorsCache.get(constructor);
    }

    public void setConstructorColor(F1Constructor constructor, Integer color) {
        constructorColorsCache.put(constructor, color);
    }

    public Integer getDriverColor(F1Driver driver) {
        return driverColorsCache.get(driver);
    }

    public void setDriverColor(F1Driver driver, Integer color) {
        driverColorsCache.put(driver, color);
    }
}
