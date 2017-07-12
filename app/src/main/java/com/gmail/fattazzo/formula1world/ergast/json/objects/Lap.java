package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Lap {
    private int number;
    private List<Timing> timings;

    public Lap(int number, List<Timing> timings) {
        this.number = number;
        this.timings = timings;
    }

    public F1LapTime toF1LapTime(F1Race race, F1Driver driver) {
        F1LapTime f1LapTime = new F1LapTime();
        f1LapTime.driver = driver;
        f1LapTime.race = race;
        f1LapTime.lap = number;
        f1LapTime.position = timings.get(0).getPosition();
        f1LapTime.time = timings.get(0).getTime();
        f1LapTime.milliseconds = Parser.parseTimeToMillisec(f1LapTime.time);
        return f1LapTime;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "number=" + number +
                ", timings=" + timings +
                '}';
    }
}
