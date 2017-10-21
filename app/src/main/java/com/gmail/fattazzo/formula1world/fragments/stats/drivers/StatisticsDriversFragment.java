package com.gmail.fattazzo.formula1world.fragments.stats.drivers;

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
public class StatisticsDriversFragment extends AbstractStatisticsFragment {

    public static final String TAG = StatisticsDriversFragment.class.getSimpleName();

    @Override
    public void showStat(int index, Integer seasonStart, Integer seasonEnd, int containerResId, FrameLayout container) {
        switch (index) {
            case 1:
                FragmentUtils.replace(getActivity(), DriversWinsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 2:
                FragmentUtils.replace(getActivity(), DriversNationalityWinsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 3:
                FragmentUtils.replace(getActivity(), DriversPodiumsStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 4:
                FragmentUtils.replace(getActivity(), DriversNumbersStatsFragment_.builder().seasonStart(seasonStart).seasonEnd(seasonEnd).build(), containerResId);
                break;
            default:
                container.removeAllViews();
                break;
        }
    }

    @Override
    public boolean isMultiSeasons() {
        return true;
    }

    @Override
    public int getStatsListResId() {
        return R.array.statistics_drivers_entry;
    }
}
