package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class PitStop {

    private String driverId;
    private int stop;
    private int lap;
    private String time;
    private String duration;

    public F1PitStop toF1PitStop(F1Race race, List<F1Driver> drivers) {
        F1PitStop f1PitStop = new F1PitStop();
        f1PitStop.race = race;
        F1Driver f1Driver = new F1Driver();
        f1Driver.driverRef = driverId;
        if (drivers.contains(f1Driver)) {
            f1PitStop.driver = drivers.get(drivers.indexOf(f1Driver));
        }
        f1PitStop.duration = duration;
        f1PitStop.lap = lap;
        f1PitStop.stop = stop;
        f1PitStop.time = time;
        f1PitStop.milliseconds = Parser.parseTimeToMillisec(duration);

        return f1PitStop;
    }

    @Override
    public String toString() {
        return "PitStop{" +
                "driverId='" + driverId + '\'' +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
