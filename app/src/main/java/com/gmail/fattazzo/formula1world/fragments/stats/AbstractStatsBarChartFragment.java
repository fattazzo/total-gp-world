package com.gmail.fattazzo.formula1world.fragments.stats;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.service.StatisticsService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.ThemeUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_barchart)
public abstract class AbstractStatsBarChartFragment extends BaseFragment {

    @FragmentArg
    public int seasonStart;

    @FragmentArg
    public int seasonEnd;

    @Bean
    protected StatisticsService statisticsService;

    @Bean
    protected ApplicationPreferenceManager preferenceManager;

    @Bean
    protected ThemeUtils themeUtils;

    @ViewById
    protected HorizontalBarChart chart;

    @ViewById
    protected TextView title;

    private Date lastData;

    @AfterViews
    protected void initViews() {
        configureChart();

        bindData();

        title.setText(createTitle());
    }

    @AfterInject
    protected void afterInjenct() {
        lastData = statisticsService.getLastRaceData();
    }

    private SpannableString createTitle() {
        DateFormat outFormat = android.text.format.DateFormat.getDateFormat(getContext());
        String seasonsTxt;
        if (seasonStart == seasonEnd) {
            seasonsTxt = getString(R.string.season) + " " + seasonStart;
        } else {
            seasonsTxt = getString(R.string.seasons) + " " + seasonStart + " - " + seasonEnd;
        }
        SpannableString text = new SpannableString(seasonsTxt);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastData);
        if (seasonEnd >= calendar.get(Calendar.YEAR)) {
            text = new SpannableString(seasonsTxt + " *" + getString(R.string.until) + " " + outFormat.format(lastData));
        }
        text.setSpan(new RelativeSizeSpan(1.0f), 0, seasonsTxt.length(), 0);
        text.setSpan(new RelativeSizeSpan(0.65f), seasonsTxt.length(), text.length(), 0);
        return text;
    }

    private void configureChart() {
        if (chart != null) {

            chart.setDrawBarShadow(false);

            chart.setDrawValueAboveBar(true);

            chart.setMaxVisibleValueCount(30);

            chart.getDescription().setEnabled(false);

            chart.setVisibleXRangeMaximum(30);

            chart.setPinchZoom(false);

            chart.setDrawGridBackground(false);

            XAxis xl = chart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(0f);
            xl.setTextColor(themeUtils.getThemeTextColor(getContext()));
            xl.setTextSize(themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small));

            YAxis yl = chart.getAxisLeft();
            yl.setDrawAxisLine(true);
            yl.setDrawGridLines(true);
            yl.setAxisMinimum(0f);
            yl.setTextColor(themeUtils.getThemeTextColor(getContext()));
            yl.setTextSize(themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small));

            YAxis yr = chart.getAxisRight();
            yr.setDrawAxisLine(true);
            yr.setDrawGridLines(false);
            yr.setAxisMinimum(1f);
            yr.setTextColor(themeUtils.getThemeTextColor(getContext()));
            yr.setTextSize(themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small));

            chart.getLegend().setEnabled(false);

            chart.clear();
        }
    }

    private void setChartData(final List<StatsData> statsData) {

        ArrayList<BarEntry> values = new ArrayList<>();

        for (StatsData stsdata : statsData) {
            values.add(new BarEntry(Float.parseFloat(stsdata.getLabel()), stsdata.getValue()));
        }

        BarDataSet set1 = new BarDataSet(values, "DataSet");

        set1.setColors(preferenceManager.getStatisticsChartColorTheme().getColors());
        set1.setValueTextSize(themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small));
        set1.setValueTextColor(themeUtils.getThemeTextColor(getContext()));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small));
        data.setValueTextColor(themeUtils.getThemeTextColor(getContext()));
        chart.setData(data);

        chart.invalidate();

        chart.setFitBars(true);
        chart.animateY(1400);
    }

    void bindData() {
        List<StatsData> data = loadData();

        if (chart != null) {
            setChartData(data);
        }
    }

    protected abstract
    @NonNull
    List<StatsData> loadData();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.statistics, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
