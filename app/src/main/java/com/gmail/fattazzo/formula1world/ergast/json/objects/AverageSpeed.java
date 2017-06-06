package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1AverageSpeed;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class AverageSpeed {
    private String units;
    private double speed;

    public AverageSpeed(String units, double speed) {
        this.units = units;
        this.speed = speed;
    }

    F1AverageSpeed toF1AverageSpeed() {
        F1AverageSpeed f1AverageSpeed = new F1AverageSpeed();
        f1AverageSpeed.units = this.units;
        f1AverageSpeed.speed = this.speed;
        return f1AverageSpeed;
    }

    @Override
    public String toString() {
        return "AverageSpeed{" +
                "units='" + units + '\'' +
                ", speed=" + speed +
                '}';
    }
}
