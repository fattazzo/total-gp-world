package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Race;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class RacePitStops {
    private int season;
    private int round;
    private String url;
    private String raceName;
    private Circuit circuit;
    private String date;
    private String time;
    private List<PitStop> pitStops;

    public List<F1PitStop> toF1PitStop(F1Race race, List<F1Driver> drivers) {
        List<F1PitStop> pits = new ArrayList<>();

        Collection<PitStop> pitStopsRace = CollectionUtils.emptyIfNull(pitStops);
        for (PitStop pitStop : pitStopsRace) {
            pits.add(pitStop.toF1PitStop(race, drivers));
        }

        return pits;
    }

    @Override
    public String toString() {
        return "RacePitStops{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pitStops=" + pitStops +
                '}';
    }
}
