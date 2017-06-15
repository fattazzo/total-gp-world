package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.results_race_fragment)
public class ResultsRaceFragment extends Fragment implements ITitledFragment {

    @FragmentArg
    F1Race race;

    @ViewById(R.id.results_race_table_layout)
    TableLayout tableLayout;

    @ViewById
    FloatingActionButton refreshFab;

    @ViewById
    ProgressBar progressBar;

    @Bean
    DataService dataService;

    private List<F1Result> raceResults;

    public static ResultsRaceFragment newInstance(F1Race race) {
        ResultsRaceFragment resultsFragment = new ResultsRaceFragment_();
        Bundle args = new Bundle();
        args.putSerializable("race", race);
        resultsFragment.setArguments(args);
        return resultsFragment;
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
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Background
    void load() {
        startLoad();

        if (raceResults == null) {
            raceResults = dataService.loadRaceResult(race);
        }
        updateUI();
    }

    @UiThread
    void updateUI() {
        try {
            if (tableLayout != null) {
                tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

                int rowNumber = 1;
                for (F1Result result : raceResults) {
                    ResultsRaceItemView row = ResultsRaceItemView_.build(getActivity(), result, rowNumber);
                    tableLayout.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowNumber++;
                }
            }
        } finally {
            if (progressBar != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getTitleResId() {
        return R.string.detail_race_results_tab_title;
    }
}