package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.ranking;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResults;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;
import com.gmail.fattazzo.formula1livenews.utils.ImageUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/04/17
 */
@EFragment(R.layout.ranking_driver_fragment)
public class RankingDriverFragment extends Fragment {

    @FragmentArg
    Driver driver;

    @Bean
    ImageUtils imageUtils;

    @Bean
    ChartManager chartManager;

    @Bean
    CurrentSeasonDataService dataService;

    @ViewById(R.id.ranking_driver_position_chart)
    LineChart positionChart;

    @ViewById(R.id.ranking_driver_points_chart)
    LineChart pointsChart;

    @ViewById
    FloatingActionButton refreshFab;

    @ViewById
    ProgressBar progressBar;

    private List<RaceResults> raceResults, leaderRaceResults;

    public static RankingDriverFragment newInstance(Driver driver) {
        RankingDriverFragment rankingDriverFragment = new RankingDriverFragment_();
        Bundle args = new Bundle();
        args.putSerializable("driver", driver);
        rankingDriverFragment.setArguments(args);
        return rankingDriverFragment;
    }

    @AfterViews
    void init() {
        chartManager.configureChart(positionChart);
        chartManager.configureChart(pointsChart);

        loadData();
    }

    @Click
    void refreshFab() {
        raceResults = null;
        leaderRaceResults = null;
        loadData();
    }

    @UiThread
    void startLoad() {
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Background
    void loadData() {
        startLoad();

        if (raceResults == null) {
            raceResults = dataService.loadDriverRacesResult(driver.getDriverId());
        }

        if (leaderRaceResults == null) {
            leaderRaceResults = new ArrayList<>();

            DriverStandings driverLeader = dataService.loadDriverLeader();
            if (driverLeader != null) {
                leaderRaceResults = dataService.loadDriverRacesResult(driverLeader.getDriver().getDriverId());
            }
        }

        loadChartData();
    }

    @UiThread
    void loadChartData() {
        try {
            if(positionChart != null && pointsChart != null) {
                chartManager.loadPositionsChartData(positionChart, raceResults);
                chartManager.loadPointsChartData(pointsChart, raceResults, leaderRaceResults);
                startChatsAnimation();
            }
        } finally {
            if(progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && positionChart != null) {
            startChatsAnimation();
        }
    }

    private void startChatsAnimation() {
        positionChart.animateY(1000, Easing.EasingOption.EaseInOutSine);
        pointsChart.animateY(1000, Easing.EasingOption.EaseInOutSine);
    }

}
