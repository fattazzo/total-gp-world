package com.gmail.fattazzo.formula1world.fragments.stats.constructors;

import android.widget.FrameLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

import org.androidannotations.annotations.EFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsConstructorsFragment extends AbstractStatisticsFragment {

    public static final String TAG = StatisticsConstructorsFragment.class.getSimpleName();

    @Override
    public boolean isMultiSeasons() {
        return true;
    }

    @Override
    public int getStatsListResId() {
        return R.array.statistics_constructors_entry;
    }

    @Override
    public void showStat(int index, Integer seasonStart, Integer seasonEnd, int containerResId, FrameLayout container) {
        switch (index) {
            case 1:
                FragmentUtils.replace(getActivity(), ConstructorsWinsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 2:
                FragmentUtils.replace(getActivity(), ConstructorsNationalityWinsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 3:
                FragmentUtils.replace(getActivity(), ConstructorsPodiumsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 4:
                FragmentUtils.replace(getActivity(), ConstructorsNumbersStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            default:
                container.removeAllViews();
                break;
        }
    }
}
