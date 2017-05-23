package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.ranking;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResult;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResults;
import com.gmail.fattazzo.formula1livenews.utils.ImageUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 23/04/17
 */
@EBean
public class ChartManager {

    @RootContext
    Context context;

    @Bean
    ImageUtils imageUtils;

    @NonNull
    private LineDataSet buildDataSet(@NonNull List<RaceResults> raceResults, @NonNull DataSetType type) {
        String driverName = "";
        List<Entry> entries = new ArrayList<>();
        int color = -1;
        int points = 0;
        for (RaceResults data : raceResults) {
            for (RaceResult result : data.getResults()) {
                points = points + result.getPoints();

                switch (type) {
                    case POSITIONS:
                        entries.add(new Entry(data.getRound(), result.getPosition()));
                        break;
                    case POINTS:
                        entries.add(new Entry(data.getRound(), points));
                        break;
                }
                if (color == -1) {
                    color = imageUtils.getColorForConstructorId(result.getConstructor().getConstructorId());
                }
                driverName = StringUtils.join(result.getDriver().getGivenName()," ",result.getDriver().getFamilyName());
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, driverName);
        dataSet.setCircleColor(color);
        return dataSet;
    }

    void loadPointsChartData(@NonNull LineChart pointsChart, @NonNull List<RaceResults> raceResults, @NonNull List<RaceResults> leaderRaceResults) {

        LineDataSet pointsDataSet = buildDataSet(raceResults, DataSetType.POINTS);
        LineDataSet leaderPointsDataSet = buildDataSet(leaderRaceResults, DataSetType.POINTS);
        leaderPointsDataSet.setLineWidth(4f);
        leaderPointsDataSet.setColor(Color.parseColor(context.getResources().getString(leaderPointsDataSet.getCircleColor(0))));

        LineData pointsLineData;
        if (StringUtils.equals(pointsDataSet.getLabel(), leaderPointsDataSet.getLabel())) {
            pointsLineData = new LineData(pointsDataSet);
        } else {
            pointsLineData = new LineData(pointsDataSet, leaderPointsDataSet);
        }
        pointsChart.setData(pointsLineData);
        configureDataSet(pointsDataSet, pointsChart.getAxisLeft().getAxisMinimum());
    }

    void loadPositionsChartData(@NonNull LineChart positionChart, @NonNull List<RaceResults> raceResults) {

        LineDataSet positionDataSet = buildDataSet(raceResults, DataSetType.POSITIONS);
        LineData positionLineData = new LineData(positionDataSet);
        positionChart.getAxisLeft().setInverted(true);
        positionChart.getLegend().setEnabled(false);
        positionChart.setData(positionLineData);
        configureDataSet(positionDataSet, positionChart.getAxisLeft().getAxisMaximum());
    }

    public void configureChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        if(chart.getData() != null) {
            chart.getData().setHighlightEnabled(false);
            chart.invalidate();
        }

        chart.setTouchEnabled(false);

        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setAxisLineColor(Color.WHITE);
        chart.getXAxis().setGranularity(1.0f);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);

        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setAxisLineColor(Color.WHITE);
        chart.getAxisLeft().setGranularity(1.0f);
        chart.getAxisLeft().setGranularityEnabled(true);
        chart.getAxisLeft().setDrawGridLines(false);

        chart.getLegend().setTextColor(Color.WHITE);
        int valueInPixels = (int) context.getResources().getDimension(R.dimen.font_size_small);
        int dp = (int) (valueInPixels / context.getResources().getDisplayMetrics().density);
        chart.getLegend().setTextSize(dp);

        chart.getAxisRight().setEnabled(false);
    }

    private void configureDataSet(LineDataSet dataSet, final float fillPosition) {
        dataSet.setColor(Color.WHITE);
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(4f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setDrawFilled(true);

        @SuppressWarnings("ResourceType") int[] colors = {Color.parseColor(context.getResources().getString(dataSet.getCircleColor(0))), Color.parseColor("#00ffffff")};
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        dataSet.setFillDrawable(gd);

        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return fillPosition;
            }
        });
    }

    private enum DataSetType {POSITIONS, POINTS}
}
