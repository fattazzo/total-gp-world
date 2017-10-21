package com.gmail.fattazzo.formula1world.fragments.stats.season;

import android.widget.FrameLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.ConstructorsPodiumsStatsFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.ConstructorsWinsStatsFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.DriversPodiumsStatsFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.DriversWinsStatsFragment_;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

import org.androidannotations.annotations.EFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsSeasonFragment extends AbstractStatisticsFragment {

    public static final String TAG = StatisticsSeasonFragment.class.getSimpleName();

    @Override
    public void showStat(int index, Integer seasonStart, Integer seasonEnd, int containerResId, FrameLayout container) {
        switch (index) {
            case 1:
                FragmentUtils.replace(getActivity(), SeasonComparisonStatsFragment_.builder().season(seasonEnd).build(), containerResId);
                break;
            case 2:
                FragmentUtils.replace(getActivity(), DriversWinsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 3:
                FragmentUtils.replace(getActivity(), ConstructorsWinsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 4:
                FragmentUtils.replace(getActivity(), DriversPodiumsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 5:
                FragmentUtils.replace(getActivity(), ConstructorsPodiumsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 6:
                FragmentUtils.replace(getActivity(), SeasonDriversCircuitsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            case 7:
                FragmentUtils.replace(getActivity(), SeasonConstructorsCircuitsStatsFragment_.builder().seasonStart(seasonEnd).seasonEnd(seasonEnd).build(), containerResId);
                break;
            default:
                container.removeAllViews();
                break;
        }
    }

    @Override
    public boolean isMultiSeasons() {
        return false;
    }

    @Override
    public int getStatsListResId() {
        return R.array.statistics_season_entry;
    }
}
