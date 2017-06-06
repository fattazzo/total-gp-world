package com.gmail.fattazzo.formula1world.ergast.json.objects;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Season {
    private int season;
    private String url;

    public Season(String season, String url) {
        this.season = Integer.valueOf(season);
        this.url = url;
    }

    @Override
    public String toString() {
        return "Season{" +
                "season=" + season +
                ", url='" + url + '\'' +
                '}';
    }
}
