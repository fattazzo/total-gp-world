package com.gmail.fattazzo.formula1world.domain;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1Race implements Serializable {

    public int year;

    public int round;

    public String url;

    public String name;

    public F1Circuit circuit;

    public String date;

    public String time;

    public F1Race(int year, int round, String url, String name, F1Circuit circuit, String date, String time) {
        this.year = year;
        this.round = round;
        this.url = url;
        this.name = name;
        this.circuit = circuit;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "F1Race{" +
                "year=" + year +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        F1Race f1Race = (F1Race) o;

        if (year != f1Race.year) return false;
        return round == f1Race.round;

    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + round;
        return result;
    }
}
