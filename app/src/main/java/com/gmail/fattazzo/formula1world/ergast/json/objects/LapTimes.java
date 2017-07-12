package com.gmail.fattazzo.formula1world.ergast.json.objects;

import org.apache.commons.collections4.ListUtils;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class LapTimes {
    private int season;
    private int round;
    private String url;
    private String raceName;
    private Circuit circuit;
    private String date;
    private String time;
    private List<Lap> laps;

    public LapTimes(int season, int round, String url, String raceName, Circuit circuit, String date, String time, List<Lap> laps) {
        this.season = season;
        this.round = round;
        this.url = url;
        this.raceName = raceName;
        this.circuit = circuit;
        this.date = date;
        this.time = time;
        this.laps = laps;
    }

    public List<Lap> getLaps() {
        return ListUtils.emptyIfNull(laps);
    }

    @Override
    public String toString() {
        return "LapTimes{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", laps=" + laps +
                '}';
    }
}
