package com.gmail.fattazzo.formula1livenews.fragments.current.drivers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dspot.declex.Action;
import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/04/17
 */
@OptionsMenu(R.menu.drivers)
@EFragment(R.layout.fragment_current_drivers)
public class CurrentDriversFragment extends Fragment {

    @Bean
    CurrentSeasonDataService dataService;

    @ViewById(R.id.drivers_progressBar)
    ProgressBar progressBar;

    @Bean
    DriversListAdapter driversListAdapter;

    @ViewById(R.id.current_drivers_list_view)
    void setOneView(ListView listView){
        listView.setAdapter(driversListAdapter);
    }

    @AfterViews
    void init() {
        loadDrivers();
    }

    @Background
    public void loadDrivers() {
        List<Driver> result = null;
        try {
            startLoad();
            result = dataService.loadDrivers();
        } finally {
            updateUI(result);
        }
    }

    @UiThread
    void startLoad() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @UiThread
    void updateUI(List<Driver> result) {
        try {
            driversListAdapter.clearItems();
            driversListAdapter.setDrivers(result);
            driversListAdapter.notifyDataSetChanged();
        } finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    // ----------------------------FILTERS ------------------------------------

    @com.dspot.declex.api.action.Action
    void order_by_number() {
        sortDrivers(DriversListAdapter.SortType.NUMBER);
    }

    @com.dspot.declex.api.action.Action
    void order_by_name() {
        sortDrivers(DriversListAdapter.SortType.NAME);
    }

    private void sortDrivers(DriversListAdapter.SortType sortType) {
        driversListAdapter.setSortType(sortType);
        driversListAdapter.sortDrivers();
        driversListAdapter.notifyDataSetChanged();
    }

    // ------------------------------------------------------------------------

    @Event
    void onBackPressedEvent() {
        Action.$HomeFragment();
    }
}
