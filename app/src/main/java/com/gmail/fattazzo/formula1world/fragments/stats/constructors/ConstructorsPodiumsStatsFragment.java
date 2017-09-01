package com.gmail.fattazzo.formula1world.fragments.stats.constructors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsDataPodiumsListAdapter;

import org.androidannotations.annotations.EFragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
public class ConstructorsPodiumsStatsFragment extends AbstractStatsChartFragment {

    @NonNull
    @Override
    public List<StatsData> loadData() {
        return statisticsService.loadConstructorsPodiums(seasonStart, seasonEnd);
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

    @Override
    protected BaseAdapter createListAdapter(List<StatsData> data, DecimalFormat valueFormat) {
        return new StatsDataPodiumsListAdapter(getActivity(), data, valueFormat);
    }

    @Nullable
    @Override
    protected View getHeaderListView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.stats_four_values_row, null, false);
        ((TextView) header.findViewById(R.id.textLeft)).setText(getString(R.string.stats_pos));
        ((TextView) header.findViewById(R.id.textLabel)).setText(getString(R.string.constructors));
        ((TextView) header.findViewById(R.id.textValueOne)).setText(getString(R.string.stats_total));
        ((TextView) header.findViewById(R.id.textValueTwo)).setText(getString(R.string.stats_first));
        ((TextView) header.findViewById(R.id.textValueThree)).setText(getString(R.string.stats_second));
        ((TextView) header.findViewById(R.id.textValueFour)).setText(getString(R.string.stats_third));
        return header;
    }
}
