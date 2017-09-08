package com.gmail.fattazzo.formula1world.fragments.stats.season;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsSeasonComparisonData;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsSeasonData;
import com.gmail.fattazzo.formula1world.service.StatisticsService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/09/17
 */
@EFragment(R.layout.fragment_stats_season_comparison)
public class SeasonComparisonStatsFragment extends Fragment {

    @Bean
    StatisticsService statisticsService;

    @FragmentArg
    int season;

    @ViewById
    TextView prevSeasonTV, seasonTV, nextSeasonTV;

    @ViewById
    ProgressBar prevRacesCompletedBar, racesCompletedBar, nextRacesCompletedBar;

    @ViewById
    LinearLayout seasonComparisonStatsContainer;

    @AfterViews
    void init() {
        StatsSeasonComparisonData stats = statisticsService.loadSeasonComparison(season);

        // Season ------------------------------------------------------
        prevSeasonTV.setText(String.valueOf(stats.getPrevSeasonData().getSeason()));
        seasonTV.setText(String.valueOf(stats.getSeasonData().getSeason()));
        nextSeasonTV.setText(String.valueOf(stats.getNextSeasonData().getSeason()));


        // Races completed ---------------------------------------------
        prevRacesCompletedBar.setMax(stats.getPrevSeasonData().getRounds());
        prevRacesCompletedBar.setProgress(stats.getPrevSeasonData().getRacesCompleted());
        racesCompletedBar.setMax(stats.getSeasonData().getRounds());
        racesCompletedBar.setProgress(stats.getSeasonData().getRacesCompleted());
        nextRacesCompletedBar.setMax(stats.getNextSeasonData().getRounds());
        nextRacesCompletedBar.setProgress(stats.getNextSeasonData().getRacesCompleted());

        seasonComparisonStatsContainer.removeAllViews();

        StatsSeasonData prevSeason = stats.getPrevSeasonData();
        StatsSeasonData season = stats.getSeasonData();
        StatsSeasonData nextSeason = stats.getNextSeasonData();
        // Rounds ------------------------------------------------------
        SeasonComparisonRowView roundsRow = SeasonComparisonRowView_.build(getContext(), R.string.rounds, prevSeason.getRounds(), season.getRounds(), nextSeason.getRounds());
        seasonComparisonStatsContainer.addView(roundsRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Drivers -----------------------------------------------------
        SeasonComparisonRowView driversRow = SeasonComparisonRowView_.build(getContext(), R.string.drivers, prevSeason.getDrivers(), season.getDrivers(), nextSeason.getDrivers());
        seasonComparisonStatsContainer.addView(driversRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Constructors -----------------------------------------------------
        SeasonComparisonRowView constructorsRow = SeasonComparisonRowView_.build(getContext(), R.string.constructors, prevSeason.getConstructors(), season.getConstructors(), nextSeason.getConstructors());
        seasonComparisonStatsContainer.addView(constructorsRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // WinningDrivers -----------------------------------------------------
        SeasonComparisonRowView winningDriversRow = SeasonComparisonRowView_.build(getContext(), R.string.winningDrivers, prevSeason.getWinningDrivers(), season.getWinningDrivers(), nextSeason.getWinningDrivers());
        seasonComparisonStatsContainer.addView(winningDriversRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // WinningConstructors -----------------------------------------------------
        SeasonComparisonRowView winningConstructorsRow = SeasonComparisonRowView_.build(getContext(), R.string.winningConstructors, prevSeason.getWinningConstructos(), season.getWinningConstructos(), nextSeason.getWinningConstructos());
        seasonComparisonStatsContainer.addView(winningConstructorsRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // PodiumDrivers -----------------------------------------------------
        SeasonComparisonRowView podiumDriversRow = SeasonComparisonRowView_.build(getContext(), R.string.podiumDrivers, prevSeason.getPodiumDrivers(), season.getPodiumDrivers(), nextSeason.getPodiumDrivers());
        seasonComparisonStatsContainer.addView(podiumDriversRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // PodiumConstructors -----------------------------------------------------
        SeasonComparisonRowView podiumConstructorsRow = SeasonComparisonRowView_.build(getContext(), R.string.podiumConstructors, prevSeason.getPodiumConstructors(), season.getPodiumConstructors(), nextSeason.getPodiumConstructors());
        seasonComparisonStatsContainer.addView(podiumConstructorsRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // WinningDriversPoints -----------------------------------------------------
        SeasonComparisonRowView winningDriversPointsRow = SeasonComparisonRowView_.build(getContext(), R.string.winningDriverPoints, prevSeason.getWinningDriverPoints(), season.getWinningDriverPoints(), nextSeason.getWinningDriverPoints());
        seasonComparisonStatsContainer.addView(winningDriversPointsRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // PointsAssigned -----------------------------------------------------
        SeasonComparisonRowView pointsAssignedRow = SeasonComparisonRowView_.build(getContext(), R.string.pointsAssigned, prevSeason.getPointsAssigned(), season.getPointsAssigned(), nextSeason.getPointsAssigned());
        seasonComparisonStatsContainer.addView(pointsAssignedRow, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
