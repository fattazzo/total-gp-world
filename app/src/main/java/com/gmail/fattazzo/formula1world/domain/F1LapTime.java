package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/07/17
 */
public class F1LapTime {

    public F1Race race;

    public F1Driver driver;

    public int lap;

    public int position;

    public String time;

    public int milliseconds;

    @Override
    public String toString() {
        return "LapTime{" +
                "race=" + race +
                ", driver=" + driver +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                '}';
    }
}
