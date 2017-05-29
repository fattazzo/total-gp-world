package com.gmail.fattazzo.formula1livenews.fragments.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.activity.settings.SettingsActivity_;
import com.gmail.fattazzo.formula1livenews.fragments.home.circuit.CurrentCircuitTask;
import com.gmail.fattazzo.formula1livenews.fragments.home.constructorstandings.CurrentConstructorStandingsTask;
import com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings.CurrentDriverStandingsTask;
import com.gmail.fattazzo.formula1livenews.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/04/17
 */
@OptionsMenu(R.menu.home)
@EFragment(R.layout.content_home)
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Bean
    Utils utils;

    @Bean
    CurrentCircuitTask currentCircuitTask;

    @Bean
    CurrentDriverStandingsTask currentDriverStandingsTask;

    @Bean
    CurrentConstructorStandingsTask currentConstructorStandingsTask;

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @AfterViews
    void init() {
        setRetainInstance(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCircuitTask.loadCurrentSchedule(true);
                currentDriverStandingsTask.loadCurrentStandings(true);
                currentConstructorStandingsTask.loadCurrentStandings(true);
            }
        });

        currentCircuitTask.loadCurrentSchedule();
        currentDriverStandingsTask.loadCurrentStandings();
        currentConstructorStandingsTask.loadCurrentStandings();
    }

    @Event
    void onBackPressedEvent() {
        getActivity().finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SettingsActivity_.intent(getContext()).start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
