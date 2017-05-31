package com.gmail.fattazzo.formula1livenews.fragments.current.constructors.detail.pages.progress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Constructor;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResult;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResults;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment_;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress.RaceResultsItemView;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress.RaceResultsItemView_;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EFragment(R.layout.progress_driver_fragment)
public class ProgressConstructorFragment extends Fragment {

    @FragmentArg
    Constructor constructor;

    @ViewById(R.id.race_result_progress_main_layout)
    LinearLayout mainLayout;

    @ViewById
    FloatingActionButton refreshFab;

    @ViewById
    ProgressBar progressBar;

    @Bean
    CurrentSeasonDataService dataService;

    private List<RaceResults> raceResults;

    public static ProgressConstructorFragment newInstance(Constructor constructor) {
        ProgressConstructorFragment progressFragment = new ProgressConstructorFragment_();
        Bundle args = new Bundle();
        args.putSerializable("constructor", constructor);
        progressFragment.setArguments(args);
        return progressFragment;
    }

    @AfterViews
    void init() {
        mainLayout.removeAllViews();

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
            raceResults = dataService.loadConstructorRacesResult(constructor.getConstructorId());
        }
        updateUI();
    }

    @UiThread
    void updateUI() {
        try {
            if(mainLayout != null) {
                mainLayout.removeAllViews();

                // 1 table for each driver
                MultiValuedMap<String,RaceResult> resultsMap = new ArrayListValuedHashMap<>();

                for (RaceResults results : raceResults) {
                    for (RaceResult result: results.getResults()) {
                        String driverName = result.getDriver().getGivenName() + " " + result.getDriver().getFamilyName();
                        resultsMap.put(driverName,result);
                    }
                }

                for (String driverName : resultsMap.keySet()) {

                    TableLayout tableLayout = (TableLayout) getActivity().getLayoutInflater().inflate(R.layout.race_result_progress_table_layout,null).findViewById(R.id.progress_driver_table_layout);

                    TextView driverTextView = new TextView(getContext());
                    driverTextView.setText(driverName);
                    tableLayout.addView(driverTextView,0, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    int rowNumber = 1;
                    for (RaceResult result : resultsMap.get(driverName)) {
                        RaceResultsItemView row = RaceResultsItemView_.build(getActivity(), result, rowNumber);
                        tableLayout.addView(row, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        rowNumber++;
                    }

                    mainLayout.addView(tableLayout);
                }

                //tableLayout.getChildAt(0).setVisibility(View.INVISIBLE);
            }
        } finally {
            if(progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}