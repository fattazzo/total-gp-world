package com.gmail.fattazzo.formula1livenews.fragments.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.fragments.home.circuit.CurrentCircuitTask;
import com.gmail.fattazzo.formula1livenews.fragments.home.constructorstandings.CurrentConstructorStandingsTask;
import com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings.CurrentDriverStandingsTask;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCircuitTask.loadCurrentSchedule();
                currentDriverStandingsTask.loadCurrentStandings();
                currentConstructorStandingsTask.loadCurrentStandings();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
