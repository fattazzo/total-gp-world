package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1Time {

    public int millis;

    public String time;

    public F1Time() {
    }

    public F1Time(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Time{" +
                "millis=" + millis +
                ", time='" + time + '\'' +
                '}';
    }
}
