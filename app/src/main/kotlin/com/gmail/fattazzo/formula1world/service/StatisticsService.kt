package com.gmail.fattazzo.formula1world.service

import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.*
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class StatisticsService {

    @Bean
    lateinit internal var localDBStatsService: LocalDBStatsService

    @Bean
    lateinit internal var localDBStatsDriversService: LocalDBStatsDriversService

    @Bean
    lateinit internal var localDBStatsConstructorsService: LocalDBStatsConstructorsService

    @Bean
    lateinit internal var localDBStatsSeasonService: LocalDBStatsSeasonService

    @Bean
    lateinit internal var localDBStatsCircuitsService: LocalDBStatsCircuitsService

    val lastRaceData: Date
        get() = localDBStatsService!!.lastRaceData

    fun loadDriversWins(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsDriversService!!.loadWins(seasonStart, seasonEnd)
    }

    fun loadDriversWinsNationality(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsDriversService!!.loadWinsNationality(seasonStart, seasonEnd)
    }

    fun loadDriversPodiums(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsDriversService!!.loadPodiums(seasonStart, seasonEnd)
    }

    fun loadDriversNumbers(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsDriversService!!.loadNumbers(seasonStart, seasonEnd)
    }

    fun loadConstructorsWins(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsConstructorsService!!.loadWins(seasonStart, seasonEnd)
    }

    fun loadConstructorsWinsNationality(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsConstructorsService!!.loadWinsNationality(seasonStart, seasonEnd)
    }

    fun loadConstructorsPodiums(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsConstructorsService!!.loadPodiums(seasonStart, seasonEnd)
    }

    fun loadConstructorsNumbers(seasonStart: Int, seasonEnd: Int): List<StatsData> {
        return localDBStatsConstructorsService!!.loadNumbers(seasonStart, seasonEnd)
    }

    fun loadSeasonComparison(season: Int): StatsSeasonComparisonData {
        return localDBStatsSeasonService!!.loadComparison(season)
    }

    fun loadCircuitsCount(seasonStart: Int, seasonEnd: Int): List<StatsCircuitsData> {
        return localDBStatsCircuitsService!!.loadCount(seasonStart, seasonEnd)
    }

    fun loadCircuitsWinner(season: Int, type: StatsCircuitsData.Type): List<StatsCircuitsData> {
        return localDBStatsCircuitsService!!.loadWinner(season, type)
    }
}
