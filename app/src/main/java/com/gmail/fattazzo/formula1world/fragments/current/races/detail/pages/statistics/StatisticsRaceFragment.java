package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$RaceStatPitStopFragment;

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
        switch (position) {
            case 1:
                $RaceStatPitStopFragment().race(race).container(R.id.race_statistics_container);
                break;
            default:
                race_statistics_container.removeAllViews();
                break;
        }
    }
}
