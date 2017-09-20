package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.DriverSpinnerModel;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.DriversSpinnerAdapter;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.utils.ThemeUtils;
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerLapTimeView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author fattazzo
 *         <p/>
 *         date: 19/09/17
 */
@EFragment(R.layout.fragment_race_stat_chart_drivers)
public abstract class AbstractRaceLapTimeFragment extends Fragment {

    @FragmentArg
    protected F1Race race;

    @Bean
    protected DataService dataService;

    @Bean
    protected ThemeUtils themeUtils;

    @Bean
    protected DriversSpinnerAdapter spinnerAdapter;

    @ViewById
    protected Spinner drivers_spinner;

    @ViewById
    protected ConstraintLayout listHeader;

    @Bean
    protected LapTimeListAdapter adapter;

    @ViewById
    protected LineChart chart;
    @ViewById
    protected ListView listview;

    @ViewById
    protected ViewFlipper view_flipper;

    @ViewById
    protected ProgressBar refresh_progressBar;
    protected int textColor;
    protected float textSize;
    private DriverSpinnerModel allDriversSpinnerModel;

    @AfterInject
    protected void init() {
        textColor = themeUtils.getThemeTextColor(getContext());
        textSize = themeUtils.getThemeTextSize(getContext(), R.dimen.font_size_small);

        F1Driver f1Driver = new F1Driver();
        f1Driver.driverRef = "___";
        f1Driver.givenName = getString(R.string.all_drivers);
        f1Driver.familyName = "";
        F1Constructor f1Constructor = new F1Constructor();
        f1Constructor.constructorRef = "___";
        allDriversSpinnerModel = new DriverSpinnerModel(f1Driver, f1Constructor);
    }

    @AfterViews
    protected void initViews() {
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

        configureChart(chart);

        ((TextView) listHeader.findViewById(R.id.driverName)).setText(getString(R.string.drivers));
        ((TextView) listHeader.findViewById(R.id.lap)).setText(getString(R.string.lap));
        ((TextView) listHeader.findViewById(R.id.position)).setText(getString(R.string.stats_pos));
        ((TextView) listHeader.findViewById(R.id.time)).setText(getString(R.string.time2));
    }

    @CheckedChange
    protected void chartListviewSwitch(Switch chartListviewSwitch) {
        if (chartListviewSwitch.isChecked()) {
            view_flipper.showNext();
        } else {
            view_flipper.showPrevious();
        }
        view_flipper.invalidate();
    }

    @Click
    protected void add_button() {
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

            ILineDataSet dataSetDriver = chart.getLineData() != null ? chart.getLineData().getDataSetByLabel(driverSelected.getDriver().getFullName(), false) : null;
            if (dataSetDriver != null) {
                // dataset fro driver selected already added
                continue;
            }

            driversForChart.add(driverSelected);
        }
        loadDriverLapTimes(race, driversForChart);
    }

    @Click
    protected void remove_button() {
        DriverSpinnerModel driverSelected = (DriverSpinnerModel) drivers_spinner.getSelectedItem();

        try {
            chart.setMarker(null);

            if (driverSelected.getDriver().driverRef.equals("___")) {
                chart.clear();
                adapter.clearItems();
                adapter.notifyDataSetChanged();
            } else {
                ILineDataSet dataSetByLabel = chart.getLineData() != null ? chart.getLineData().getDataSetByLabel(driverSelected.getDriver().getFullName(), false) : null;
                if (dataSetByLabel != null) {
                    chart.getLineData().removeDataSet(dataSetByLabel);
                    chart.getLineData().notifyDataChanged();
                    chart.notifyDataSetChanged();

                    adapter.removeItems(driverSelected.getDriver());
                    adapter.notifyDataSetChanged();
                }
            }
        } finally {
            chart.animateX(1000, Easing.EasingOption.EaseInSine);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (chart != null) {
                        chart.setMarker(new F1MarkerLapTimeView(getContext()));
                    }
                }
            }, 2000);
        }
    }

    @Background
    protected void loadDriverLapTimes(F1Race race, List<DriverSpinnerModel> driversModel) {
        startAdd();

        Map<F1Driver, List<F1LapTime>> lapsMap = new HashedMap<>();
        for (DriverSpinnerModel model : driversModel) {
            List<F1LapTime> driverLapTimes = dataService.loadLaps(race, model.getDriver());
            if (CollectionUtils.isNotEmpty(driverLapTimes)) {
                lapsMap.put(model.getDriver(), driverLapTimes);
            }
        }

        updateChart(lapsMap);
        updateListView(lapsMap);
    }

    @UiThread
    protected void startAdd() {
        if (refresh_progressBar != null) {
            refresh_progressBar.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    protected void updateListView(Map<F1Driver, List<F1LapTime>> laps) {
        List<F1LapTime> lapsListView = new ArrayList<>();
        for (Map.Entry<F1Driver, List<F1LapTime>> driverEntry : laps.entrySet()) {
            for (F1LapTime lapTime : driverEntry.getValue()) {
                lapTime.driver = driverEntry.getKey();
                lapsListView.add(lapTime);
            }
        }

        sortLapTimesForList(lapsListView);

        adapter.setLapTimes(lapsListView);
        listview.setAdapter(adapter);
    }

    @UiThread
    protected void updateChart(Map<F1Driver, List<F1LapTime>> laps) {
        try {
            for (Map.Entry<F1Driver, List<F1LapTime>> driverEntry : laps.entrySet()) {
                LineDataSet dataSet = buildDataSet(driverEntry.getValue(), driverEntry.getKey());
                if (chart == null) {
                    return;
                }
                if (chart.getLineData() == null) {
                    chart.setData(new LineData(dataSet));
                } else {
                    chart.getLineData().addDataSet(dataSet);
                }
            }
            chart.notifyDataSetChanged();
            chart.animateX(1000, Easing.EasingOption.EaseInSine);
        } finally {
            if (refresh_progressBar != null) {
                refresh_progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected abstract void configureChart(LineChart chart);

    protected abstract
    @NonNull
    LineDataSet buildDataSet(@NonNull List<F1LapTime> results, F1Driver driver);

    protected abstract void sortLapTimesForList(List<F1LapTime> lapsListView);
}
