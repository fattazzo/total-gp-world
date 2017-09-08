package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/09/17
 */
public class StatsSeasonComparisonData {

    private StatsSeasonData prevSeasonData;
    private StatsSeasonData seasonData;
    private StatsSeasonData nextSeasonData;

    StatsSeasonComparisonData(StatsSeasonData prevSeasonData, StatsSeasonData seasonData, StatsSeasonData nextSeasonData) {
        this.prevSeasonData = prevSeasonData;
        this.seasonData = seasonData;
        this.nextSeasonData = nextSeasonData;
    }

    public StatsSeasonData getSeasonData() {
        return seasonData;
    }

    public StatsSeasonData getPrevSeasonData() {
        return prevSeasonData;
    }

    public StatsSeasonData getNextSeasonData() {
        return nextSeasonData;
    }
}
