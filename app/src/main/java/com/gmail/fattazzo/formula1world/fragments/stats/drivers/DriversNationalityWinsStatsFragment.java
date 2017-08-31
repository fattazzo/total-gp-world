package com.gmail.fattazzo.formula1world.fragments.stats.drivers;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsDataImageLabelListAdapter;
import com.gmail.fattazzo.formula1world.utils.CountryNationality;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
public class DriversNationalityWinsStatsFragment extends AbstractStatsChartFragment {

    @Bean
    protected ImageUtils imageUtils;

    @Bean
    protected Utils utils;

    @NonNull
    @Override
    public List<StatsData> loadData() {
        return statisticsService.loadDriversWinsNationality(seasonStart, seasonEnd);
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
        return new StatsDataImageLabelListAdapter(getActivity(), data, valueFormat) {
            @Nullable
            @Override
            public Bitmap getImage(StatsData data) {
                CountryNationality country = utils.getCountryNationality(data.getLabel());
                if (country != null) {
                    return imageUtils.getFlagForCountryCode(country.getAlpha2Code());
                }
                return null;
            }
        };
    }
}
