package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Circuit;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class RaceResults {

    private int season;
    private int round;
    private String url;
    private String raceName;
    private Circuit circuit;
    private String date;
    private String time;
    private List<RaceResult> results;

    public List<F1Result> toF1Result() {
        List<F1Result> results = new ArrayList<>();

        F1Circuit f1Circuit = this.circuit != null ? this.circuit.toF1Circuit() : null;
        F1Race f1race = new F1Race(this.season, this.round, this.url, this.raceName, f1Circuit, this.date, this.time);

        for (RaceResult result : CollectionUtils.emptyIfNull(this.results)) {
            F1Result f1Result = new F1Result();
            f1Result.race = f1race;
            f1Result.number = result.getNumber();
            f1Result.grid = result.getGrid();
            f1Result.position = result.getPosition();
            f1Result.positionText = result.getPositionText();
            f1Result.positionOrder = result.getPosition();
            f1Result.points = result.getPoints();
            f1Result.status = result.getStatus();
            f1Result.laps = result.getLaps();
            if (result.getConstructor() != null) {
                f1Result.constructor = result.getConstructor().toF1Constructor();
            }
            if (result.getDriver() != null) {
                f1Result.driver = result.getDriver().toF1Driver();
            }
            if (result.getFastestLap() != null) {
                f1Result.fastestLap = result.getFastestLap().toF1FastestLap();
            }
            if (result.getTime() != null) {
                f1Result.time = result.getTime().toF1Time();
            }
            results.add(f1Result);
        }

        return results;
    }

    @Override
    public String toString() {
        return "RaceResults{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", results=" + results +
                '}';
    }
}
