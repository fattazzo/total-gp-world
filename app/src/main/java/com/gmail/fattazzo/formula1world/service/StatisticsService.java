package com.gmail.fattazzo.formula1world.service;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsDriversService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class StatisticsService {

    @Bean
    LocalDBStatsService localDBStatsService;

    @Bean
    LocalDBStatsDriversService localDBStatsDriversService;

    public
    @NonNull
    Date getLastRaceData() {
        return localDBStatsService.getLastRaceData();
    }

    public
    @NonNull
    List<StatsData> loadDriversWins(int seasonStart, int seasonEnd) {
        return localDBStatsDriversService.loadWins(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadDriversWinsNationality(int seasonStart, int seasonEnd) {
        return localDBStatsDriversService.loadWinsNationality(seasonStart,seasonEnd);
    }
}
