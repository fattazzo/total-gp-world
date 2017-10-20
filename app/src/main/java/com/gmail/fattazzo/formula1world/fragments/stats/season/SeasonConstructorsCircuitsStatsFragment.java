package com.gmail.fattazzo.formula1world.fragments.stats.season;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsCircuitsFragment;

import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/10/17
 */
@EFragment(R.layout.fragment_stats_circuits)
public class SeasonConstructorsCircuitsStatsFragment extends AbstractStatsCircuitsFragment {

    @NonNull
    @Override
    protected List<StatsCircuitsData> loadData() {
        return statisticsService.loadCircuitsWinner(seasonStart, StatsCircuitsData.Type.CONSTRUCTORS_WINNER);
    }
}
