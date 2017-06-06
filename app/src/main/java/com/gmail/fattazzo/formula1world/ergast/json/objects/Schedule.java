package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Circuit;
import com.gmail.fattazzo.formula1world.domain.F1Race;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Schedule {
    private int season;
    private int round;
    private String url;
    private String raceName;
    private Circuit circuit;
    private String date;
    private String time;

    public Schedule(int season, int round, String url, String raceName, Circuit circuit, String date, String time) {
        this.season = season;
        this.round = round;
        this.url = url;
        this.raceName = raceName;
        this.circuit = circuit;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public F1Race toF1Race() {
        F1Circuit f1Circuit = this.circuit != null ? this.circuit.toF1Circuit() : null;
        return new F1Race(season, round, url, raceName, f1Circuit, date, time);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
