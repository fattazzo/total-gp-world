package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "qualifying")
public class Qualification extends Model {

    @Column(name = "raceId")
    Race race;

    @Column(name = "driverId")
    Driver driver;

    @Column(name = "constructorId")
    Constructor constructor;

    @Column
    int number;

    @Column
    int position;

    @Column
    String q1;

    @Column
    String q2;

    @Column
    String q3;

    public F1Qualification toF1Qualification() {
        F1Qualification f1Qualification = new F1Qualification();
        f1Qualification.race = this.race.toF1Race();
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
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", position=" + position +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}';
    }
}
