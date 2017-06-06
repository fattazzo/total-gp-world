package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
public class F1DriverStandings {

    public int position;

    public String positionText;

    public float points;

    public int wins;

    public F1Driver driver;

    public F1Constructor constructor;

    @Override
    public String toString() {
        return "F1DriverStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", driver=" + driver +
                ", constructor=" + constructor +
                '}';
    }
}
