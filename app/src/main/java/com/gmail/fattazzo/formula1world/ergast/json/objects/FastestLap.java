package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1FastestLap;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class FastestLap {
    private int rank;
    private int lap;
    private Time time;
    private AverageSpeed averageSpeed;

    public FastestLap(int rank, int lap, Time time, AverageSpeed averageSpeed) {
        this.rank = rank;
        this.lap = lap;
        this.time = time;
        this.averageSpeed = averageSpeed;
    }

    F1FastestLap toF1FastestLap() {
        F1FastestLap f1FastestLap = new F1FastestLap();
        f1FastestLap.rank = this.rank;
        f1FastestLap.lap = this.lap;
        if (this.time != null) {
            f1FastestLap.time = this.time.toF1Time();
        }
        if (this.averageSpeed != null) {
            f1FastestLap.averageSpeed = this.averageSpeed.toF1AverageSpeed();
        }
        return f1FastestLap;
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
