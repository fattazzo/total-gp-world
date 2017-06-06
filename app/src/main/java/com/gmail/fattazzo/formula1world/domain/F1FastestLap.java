package com.gmail.fattazzo.formula1world.domain;

import com.gmail.fattazzo.formula1world.ergast.json.objects.Time;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1FastestLap {

    public int rank;

    public int lap;

    public F1Time time;

    public F1AverageSpeed averageSpeed;

    public F1FastestLap() {
    }

    public F1FastestLap(int rank, int lap, F1Time time, F1AverageSpeed averageSpeed) {
        this.rank = rank;
        this.lap = lap;
        this.time = time;
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return "FastestLap{" +
                "rank=" + rank +
                ", lap=" + lap +
                ", time=" + time +
                ", averageSpeed=" + averageSpeed +
                '}';
    }
}
