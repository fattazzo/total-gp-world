package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "lapTimes")
public class LapTime extends Model {

    @Column(name = "raceId")
    Race race;

    @Column(name = "driverId")
    Driver driver;

    @Column
    int lap;

    @Column
    int position;

    @Column
    String time;

    @Column
    int milliseconds;

    public F1LapTime toF1LapTime() {
        F1LapTime f1LapTime = new F1LapTime();
        if (race != null) {
            f1LapTime.race = race.toF1Race();
        }
        if (driver != null) {
            f1LapTime.driver = driver.toF1Driver();
        }
        f1LapTime.milliseconds = milliseconds;
        f1LapTime.time = time;
        f1LapTime.position = position;
        f1LapTime.lap = lap;
        return f1LapTime;
    }

    @Override
    public String toString() {
        return "LapTime{" +
                "race=" + race +
                ", driver=" + driver +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                '}';
    }
}
