package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResults;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.progress_driver_fragment)
public class ProgressDriverFragment extends Fragment {

    @FragmentArg
    Driver driver;

    @ViewById(R.id.progress_driver_table_layout)
    TableLayout tableLayout;

    @ViewById
    FloatingActionButton refreshFab;

    @ViewById
    ProgressBar progressBar;

    @Bean
    CurrentSeasonDataService dataService;

    private List<RaceResults> raceResults;

    public static ProgressDriverFragment newInstance(Driver driver) {
        ProgressDriverFragment progressFragment = new ProgressDriverFragment_();
        Bundle args = new Bundle();
        args.putSerializable("driver", driver);
        progressFragment.setArguments(args);
        return progressFragment;
    }

    @AfterViews
    void init() {
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

        load();
    }

    @Click
    void refreshFab() {
        raceResults = null;
        load();
    }

    @UiThread
    void startLoad() {
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Background
    void load() {
        startLoad();

        if (raceResults == null) {
            raceResults = dataService.loadDriverRacesResult(driver.getDriverId());
        }
        updateUI();
    }

    @UiThread
    void updateUI() {
        try {
            if(tableLayout != null) {
                tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

                int rowNumber = 1;
                for (RaceResults results : raceResults) {
                    RaceResultsItemView row = RaceResultsItemView_.build(getActivity(), results.getResults().get(0), rowNumber);
                    tableLayout.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowNumber++;
                }
            }
        } finally {
            if(progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}