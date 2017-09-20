package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.lapstime.RaceStatLapsTimeFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.RaceStatPitStopFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.RaceStatPositionsFragment_;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/07/17
 */
@EFragment(R.layout.fragment_race_statistics)
public class StatisticsRaceFragment extends Fragment implements ITitledFragment {

    @FragmentArg
    F1Race race;

    @ViewById
    Spinner statistics_spinner;

    @ViewById
    FrameLayout race_statistics_container;

    public static StatisticsRaceFragment newInstance(F1Race race) {
        StatisticsRaceFragment fragment = new StatisticsRaceFragment_();
        Bundle args = new Bundle();
        args.putSerializable("race", race);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTitleResId() {
        return R.string.detail_race_statistics_tab_title;
    }

    @ItemSelect
    public void statistics_spinner(boolean selected, int position) {
        FragmentManager fm = getFragmentManager();
        switch (position) {
            case 1:
                fm.beginTransaction().replace(R.id.race_statistics_container,RaceStatPitStopFragment_.builder().race(race).build()).commit();
                break;
            case 2:
                fm.beginTransaction().replace(R.id.race_statistics_container, RaceStatPositionsFragment_.builder().race(race).build()).commit();
                break;
            case 3:
                fm.beginTransaction().replace(R.id.race_statistics_container, RaceStatLapsTimeFragment_.builder().race(race).build()).commit();
                break;
            default:
                race_statistics_container.removeAllViews();
                break;
        }
    }
}
