package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Qualification {
    private int number;
    private int position;
    private Driver driver;
    private Constructor constructor;
    private String q1;
    private String q2;
    private String q3;

    public F1Qualification toF1Qualification(F1Race race) {
        F1Qualification f1Qualification = new F1Qualification();
        f1Qualification.race = race;
        if (this.driver != null) {
            f1Qualification.driver = this.driver.toF1Driver();
        }
        if (this.constructor != null) {
            f1Qualification.constructor = this.constructor.toF1Constructor();
        }
        f1Qualification.number = this.number;
        f1Qualification.position = this.position;
        f1Qualification.q1 = this.q1;
        f1Qualification.q2 = this.q2;
        f1Qualification.q3 = this.q3;
        return f1Qualification;
    }

    @Override
    public String toString() {
        return "Qualification{" +
                "number=" + number +
                ", position=" + position +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}';
    }
}
