package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1AverageSpeed;
import com.gmail.fattazzo.formula1world.domain.F1FastestLap;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.domain.F1Time;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "results")
public class Result extends Model {

    @Column(name = "raceId")
    Race race;

    @Column(name = "driverId")
    Driver driver;

    @Column(name = "constructorId")
    Constructor constructor;

    @Column
    int number;

    @Column
    int grid;

    @Column
    int position;

    @Column
    String positionText;

    @Column
    int positionOrder;

    @Column
    float points;

    @Column
    int laps;

    @Column
    String time;

    @Column
    int milliseconds;

    @Column
    int fastestLap;

    @Column
    int rank;

    @Column
    String fastestLapTime;

    @Column
    String fastestLapSpeed;

    @Column(name = "statusId")
    Status status;

    public F1Result toF1Result() {
        F1Result f1Result = new F1Result();
        if(this.race != null) {
            f1Result.race = this.race.toF1Race();
        }
        if(this.driver != null) {
            f1Result.driver = this.driver.toF1Driver();
        }
        if(this.constructor != null) {
            f1Result.constructor = this.constructor.toF1Constructor();
        }
        f1Result.number = this.number;
        f1Result.grid = this.grid;
        f1Result.position = this.position;
        f1Result.positionText = this.positionText;
        f1Result.positionOrder = this.positionOrder;
        f1Result.points = this.points;
        f1Result.laps = this.laps;
        f1Result.time = new F1Time(this.time);

        Double speed = StringUtils.isBlank(fastestLapSpeed) ? null : Double.valueOf(fastestLapSpeed);
        f1Result.fastestLap = new F1FastestLap(rank,fastestLap,new F1Time(fastestLapTime),new F1AverageSpeed("",speed));
        if(this.status != null) {
            f1Result.status = this.status.status;
        }

        return f1Result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", grid=" + grid +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", positionOrder=" + positionOrder +
                ", points=" + points +
                ", laps=" + laps +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                ", fastestLap=" + fastestLap +
                ", rank=" + rank +
                ", fastestLapTime='" + fastestLapTime + '\'' +
                ", fastestLapSpeed='" + fastestLapSpeed + '\'' +
                ", status=" + status +
                '}';
    }
}
