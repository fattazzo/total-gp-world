package com.gmail.fattazzo.formula1world.fragments.stats.constructors;

import android.widget.FrameLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatisticsFragment;

import org.androidannotations.annotations.EFragment;

import static com.dspot.declex.Action.$ConstructorsNationalityWinsStatsFragment;
import static com.dspot.declex.Action.$ConstructorsPodiumsStatsFragment;
import static com.dspot.declex.Action.$ConstructorsWinsStatsFragment;
import static com.dspot.declex.Action.$HomeFragment;

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
                $ConstructorsWinsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 2:
                $ConstructorsNationalityWinsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
                break;
            case 3:
                $ConstructorsPodiumsStatsFragment().seasonStart(seasonStart).seasonEnd(seasonEnd).container(containerResId);
                break;
            default:
                container.removeAllViews();
                break;
        }
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
