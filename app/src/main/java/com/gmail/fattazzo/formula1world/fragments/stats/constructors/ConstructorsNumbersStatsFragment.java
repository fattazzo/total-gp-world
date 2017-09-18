package com.gmail.fattazzo.formula1world.fragments.stats.constructors;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsBarChartFragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 08/09/17
 */
@EFragment(R.layout.fragment_stats_barchart)
public class ConstructorsNumbersStatsFragment extends AbstractStatsBarChartFragment {

    @NonNull
    @Override
    public List<StatsData> loadData() {
        return statisticsService.loadConstructorsNumbers(seasonStart, seasonEnd);
    }
}
