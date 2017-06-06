package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import android.support.annotation.Nullable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "driverStandings")
public class DriverStandings extends Model {

    @Column
    int raceId;

    @Column
    float points;

    @Column
    int position;

    @Column
    String positionText;

    @Column
    int wins;

    @Column(name = "driverId")
    Driver driver;

    private @Nullable Constructor getConstructor(@Nullable Integer year) {
        if(year == null) {
            return null;
        }

        return new Select("c.*").distinct()
                .from(Constructor.class).as("c")
                .innerJoin(DriverConstructor.class).as("dc").on("dc.constructorId = c.Id")
                .where("dc.driverId = ?", this.driver.getId())
                .where("dc.year = ?", year)
                .limit(1).executeSingle();
    }

    public F1DriverStandings toF1DriverStandings(@Nullable Integer year) {
        F1DriverStandings f1DriverStandings = new F1DriverStandings();
        f1DriverStandings.position = this.position;
        f1DriverStandings.positionText = this.positionText;
        f1DriverStandings.wins = this.wins;
        f1DriverStandings.points = this.points;
        if (this.driver != null) {
            f1DriverStandings.driver = this.driver.toF1Driver();
        }
        Constructor constructor = getConstructor(year);
        if (constructor != null) {
            f1DriverStandings.constructor = constructor.toF1Constructor();
        }

        return f1DriverStandings;
    }

    @Override
    public String toString() {
        return "DriverStandings{" +
                "raceId=" + raceId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                ", driver=" + driver +
                '}';
    }
}
