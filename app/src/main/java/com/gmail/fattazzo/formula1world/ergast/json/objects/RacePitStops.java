package com.gmail.fattazzo.formula1world.ergast.json.objects;

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

    public RacePitStops(int season, int round, String url, String raceName, Circuit circuit, String date, String time, List<PitStop> pitStops) {
        this.season = season;
        this.round = round;
        this.url = url;
        this.raceName = raceName;
        this.circuit = circuit;
        this.date = date;
        this.time = time;
        this.pitStops = pitStops;
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
