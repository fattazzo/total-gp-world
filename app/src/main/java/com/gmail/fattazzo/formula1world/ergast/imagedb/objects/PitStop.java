package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;

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

    public F1PitStop f1PitStop() {
        F1PitStop f1PitStop = new F1PitStop();
        if (race != null) {
            f1PitStop.race = race.toF1Race();
        }
        if (driver != null) {
            f1PitStop.driver = driver.toF1Driver();
        }
        f1PitStop.stop = stop;
        f1PitStop.lap = lap;
        f1PitStop.time = time;
        f1PitStop.duration = duration;
        f1PitStop.milliseconds = milliseconds;
        return f1PitStop;
    }

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
