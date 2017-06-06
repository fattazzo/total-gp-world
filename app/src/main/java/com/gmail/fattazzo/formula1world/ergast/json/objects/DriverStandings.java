package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class DriverStandings {
    private int position;
    private String positionText;
    private float points;
    private int wins;
    private Driver driver;
    private List<Constructor> constructors;

    public DriverStandings(int position, String positionText, float points, int wins, Driver driver, List<Constructor> constructors) {
        this.position = position;
        this.positionText = positionText;
        this.points = points;
        this.wins = wins;
        this.driver = driver;
        this.constructors = constructors;
    }

    public F1DriverStandings toF1DriverStandings() {
        F1DriverStandings f1DriverStandings = new F1DriverStandings();
        f1DriverStandings.position = this.position;
        f1DriverStandings.positionText = this.positionText;
        f1DriverStandings.wins = this.wins;
        f1DriverStandings.points = this.points;
        if (this.driver != null) {
            f1DriverStandings.driver = this.driver.toF1Driver();
        }
        if (CollectionUtils.isNotEmpty(this.constructors)) {
            f1DriverStandings.constructor = this.constructors.get(0).toF1Constructor();
        }
        return f1DriverStandings;
    }

    @Override
    public String toString() {
        return "DriverStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", driver=" + driver +
                ", constructors=" + constructors +
                '}';
    }
}
