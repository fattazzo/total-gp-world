package com.gmail.fattazzo.formula1world.fragments.stats.season

import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.service.StatisticsService
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 07/09/17
 */
@EFragment(R.layout.fragment_stats_season_comparison)
open class SeasonComparisonStatsFragment : Fragment() {

    @Bean
    lateinit internal var statisticsService: StatisticsService

    @FragmentArg
    @JvmField protected var season: Int? = 0

    @ViewById
    lateinit internal var prevSeasonTV: TextView
    @ViewById
    lateinit internal var seasonTV: TextView
    @ViewById
    lateinit internal var nextSeasonTV: TextView

    @ViewById
    lateinit internal var prevRacesCompletedBar: ProgressBar
    @ViewById
    lateinit internal var racesCompletedBar: ProgressBar
    @ViewById
    lateinit internal var nextRacesCompletedBar: ProgressBar

    @ViewById
    lateinit internal var seasonComparisonStatsContainer: LinearLayout

    @AfterViews
    internal fun init() {
        val stats = statisticsService!!.loadSeasonComparison(this!!.season!!)

        // Season ------------------------------------------------------
        prevSeasonTV!!.text = stats.prevSeasonData.season.toString()
        seasonTV!!.text = stats.seasonData.season.toString()
        nextSeasonTV!!.text = stats.nextSeasonData.season.toString()


        // Races completed ---------------------------------------------
        prevRacesCompletedBar!!.max = stats.prevSeasonData.rounds
        prevRacesCompletedBar!!.progress = stats.prevSeasonData.racesCompleted
        racesCompletedBar!!.max = stats.seasonData.rounds
        racesCompletedBar!!.progress = stats.seasonData.racesCompleted
        nextRacesCompletedBar!!.max = stats.nextSeasonData.rounds
        nextRacesCompletedBar!!.progress = stats.nextSeasonData.racesCompleted

        seasonComparisonStatsContainer!!.removeAllViews()

        val prevSeason = stats.prevSeasonData
        val season = stats.seasonData
        val nextSeason = stats.nextSeasonData
        // Rounds ------------------------------------------------------
        val roundsRow = SeasonComparisonRowView_.build(context, R.string.rounds, prevSeason.rounds, season.rounds, nextSeason.rounds)
        seasonComparisonStatsContainer!!.addView(roundsRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // Drivers -----------------------------------------------------
        val driversRow = SeasonComparisonRowView_.build(context, R.string.drivers, prevSeason.drivers, season.drivers, nextSeason.drivers)
        seasonComparisonStatsContainer!!.addView(driversRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // Constructors -----------------------------------------------------
        val constructorsRow = SeasonComparisonRowView_.build(context, R.string.constructors, prevSeason.constructors, season.constructors, nextSeason.constructors)
        seasonComparisonStatsContainer!!.addView(constructorsRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // WinningDrivers -----------------------------------------------------
        val winningDriversRow = SeasonComparisonRowView_.build(context, R.string.winningDrivers, prevSeason.winningDrivers, season.winningDrivers, nextSeason.winningDrivers)
        seasonComparisonStatsContainer!!.addView(winningDriversRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // WinningConstructors -----------------------------------------------------
        val winningConstructorsRow = SeasonComparisonRowView_.build(context, R.string.winningConstructors, prevSeason.winningConstructos, season.winningConstructos, nextSeason.winningConstructos)
        seasonComparisonStatsContainer!!.addView(winningConstructorsRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // PodiumDrivers -----------------------------------------------------
        val podiumDriversRow = SeasonComparisonRowView_.build(context, R.string.podiumDrivers, prevSeason.podiumDrivers, season.podiumDrivers, nextSeason.podiumDrivers)
        seasonComparisonStatsContainer!!.addView(podiumDriversRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // PodiumConstructors -----------------------------------------------------
        val podiumConstructorsRow = SeasonComparisonRowView_.build(context, R.string.podiumConstructors, prevSeason.podiumConstructors, season.podiumConstructors, nextSeason.podiumConstructors)
        seasonComparisonStatsContainer!!.addView(podiumConstructorsRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // WinningDriversPoints -----------------------------------------------------
        val winningDriversPointsRow = SeasonComparisonRowView_.build(context, R.string.winningDriverPoints, prevSeason.winningDriverPoints, season.winningDriverPoints, nextSeason.winningDriverPoints)
        seasonComparisonStatsContainer!!.addView(winningDriversPointsRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        // PointsAssigned -----------------------------------------------------
        val pointsAssignedRow = SeasonComparisonRowView_.build(context, R.string.pointsAssigned, prevSeason.pointsAssigned, season.pointsAssigned, nextSeason.pointsAssigned)
        seasonComparisonStatsContainer!!.addView(pointsAssignedRow, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }
}
