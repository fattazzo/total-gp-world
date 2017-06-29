package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.domain.F1Race;
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

@EFragment(R.layout.fragment_race_qualifications)
public class QualificationsRaceFragment extends Fragment implements ITitledFragment {

    @FragmentArg
    F1Race race;

    @ViewById
    TableLayout table_Layout;

    @ViewById
    FloatingActionButton refreshFab;

    @ViewById
    ProgressBar progressBar;

    @Bean
    DataService dataService;

    private List<F1Qualification> raceQualifications;

    public static QualificationsRaceFragment newInstance(F1Race race) {
        QualificationsRaceFragment resultsFragment = new QualificationsRaceFragment_();
        Bundle args = new Bundle();
        args.putSerializable("race", race);
        resultsFragment.setArguments(args);
        return resultsFragment;
    }

    @AfterViews
    void init() {
        table_Layout.removeViews(1, table_Layout.getChildCount() - 1);

        load();
    }

    @Click
    void refreshFab() {
        raceQualifications = null;
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

        if (raceQualifications == null) {
            raceQualifications = dataService.loadQualification(race);
        }
        updateUI();
    }

    @UiThread
    void updateUI() {
        try {
            if (table_Layout != null) {
                table_Layout.removeViews(1, table_Layout.getChildCount() - 1);

                int rowNumber = 1;
                for (F1Qualification result : raceQualifications) {
                    QualificationsRaceItemView row = QualificationsRaceItemView_.build(getActivity(), result, rowNumber);
                    table_Layout.addView(row, new TableLayout.LayoutParams(
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
        return R.string.detail_race_qualifications_tab_title;
    }
}