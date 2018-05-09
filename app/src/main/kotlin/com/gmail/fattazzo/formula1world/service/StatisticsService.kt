/*
 * Project: total-gp-world
 * File: StatisticsService.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
