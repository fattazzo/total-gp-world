package com.gmail.fattazzo.formula1world.ergast.json.objects;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class Lap {
    private int number;
    private List<Timing> timings;

    public Lap(int number, List<Timing> timings) {
        this.number = number;
        this.timings = timings;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "number=" + number +
                ", timings=" + timings +
                '}';
    }
}
