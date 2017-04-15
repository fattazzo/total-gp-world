package com.gmail.fattazzo.formula1livenews.fragments.current;

import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.dspot.declex.Action;
import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;



import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/04/17
 */
@OptionsMenu(R.menu.drivers)
@EFragment(R.layout.fragment_current_drivers)
public class CurrentDriversFragment extends Fragment {

    @Bean
    DriversTask driversTask;

    @AfterViews
    void init() {
        driversTask.loadDrivers();
    }

    @com.dspot.declex.api.action.Action
    void order_by_number() {
        System.out.println("Ordina per numero!");
    }

    @com.dspot.declex.api.action.Action
    void order_by_name() {
        System.out.println("Ordina per nome!");
    }

    @Event
    void onBackPressedEvent() {
        Action.$HomeFragment();
    }
}
