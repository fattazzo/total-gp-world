package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TableLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_race_results)
public class ResultsRaceFragment extends Fragment implements
        ITitledFragment, SwipeRefreshLayout.OnRefreshListener {

    @FragmentArg
    F1Race race;

    @ViewById(R.id.results_race_table_layout)
    TableLayout tableLayout;

    @ViewById
    SwipeRefreshLayout swipe_refresh_layout;

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

        swipe_refresh_layout.setOnRefreshListener(this);

        load();
    }

    @UiThread
    void startLoad() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout.setRefreshing(true);
        }
    }

    @Background
    void load() {
        startLoad();

        raceResults = dataService.loadRaceResult(race);
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
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        dataService.clearRaceResultsCache(race);
        load();
    }

    @Override
    public int getTitleResId() {
        return R.string.detail_race_results_tab_title;
    }
}