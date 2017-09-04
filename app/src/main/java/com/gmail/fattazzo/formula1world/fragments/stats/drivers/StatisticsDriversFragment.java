package com.gmail.fattazzo.formula1world.fragments.stats.drivers;

import android.widget.FrameLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment;

import org.androidannotations.annotations.EFragment;

import static com.dspot.declex.Action.$DriversNationalityWinsStatsFragment;
import static com.dspot.declex.Action.$DriversPodiumsStatsFragment;
import static com.dspot.declex.Action.$DriversWinsStatsFragment;
import static com.dspot.declex.Action.$HomeFragment;

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
                $DriversWinsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 2:
                $DriversNationalityWinsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 3:
                $DriversPodiumsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
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

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
