package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerEntryDataView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/07/17
 */
@EFragment(R.layout.fragment_race_stat_positions)
public class RaceStatPositionsFragment extends Fragment {

    @FragmentArg
    F1Race race;

    @Bean
    DataService dataService;

    @Bean
    ImageUtils imageUtils;

    @Bean
    DriversSpinnerAdapter spinnerAdapter;

    @ViewById
    Spinner drivers_spinner;

    @ViewById
    LineChart positions_chart;

    @ViewById
    ProgressBar refresh_progressBar;

    private int textColor;
    private float textSize;

    private DriverSpinnerModel allDriversSpinnerModel;

    @AfterInject
    void init() {
        textColor = getThemeTextColor(getContext());
        textSize = getThemeTextSize(getContext());

        F1Driver f1Driver = new F1Driver();
        f1Driver.driverRef = "___";
        f1Driver.givenName = getString(R.string.all_drivers);
        f1Driver.familyName = "";
        F1Constructor f1Constructor = new F1Constructor();
        f1Constructor.constructorRef = "___";
        allDriversSpinnerModel = new DriverSpinnerModel(f1Driver, f1Constructor);
    }

    @AfterViews
    void initViews() {
        Set<DriverSpinnerModel> driversSpinner = new TreeSet<>(new Comparator<DriverSpinnerModel>() {
            @Override
            public int compare(DriverSpinnerModel o1, DriverSpinnerModel o2) {
                return o1.getDriver().getFullName().compareTo(o2.getDriver().getFullName());
            }
        });

        List<F1Result> results = dataService.loadRaceResult(race);
        if (dataService.hasLocalLapsData(race)) {
            driversSpinner.add(allDriversSpinnerModel);
        }
        for (F1Result result : results) {
            driversSpinner.add(new DriverSpinnerModel(result.driver, result.constructor));
        }

        spinnerAdapter.setDrivers(driversSpinner);
        drivers_spinner.setAdapter(spinnerAdapter);

        configureChart(positions_chart);
    }

    @Click
    void add_button() {
        List<DriverSpinnerModel> driversToAdd = new ArrayList<>();
        if (drivers_spinner.getSelectedItem().equals(allDriversSpinnerModel)) {
            driversToAdd.addAll(((DriversSpinnerAdapter) drivers_spinner.getAdapter()).getItems());
        } else {
            driversToAdd.add((DriverSpinnerModel) drivers_spinner.getSelectedItem());
        }

        List<DriverSpinnerModel> driversForChart = new ArrayList<>();
        for (DriverSpinnerModel driverSelected : driversToAdd) {
            if (driverSelected.getDriver().driverRef.equals("___")) {
                // skip "all drivers" entry
                continue;
            }

            ILineDataSet dataSetDriver = positions_chart.getLineData() != null ? positions_chart.getLineData().getDataSetByLabel(driverSelected.getDriver().getFullName(), false) : null;
            if (dataSetDriver != null) {
                // dataset fro driver selected already added
                continue;
            }

            driversForChart.add(driverSelected);
        }
        loadDriverLapTimes(race, driversForChart);
    }

    @Background
    void loadDriverLapTimes(F1Race race, List<DriverSpinnerModel> driversModel) {
        startAdd();

        Map<F1Driver, List<F1LapTime>> lapsMap = new HashedMap<>();
        for (DriverSpinnerModel model : driversModel) {
            List<F1LapTime> driverLapTimes = dataService.loadLaps(race, model.getDriver());
            if (CollectionUtils.isNotEmpty(driverLapTimes)) {
                lapsMap.put(model.getDriver(), driverLapTimes);
            }
        }

        updateChart(lapsMap);
    }

    @UiThread
    void startAdd() {
        if (refresh_progressBar != null) {
            refresh_progressBar.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    void updateChart(Map<F1Driver, List<F1LapTime>> laps) {
        try {
            for (Map.Entry<F1Driver, List<F1LapTime>> driverEntry : laps.entrySet()) {
                LineDataSet dataSet = buildDataSet(driverEntry.getValue(), driverEntry.getKey());
                if(positions_chart == null) {
                    return;
                }
                if (positions_chart.getLineData() == null) {
                    positions_chart.setData(new LineData(dataSet));
                } else {
                    positions_chart.getLineData().addDataSet(dataSet);
                }
            }
            positions_chart.getLineData().notifyDataChanged();

            positions_chart.notifyDataSetChanged();
            positions_chart.animateX(1000,Easing.EasingOption.EaseInSine);
        } finally {
            if (refresh_progressBar != null) {
                refresh_progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Click
    void remove_button() {
        DriverSpinnerModel driverSelected = (DriverSpinnerModel) drivers_spinner.getSelectedItem();

        if (driverSelected.getDriver().driverRef.equals("___")) {
            positions_chart.clear();
        } else {
            ILineDataSet dataSetByLabel = positions_chart.getLineData() != null ? positions_chart.getLineData().getDataSetByLabel(driverSelected.getDriver().getFullName(), false) : null;
            if (dataSetByLabel != null) {
                positions_chart.getLineData().removeDataSet(dataSetByLabel);

                positions_chart.getLineData().notifyDataChanged();

                positions_chart.notifyDataSetChanged();
                positions_chart.animateX(1000,Easing.EasingOption.EaseInSine);
            }
        }
    }

    @NonNull
    public LineDataSet buildDataSet(@NonNull List<F1LapTime> results, F1Driver driver) {
        List<Entry> entries = new ArrayList<>();
        for (F1LapTime lapTime : results) {
            entries.add(new Entry(lapTime.lap, lapTime.position, driver.getFullName()));
        }

        LineDataSet dataSet = new LineDataSet(entries, driver.getFullName());
        dataSet.setLineWidth(4f);
        int color = dataService.loadDriverColor(driver);
        dataSet.setColor(color);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawCircles(false);

        if (color == getThemeBGColor(getContext())) {
            dataSet.enableDashedLine(10f, 5f, 0f);
            dataSet.setColor(textColor);
        }
        return dataSet;
    }

    void configureChart(LineChart chart) {
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
        chart.getAxisLeft().setGranularity(1.0f);
        chart.getAxisLeft().setGranularityEnabled(true);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setInverted(true);

        chart.getLegend().setEnabled(false);

        chart.getAxisRight().setEnabled(false);

        F1MarkerEntryDataView marker = new F1MarkerEntryDataView(getContext(), null, getResources().getString(R.string.detail_driver_position));
        chart.setMarker(marker);
    }

    private int getThemeTextColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColor, value, true);
        return value.data;
    }

    private int getThemeBGColor(final Context context) {
        try {
            final TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.bgBackground, value, true);
            return value.data;
        } catch (Exception e) {
            return android.R.color.transparent;
        }
    }

    private float getThemeTextSize(final Context context) {
        float scaleRatio = ObjectUtils.defaultIfNull(context.getResources().getDisplayMetrics().density, 1f);
        float dimenPix = ObjectUtils.defaultIfNull(context.getResources().getDimension(R.dimen.font_size_small), 16f);
        return dimenPix / scaleRatio;
    }
}
