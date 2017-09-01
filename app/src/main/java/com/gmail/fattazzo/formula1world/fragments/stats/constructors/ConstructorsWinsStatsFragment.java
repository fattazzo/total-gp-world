package com.gmail.fattazzo.formula1world.fragments.stats.constructors;

import android.support.annotation.NonNull;

import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment;

import org.androidannotations.annotations.EFragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
public class ConstructorsWinsStatsFragment extends AbstractStatsChartFragment {

    @NonNull
    @Override
    public List<StatsData> loadData() {
        return statisticsService.loadConstructorsWins(seasonStart, seasonEnd);
    }

    @NonNull
    @Override
    protected IValueFormatter getChartValueFormatter() {
        return new DefaultValueFormatter(0);
    }

    @NonNull
    @Override
    protected DecimalFormat getListValueFormat() {
        return new DecimalFormat("0");
    }
}
