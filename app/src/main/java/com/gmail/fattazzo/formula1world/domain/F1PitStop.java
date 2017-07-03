package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
public class F1PitStop {

    public F1Race race;

    public F1Driver driver;

    public int stop;

    public int lap;

    public String time;

    public String duration;

    public int milliseconds;

    @Override
    public String toString() {
        return "F1PitStop{" +
                "race=" + race +
                ", driver=" + driver +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", milliseconds=" + milliseconds +
                '}';
    }
}
