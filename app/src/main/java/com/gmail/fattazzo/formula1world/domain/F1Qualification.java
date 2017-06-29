package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 29/06/17
 */
public class F1Qualification {

    public F1Race race;

    public F1Driver driver;

    public F1Constructor constructor;

    public int number;

    public int position;

    public String q1;

    public String q2;

    public String q3;

    @Override
    public String toString() {
        return "Qualification{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", position=" + position +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}';
    }
}
