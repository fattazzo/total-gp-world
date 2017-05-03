package com.gmail.fattazzo.formula1livenews.ergast.objects;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
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

    public RaceResults(int season, int round, String url, String raceName, Circuit circuit, String date, String time, List<RaceResult> results) {
        this.season = season;
        this.round = round;
        this.url = url;
        this.raceName = raceName;
        this.circuit = circuit;
        this.date = date;
        this.time = time;
        this.results = results;
    }

    public int getSeason() {
        return season;
    }

    public int getRound() {
        return round;
    }

    public String getUrl() {
        return url;
    }

    public String getRaceName() {
        return raceName;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<RaceResult> getResults() {
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
