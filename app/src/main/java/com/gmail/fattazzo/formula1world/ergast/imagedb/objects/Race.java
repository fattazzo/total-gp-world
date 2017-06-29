package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1Circuit;
import com.gmail.fattazzo.formula1world.domain.F1Race;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "races")
public class Race extends Model {

    @Column
    int year;

    @Column
    int round;

    @Column(name = "circuitId")
    Circuit circuit;

    @Column
    String url;

    @Column
    String name;

    @Column
    String date;

    @Column
    String time;

    public F1Race toF1Race() {
        F1Circuit f1Circuit = this.circuit != null ? this.circuit.toF1Circuit() : null;
        return new F1Race(this.year,this.round,this.url,this.name,f1Circuit,this.date,this.time);
    }

    @Override
    public String toString() {
        return "Race{" +
                "year=" + year +
                ", round=" + round +
                ", circuit=" + circuit +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
