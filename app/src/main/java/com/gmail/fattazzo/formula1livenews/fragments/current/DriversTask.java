package com.gmail.fattazzo.formula1livenews.fragments.current;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;
import com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings.DriverStandingsListAdapter;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

@EBean
public class DriversTask {

    @Bean
    CurrentSeasonDataService dataService;

    @RootContext
    HomeActivity activity;

    @ViewById(R.id.drivers_progressBar)
    ProgressBar progressBar;

    @Bean
    DriversListAdapter driversListAdapter;

    @ViewById(R.id.current_drivers_list_view)
    void setOneView(ListView listView){
        listView.setAdapter(driversListAdapter);
    }

    @UiThread
    void start() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Background
    public void loadDrivers() {

        List<Driver> result = null;
        try {
            start();

            result = dataService.loadDrivers();
        } finally {
            updateUI(result);
        }
    }

    @UiThread
    void updateUI(List<Driver> result) {
        try {
            driversListAdapter.clearItems();
            driversListAdapter.setDrivers(result);
            driversListAdapter.notifyDataSetChanged();
        } finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}