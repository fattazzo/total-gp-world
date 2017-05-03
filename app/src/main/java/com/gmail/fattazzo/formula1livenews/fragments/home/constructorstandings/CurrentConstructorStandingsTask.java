package com.gmail.fattazzo.formula1livenews.fragments.home.constructorstandings;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1livenews.ergast.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

@EBean
public class CurrentConstructorStandingsTask implements View.OnTouchListener {

    @Bean
    CurrentSeasonDataService dataService;

    @RootContext
    HomeActivity activity;

    @ViewById(R.id.home_constructorStanding_progressBar)
    ProgressBar progressBar;

    @Bean
    ConstructorStandingsListAdapter adapterFront;

    @Bean
    ConstructorStandingsListAdapter adapterBack;

    private ViewFlipper viewFlipper;

    private float initialX = 0;
    private float initialY = 0;

    private List<ConstructorStandings> constructorStandings = null;

    @ViewById(R.id.home_constructor_standings_layout)
    void setOneView(ViewFlipper layout) {
        this.viewFlipper = layout;
        ListView listViewBack = (ListView) viewFlipper.findViewById(R.id.standing_listview_back);
        ListView listViewFront = (ListView) viewFlipper.findViewById(R.id.standing_listview_front);

        TextView titleViewFront = (TextView) viewFlipper.findViewById(R.id.standing_title_front);
        titleViewFront.setText(activity.getString(R.string.constructor_standings));
        TextView titleViewBack = (TextView) viewFlipper.findViewById(R.id.standing_title_back);
        titleViewBack.setText(activity.getString(R.string.constructor_standings));

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
        constructorStandings = null;
        loadCurrentStandings();
    }

    @Background
    public void loadCurrentStandings() {

        try {
            if (constructorStandings == null) {
                start();
                constructorStandings = dataService.loadConstructorStandings();
            }
        } finally {
            updateUI();
        }
    }

    @UiThread
    void updateUI() {
        try {
            List<ConstructorStandings> listFront = new ArrayList<>();
            List<ConstructorStandings> listBack = new ArrayList<>();
            int nr = 0;
            for (ConstructorStandings standings : ListUtils.emptyIfNull(constructorStandings)) {
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