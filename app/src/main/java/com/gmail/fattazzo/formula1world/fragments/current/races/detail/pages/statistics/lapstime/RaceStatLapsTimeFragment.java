package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.lapstime;

import android.support.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.AbstractRaceLapTimeFragment;
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerLapTimeView;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 18/09/17
 */
@EFragment(R.layout.fragment_race_stat_chart_drivers)
public class RaceStatLapsTimeFragment extends AbstractRaceLapTimeFragment {

    @Override
    @NonNull
    protected LineDataSet buildDataSet(@NonNull List<F1LapTime> results, F1Driver driver) {

        Collections.sort(results, new Comparator<F1LapTime>() {
            @Override
            public int compare(F1LapTime o1, F1LapTime o2) {
                return o1.lap - o2.lap;
            }
        });

        List<Entry> entries = new ArrayList<>();
        for (F1LapTime lapTime : results) {
            lapTime.driver = driver;
            entries.add(new Entry(lapTime.lap, lapTime.milliseconds, lapTime));
        }

        LineDataSet dataSet = new LineDataSet(entries, driver.getFullName());
        dataSet.setLineWidth(4f);
        int color = dataService.loadDriverColor(driver);
        dataSet.setColor(color);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawCircles(true);
        dataSet.setDrawValues(false);

        if (color == themeUtils.getThemeBGColor(getContext())) {
            dataSet.enableDashedLine(10f, 5f, 0f);
            dataSet.setColor(textColor);
        }
        return dataSet;
    }

    @Override
    protected void configureChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        if (chart.getData() != null) {
            chart.getData().setHighlightEnabled(false);
            chart.invalidate();
        }

        chart.getXAxis().setTextColor(textColor);
        chart.getXAxis().setTextSize(textSize);
        chart.getXAxis().setAxisLineColor(textColor);
        chart.getXAxis().setGranularity(1.0f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);

        chart.getAxisLeft().setTextColor(textColor);
        chart.getAxisLeft().setTextSize(textSize);
        chart.getAxisLeft().setAxisLineColor(textColor);
        chart.getAxisLeft().setGranularity(1f);
        chart.getAxisLeft().setGranularityEnabled(true);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);

        chart.getLegend().setEnabled(false);

        chart.getAxisRight().setEnabled(false);

        F1MarkerLapTimeView marker = new F1MarkerLapTimeView(getContext());
        chart.setMarker(marker);
    }

    @Override
    protected void sortLapTimesForList(List<F1LapTime> lapsListView) {
        Collections.sort(lapsListView, new Comparator<F1LapTime>() {
            @Override
            public int compare(F1LapTime o1, F1LapTime o2) {
                if (o1.lap - o2.lap == 0) {
                    return o1.milliseconds - o2.milliseconds;
                } else {
                    return o1.lap - o2.lap;
                }
            }
        });
    }
}
