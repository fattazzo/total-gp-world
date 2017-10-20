package com.gmail.fattazzo.formula1world.service;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsCircuitsService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsConstructorsService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsDriversService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsSeasonService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.LocalDBStatsService;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsSeasonComparisonData;

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

    @Bean
    LocalDBStatsConstructorsService localDBStatsConstructorsService;

    @Bean
    LocalDBStatsSeasonService localDBStatsSeasonService;

    @Bean
    LocalDBStatsCircuitsService localDBStatsCircuitsService;

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
        return localDBStatsDriversService.loadWinsNationality(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadDriversPodiums(int seasonStart, int seasonEnd) {
        return localDBStatsDriversService.loadPodiums(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadDriversNumbers(int seasonStart, int seasonEnd) {
        return localDBStatsDriversService.loadNumbers(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadConstructorsWins(int seasonStart, int seasonEnd) {
        return localDBStatsConstructorsService.loadWins(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadConstructorsWinsNationality(int seasonStart, int seasonEnd) {
        return localDBStatsConstructorsService.loadWinsNationality(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadConstructorsPodiums(int seasonStart, int seasonEnd) {
        return localDBStatsConstructorsService.loadPodiums(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsData> loadConstructorsNumbers(int seasonStart, int seasonEnd) {
        return localDBStatsConstructorsService.loadNumbers(seasonStart, seasonEnd);
    }

    public
    @NonNull
    StatsSeasonComparisonData loadSeasonComparison(int season) {
        return localDBStatsSeasonService.loadComparison(season);
    }

    public
    @NonNull
    List<StatsCircuitsData> loadCircuitsCount(int seasonStart, int seasonEnd) {
        return localDBStatsCircuitsService.loadCount(seasonStart, seasonEnd);
    }

    public
    @NonNull
    List<StatsCircuitsData> loadCircuitsWinner(int season, StatsCircuitsData.Type type) {
        return localDBStatsCircuitsService.loadWinner(season, type);
    }
}
