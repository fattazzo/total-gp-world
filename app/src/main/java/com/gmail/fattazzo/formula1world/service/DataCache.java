package com.gmail.fattazzo.formula1world.service;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;

import org.androidannotations.annotations.EBean;
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
class DataCache {

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

    void clearAll() {
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
    }

    void clearDriverStandings() {
        driverStandingsCache.clear();
    }

    void clearConstructorStandings() {
        constructorStandingsCache.clear();
    }

    void clearDrivers() {
        driversCache.clear();
    }

    void clearConstructors() {
        constructorsCache.clear();
    }

    void clearRaces() {
        racesCache.clear();
    }

    void clearRaceResults(F1Race race) {
        raceResultsCache.remove(race);
    }

    void clearRaceQualifications(F1Race race) {
        raceQualificationsCache.remove(race);
    }

    void clearRacePitStops(F1Race race) {
        racePitStopsCache.remove(race);
    }

    void clearDriverRaceResults(F1Driver driver) {
        driverRaceResultsCache.remove(driver);
    }

    void clearConstructorRaceResults(F1Constructor constructor) {
        constructorRaceResultsCache.remove(constructor);
    }

    List<F1Result> getRaceResultsCache(F1Race race) {
        return MapUtils.emptyIfNull(raceResultsCache).get(race);
    }

    void setRaceResults(F1Race race, List<F1Result> results) {
        raceResultsCache.put(race, results);
    }

    List<F1Qualification> getRaceQualificationsCache(F1Race race) {
        return MapUtils.emptyIfNull(raceQualificationsCache).get(race);
    }

    void setRaceQualifications(F1Race race, List<F1Qualification> qualifications) {
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

    List<F1Result> getDriverRaceResults(F1Driver driver) {
        return MapUtils.emptyIfNull(driverRaceResultsCache).get(driver);
    }

    void setDriverRaceResults(F1Driver driver, List<F1Result> results) {
        driverRaceResultsCache.put(driver, results);
    }

    public List<F1Constructor> getConstructors() {
        return ListUtils.unmodifiableList(constructorsCache);
    }

    public void setConstructors(List<F1Constructor> constructors) {
        this.constructorsCache = ListUtils.emptyIfNull(constructors);
    }

    List<F1Result> getConstructorRaceResults(F1Constructor constructor) {
        return MapUtils.emptyIfNull(constructorRaceResultsCache).get(constructor);
    }

    void setConstructorRaceResults(F1Constructor constructor, List<F1Result> results) {
        constructorRaceResultsCache.put(constructor, results);
    }

    List<F1PitStop> getRacePitStops(F1Race race) {
        return MapUtils.emptyIfNull(racePitStopsCache).get(race);
    }

    void setRacePitStops(F1Race race, List<F1PitStop> pitStops) {
        racePitStopsCache.put(race, pitStops);
    }
}
