package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Time;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Time {
    private int millis;
    private String time;

    public Time(int millis, String time) {
        this.millis = millis;
        this.time = time;
    }

    F1Time toF1Time() {
        F1Time f1Time = new F1Time();
        f1Time.millis = this.millis;
        f1Time.time = this.time;
        return f1Time;
    }

    @Override
    public String toString() {
        return "Time{" +
                "millis=" + millis +
                ", time='" + time + '\'' +
                '}';
    }
}
