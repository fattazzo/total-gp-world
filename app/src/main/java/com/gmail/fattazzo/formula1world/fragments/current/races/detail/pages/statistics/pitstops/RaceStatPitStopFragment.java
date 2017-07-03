package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TableLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopDriverComparator;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopDurationComparator;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopTimeComparator;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/07/17
 */
@EFragment(R.layout.fragment_race_stat_pitstops)
public class RaceStatPitStopFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @FragmentArg
    F1Race race;

    @Bean
    DataService dataService;

    @ViewById(R.id.stat_pitstops_table_layout)
    TableLayout tableLayout;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<F1PitStop> pitStops;

    private Comparator<F1PitStop> pitStopsComparator = new F1PitStopTimeComparator();

    @AfterViews
    void init() {
        tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

        swipeRefreshLayout.setOnRefreshListener(this);

        load();
    }

    @UiThread
    void startLoad() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Background
    void load() {
        startLoad();
        pitStops = dataService.loadPitStops(race);
        Collections.sort(pitStops, pitStopsComparator);
        updateUI();
    }

    @UiThread
    void updateUI() {
        try {
            if (tableLayout != null) {
                tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

                int rowNumber = 1;
                for (F1PitStop pit : pitStops) {
                    RaceStatPitStopItemView row = RaceStatPitStopItemView_.build(getActivity(), pit, rowNumber);
                    tableLayout.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowNumber++;
                }
            }
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        dataService.clearRacePitStopsCache(race);
        load();
    }

    @Click({R.id.order_by_driver_tv,R.id.order_by_driver_img})
    void order_by_driver() {
        pitStopsComparator = new F1PitStopDriverComparator();
        load();
    }

    @Click({R.id.order_by_time_tv,R.id.order_by_time_img})
    void order_by_time() {
        pitStopsComparator = new F1PitStopTimeComparator();
        load();
    }

    @Click({R.id.order_by_duration_tv,R.id.order_by_duration_img})
    void order_by_duration() {
        pitStopsComparator = new F1PitStopDurationComparator();
        load();
    }
}
