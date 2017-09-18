package com.gmail.fattazzo.formula1world.fragments.stats.drivers;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsBarChartFragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_barchart)
public class DriversNumbersStatsFragment extends AbstractStatsBarChartFragment {

    @NonNull
    @Override
    public List<StatsData> loadData() {
        return statisticsService.loadDriversNumbers(seasonStart, seasonEnd);
    }
}
