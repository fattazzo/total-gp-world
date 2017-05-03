package com.gmail.fattazzo.formula1livenews.fragments.current.drivers;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.DetailDriverFragment;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.DetailDriverFragment_;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;
import com.gmail.fattazzo.formula1livenews.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
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
public class CurrentDriversFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CurrentDriversFragment.class.getSimpleName();

    @Bean
    Utils utils;

    @Bean
    CurrentSeasonDataService dataService;

    @Bean
    DriversListAdapter driversListAdapter;

    @ViewById(R.id.current_drivers_list_view)
    ListView listView;

    @ViewById(R.id.current_drivers_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @AfterViews
    void init() {
        listView.setAdapter(driversListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if(driversListAdapter.isEmpty()) {
            onRefresh();
        }
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
        swipeRefreshLayout.setRefreshing(true);
    }

    @UiThread
    void updateUI(List<Driver> result) {
        try {
            driversListAdapter.clearItems();
            driversListAdapter.setDrivers(result);
            driversListAdapter.notifyDataSetChanged();
        } finally {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @ItemClick(R.id.current_drivers_list_view)
    public void itemClicked(int position) {
        Driver driver = driversListAdapter.getItem(position);
        //$DetailDriverFragment().driver(driver);
        DetailDriverFragment detailDriverFragment = DetailDriverFragment_.builder().driver(driver).build();
        utils.showFragment(getActivity(),detailDriverFragment, DetailDriverFragment.TAG,false);
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

    @Override
    public void onRefresh() {
        loadDrivers();
    }
}
