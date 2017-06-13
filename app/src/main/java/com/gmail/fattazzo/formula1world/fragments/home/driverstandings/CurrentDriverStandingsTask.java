package com.gmail.fattazzo.formula1world.fragments.home.driverstandings;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

@EBean
public class CurrentDriverStandingsTask implements View.OnTouchListener {

    @Bean
    DataService dataService;

    @RootContext
    HomeActivity activity;

    @ViewById(R.id.home_driverstandings_progressBar)
    ProgressBar progressBar;

    @Bean
    DriverStandingsListAdapter adapterFront;
    @Bean
    DriverStandingsListAdapter adapterBack;

    private ViewFlipper viewFlipper;

    private float initialX = 0;
    private float initialY = 0;

    private List<F1DriverStandings> driverStandings = null;

    @ViewById(R.id.home_driver_standings_layout)
    void setOneView(ViewFlipper layout) {
        this.viewFlipper = layout;
        ListView listViewBack = (ListView) viewFlipper.findViewById(R.id.standing_listview_back);
        ListView listViewFront = (ListView) viewFlipper.findViewById(R.id.standing_listview_front);

        TextView titleViewFront = (TextView) viewFlipper.findViewById(R.id.standing_title_front);
        titleViewFront.setText(activity.getString(R.string.driverStandings));
        TextView titleViewBack = (TextView) viewFlipper.findViewById(R.id.standing_title_back);
        titleViewBack.setText(activity.getString(R.string.driverStandings));

        listViewFront.setAdapter(adapterFront);
        listViewFront.setOnTouchListener(this);
        listViewBack.setAdapter(adapterBack);
        listViewBack.setOnTouchListener(this);
    }

    @UiThread
    void start() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Background
    public void loadCurrentStandings(boolean reloadData) {
        driverStandings = null;
        loadCurrentStandings();
    }

    @Background
    public void loadCurrentStandings() {

        try {
            if (CollectionUtils.isEmpty(driverStandings)) {
                start();
                driverStandings = dataService.loadDriverStandings();
            }
        } finally {
            updateUI();
        }
    }

    @UiThread
    void updateUI() {
        try {
            List<F1DriverStandings> listFront = new ArrayList<>();
            List<F1DriverStandings> listBack = new ArrayList<>();
            int nr = 0;
            for (F1DriverStandings standings : ListUtils.emptyIfNull(driverStandings)) {
                if (nr < 5) {
                    listFront.add(standings);
                } else {
                    listBack.add(standings);
                }
                nr++;
            }
            adapterFront.clearItems();
            adapterFront.setDrivers(listFront);
            adapterFront.notifyDataSetChanged();

            adapterBack.clearItems();
            adapterBack.setDrivers(listBack);
            adapterBack.notifyDataSetChanged();
        } finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float deltaY;
        float deltaX;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialX = event.getRawX();
            initialY = event.getRawY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            deltaX = event.getRawX() - initialX;
            deltaY = event.getRawY() - initialY;

            if (deltaY == 0 && deltaX == 0) {

                viewFlipper.setFlipInterval(1000);
                if (v.getId() == R.id.standing_listview_front) {
                    viewFlipper.showNext();
                } else {
                    viewFlipper.showPrevious();
                }
                viewFlipper.invalidate();
            }
        }
        return false;
    }
}