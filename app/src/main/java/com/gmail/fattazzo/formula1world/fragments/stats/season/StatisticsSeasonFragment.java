package com.gmail.fattazzo.formula1world.fragments.stats.season;

import android.widget.FrameLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment;

import org.androidannotations.annotations.EFragment;

import static com.dspot.declex.Action.$ConstructorsPodiumsStatsFragment;
import static com.dspot.declex.Action.$ConstructorsWinsStatsFragment;
import static com.dspot.declex.Action.$DriversPodiumsStatsFragment;
import static com.dspot.declex.Action.$DriversWinsStatsFragment;
import static com.dspot.declex.Action.$HomeFragment;
import static com.dspot.declex.Action.$SeasonComparisonStatsFragment;

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
                $SeasonComparisonStatsFragment().season(seasonEnd).container(containerResId);
                break;
            case 2:
                $DriversWinsStatsFragment().seasonStart(seasonEnd).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 3:
                $ConstructorsWinsStatsFragment().seasonStart(seasonEnd).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 4:
                $DriversPodiumsStatsFragment().seasonStart(seasonEnd).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 5:
                $ConstructorsPodiumsStatsFragment().seasonStart(seasonEnd).seasonEnd(seasonEnd).container(containerResId);
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

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
