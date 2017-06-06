package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "pitStops")
public class PitStop extends Model {

    @Column(name = "raceId")
    Race race;

    @Column(name = "driverId")
    Driver driver;

    @Column
    int stop;

    @Column
    int lap;

    @Column(name = "time")
    String time;

    @Column
    String duration;

    @Column
    int milliseconds;

    @Override
    public String toString() {
        return "PitStop{" +
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
