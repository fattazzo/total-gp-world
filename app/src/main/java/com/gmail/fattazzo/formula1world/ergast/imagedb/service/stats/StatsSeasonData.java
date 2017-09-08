package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/09/17
 */
public class StatsSeasonData {

    private int season;

    private int racesCompleted;
    private int rounds;

    private int drivers;
    private int constructors;

    private int winningDrivers;
    private int winningConstructos;

    private int podiumDrivers;
    private int podiumConstructors;

    private float winningDriverPoints;
    private float pointsAssigned;

    StatsSeasonData(int season, int racesCompleted, int rounds, int drivers, int constructors, int winningDrivers,
                    int winningConstructos, int podiumDrivers, int podiumConstructors, float winningDriverPoints, float pointsAssigned) {
        this.season = season;
        this.racesCompleted = racesCompleted;
        this.rounds = rounds;

        this.drivers = drivers;
        this.constructors = constructors;

        this.winningDrivers = winningDrivers;
        this.winningConstructos = winningConstructos;

        this.podiumDrivers = podiumDrivers;
        this.podiumConstructors = podiumConstructors;

        this.winningDriverPoints = winningDriverPoints;
        this.pointsAssigned = pointsAssigned;
    }

    public int getDrivers() {
        return drivers;
    }

    public int getSeason() {
        return season;
    }

    public int getRacesCompleted() {
        return racesCompleted;
    }

    public int getRounds() {
        return rounds;
    }

    public int getConstructors() {
        return constructors;
    }

    public int getWinningDrivers() {
        return winningDrivers;
    }

    public int getWinningConstructos() {
        return winningConstructos;
    }

    public int getPodiumDrivers() {
        return podiumDrivers;
    }

    public int getPodiumConstructors() {
        return podiumConstructors;
    }

    public float getWinningDriverPoints() {
        return winningDriverPoints;
    }

    public float getPointsAssigned() {
        return pointsAssigned;
    }
}
