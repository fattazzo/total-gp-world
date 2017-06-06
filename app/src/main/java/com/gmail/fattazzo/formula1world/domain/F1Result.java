package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1Result {

    public F1Race race;

    public F1Driver driver;

    public F1Constructor constructor;

    public int number;

    public int grid;

    public int position;

    public String positionText;

    public int positionOrder;

    public float points;

    public int laps;

    public F1Time time;

    public F1FastestLap fastestLap;

    public String status;

    @Override
    public String toString() {
        return "Result{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", grid=" + grid +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", positionOrder=" + positionOrder +
                ", points=" + points +
                ", laps=" + laps +
                ", time='" + time + '\'' +
                ", fastestLap=" + fastestLap +
                ", status=" + status +
                '}';
    }
}
