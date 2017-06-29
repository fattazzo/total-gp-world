package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking;

import android.content.Context;
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
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerView;
import com.gmail.fattazzo.formula1world.view.chart.valueformatter.AlternateChartValueFormatter;
import com.gmail.fattazzo.formula1world.view.chart.valueformatter.DefaultChartValueFormatter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 23/04/17
 */
@EBean
class ChartManager {

    @RootContext
    Context context;

    @Bean
    ImageUtils imageUtils;

    private int textColor;
    private float textSize;

    @AfterInject
    void init() {
        textColor = getThemeTextColor(context);
        textSize = getThemeTextSize(context);
    }

    @NonNull
    private LineDataSet buildDataSet(@NonNull List<F1Result> results, @NonNull DataSetType type) {
        String driverName = "";
        List<Entry> entries = new ArrayList<>();
        int color = -1;
        float points = 0;
        int idx = 0;
        for (F1Result result : results) {
            points = points + result.points;

            boolean evenData = idx % 2 == 0;
            switch (type) {
                case POSITIONS:
                    entries.add(new Entry(result.race.round, result.positionOrder, evenData));
                    break;
                case POINTS:
                    entries.add(new Entry(result.race.round, points, evenData));
                    break;
            }
            if (color == -1) {
                color = imageUtils.getColorForConstructorRef(result.constructor != null ? result.constructor.constructorRef : "");
            }
            driverName = result.driver != null ? result.driver.getFullName() : "";
            idx++;
        }


        LineDataSet dataSet = new LineDataSet(entries, driverName);
        dataSet.setCircleColor(color);
        return dataSet;
    }

    void loadPointsChartData(@NonNull LineChart pointsChart, @NonNull List<F1Result> results, @NonNull List<F1Result> leaderResults) {

        LineDataSet pointsDataSet = buildDataSet(results, DataSetType.POINTS);
        LineDataSet leaderPointsDataSet = null;
        if (!leaderResults.isEmpty()) {
            leaderPointsDataSet = buildDataSet(leaderResults, DataSetType.POINTS);
            leaderPointsDataSet.setLineWidth(4f);
            int color;
            try {
                color = Color.parseColor(context.getResources().getString(leaderPointsDataSet.getCircleColor(0)));
            } catch (Exception e) {
                color = getThemeBgInvertedColor(context);
            }
            leaderPointsDataSet.setColor(color);
            leaderPointsDataSet.setValueTextColor(textColor);
            leaderPointsDataSet.setValueTextSize(textSize);
            if (leaderPointsDataSet.getEntryCount() < 15) {
                leaderPointsDataSet.setValueFormatter(new DefaultChartValueFormatter());
            } else {
                leaderPointsDataSet.setValueFormatter(new AlternateChartValueFormatter());
            }
        }

        LineData pointsLineData;
        if (leaderPointsDataSet == null || StringUtils.equals(pointsDataSet.getLabel(), leaderPointsDataSet.getLabel())) {
            pointsLineData = new LineData(pointsDataSet);
        } else {
            pointsLineData = new LineData(pointsDataSet, leaderPointsDataSet);
        }
        pointsChart.setData(pointsLineData);
        configureDataSet(pointsDataSet, pointsChart.getAxisLeft().getAxisMinimum());
    }

    void loadPositionsChartData(@NonNull LineChart positionChart, @NonNull List<F1Result> results) {

        LineDataSet positionDataSet = buildDataSet(results, DataSetType.POSITIONS);
        LineData positionLineData = new LineData(positionDataSet);
        positionChart.getAxisLeft().setInverted(true);
        positionChart.getLegend().setEnabled(false);
        positionChart.setData(positionLineData);
        configureDataSet(positionDataSet, positionChart.getAxisLeft().getAxisMaximum());
    }

    void configureChart(LineChart chart) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        if (chart.getData() != null) {
            chart.getData().setHighlightEnabled(false);
            chart.invalidate();
        }

        chart.setTouchEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

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
        chart.getAxisLeft().setGranularity(1.0f);
        chart.getAxisLeft().setGranularityEnabled(true);
        chart.getAxisLeft().setDrawGridLines(false);

        chart.getLegend().setTextColor(textColor);
        chart.getLegend().setTextSize(textSize);

        chart.getAxisRight().setEnabled(false);

        F1MarkerView marker = new F1MarkerView(context);
        //marker.setChartView(chart);
        chart.setMarker(marker);
    }

    private void configureDataSet(LineDataSet dataSet, final float fillPosition) {
        dataSet.setColor(textColor);
        dataSet.setValueTextSize(textSize);
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(4f);
        dataSet.setValueTextColor(textColor);
        dataSet.setDrawFilled(true);

        if (dataSet.getEntryCount() < 15) {
            dataSet.setValueFormatter(new DefaultChartValueFormatter());
        } else {
            dataSet.setValueFormatter(new AlternateChartValueFormatter());
        }


        @SuppressWarnings("ResourceType") int[] colors;
        try {
            colors = new int[]{Color.parseColor(context.getResources().getString(dataSet.getCircleColor(0))), Color.parseColor("#00ffffff")};
        } catch (Exception e) {
            colors = new int[]{Color.parseColor("#00ffffff"), Color.parseColor("#00ffffff")};
        }

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

    private int getThemeTextColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColor, value, true);
        return value.data;
    }

    private float getThemeTextSize(final Context context) {
        float scaleRatio = ObjectUtils.defaultIfNull(context.getResources().getDisplayMetrics().density, 1f);
        float dimenPix = ObjectUtils.defaultIfNull(context.getResources().getDimension(R.dimen.font_size_small), 16f);
        return dimenPix / scaleRatio;
    }

    private int getThemeBgInvertedColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.bgBackgroundInverted, value, true);
        return value.data;
    }

    private enum DataSetType {POSITIONS, POINTS}
}
