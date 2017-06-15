package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Result;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class RaceResult {
    private int number;
    private int position;
    private String positionText;
    private int points;
    private Driver driver;
    private Constructor constructor;

    private int grid;
    private int laps;
    private String status;
    private Time time;
    private FastestLap fastestLap;

    public RaceResult(int number, int position, String positionText,
                      int points, Driver driver, Constructor constructor,
                      int grid, int laps, String status, Time time, FastestLap fastestLap) {
        this.number = number;
        this.position = position;
        this.positionText = positionText;
        this.points = points;
        this.driver = driver;
        this.constructor = constructor;
        this.grid = grid;
        this.laps = laps;
        this.status = status;
        this.time = time;
        this.fastestLap = fastestLap;
    }

    public F1Result toF1Result() {
        F1Result f1Result = new F1Result();

        f1Result.number = number;
        f1Result.grid = grid;
        f1Result.position = position;
        f1Result.positionText = positionText;
        f1Result.positionOrder = position;
        f1Result.points = points;
        f1Result.status = status;
        f1Result.laps = laps;
        if (constructor != null) {
            f1Result.constructor = constructor.toF1Constructor();
        }
        if (driver != null) {
            f1Result.driver = driver.toF1Driver();
        }
        if (fastestLap != null) {
            f1Result.fastestLap = fastestLap.toF1FastestLap();
        }
        if (time != null) {
            f1Result.time = time.toF1Time();
        }
        return f1Result;
    }

    public int getNumber() {
        return number;
    }

    public int getPosition() {
        return position;
    }

    public String getPositionText() {
        return positionText;
    }

    public int getPoints() {
        return points;
    }

    public Driver getDriver() {
        return driver;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public int getGrid() {
        return grid;
    }

    public int getLaps() {
        return laps;
    }

    public String getStatus() {
        return status;
    }

    public Time getTime() {
        return time;
    }

    public FastestLap getFastestLap() {
        return fastestLap;
    }

    @Override
    public String toString() {
        return "RaceResult{" +
                "number=" + number +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", grid=" + grid +
                ", laps=" + laps +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", fastestLap=" + fastestLap +
                '}';
    }
}
