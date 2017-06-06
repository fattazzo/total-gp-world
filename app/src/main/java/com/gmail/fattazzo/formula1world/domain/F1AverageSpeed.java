package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1AverageSpeed {

    public String units;

    public Double speed;

    public F1AverageSpeed(String units, Double speed) {
        this.units = units;
        this.speed = speed;
    }

    public F1AverageSpeed() {

    }

    @Override
    public String toString() {
        return "AverageSpeed{" +
                "units='" + units + '\'' +
                ", speed=" + speed +
                '}';
    }
}
