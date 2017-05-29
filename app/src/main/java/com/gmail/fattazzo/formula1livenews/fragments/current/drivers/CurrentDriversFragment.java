package com.gmail.fattazzo.formula1livenews.fragments.current.drivers;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.DetailDriverFragment;
import com.gmail.fattazzo.formula1livenews.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;
import com.gmail.fattazzo.formula1livenews.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.dspot.declex.Action.$DetailDriverFragment;
import static com.dspot.declex.Action.$HomeFragment;

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
    ApplicationPreferenceManager preferenceManager;

    @Bean
    CurrentSeasonDataService dataService;

    @Bean
    DriversListAdapter driversListAdapter;

    @ViewById(R.id.current_drivers_list_view)
    ListView listView;

    @ViewById(R.id.current_drivers_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.details_driver_fragment_container)
    RelativeLayout detailsDriverFragmentContainer;

    @AfterViews
    void init() {
        setRetainInstance(true);

        listView.setAdapter(driversListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (driversListAdapter.isEmpty()) {
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
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @UiThread
    void updateUI(List<Driver> result) {
        try {
            driversListAdapter.clearItems();
            driversListAdapter.setDrivers(result);
            driversListAdapter.notifyDataSetChanged();
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @ItemClick(R.id.current_drivers_list_view)
    public void itemClicked(int position) {
        Driver driver = driversListAdapter.getItem(position);

        if(detailsDriverFragmentContainer == null) {
            $DetailDriverFragment(DetailDriverFragment.TAG).driver(driver);
        } else {
            $DetailDriverFragment(DetailDriverFragment.TAG).driver(driver).container(R.id.details_driver_fragment_container).add();
        }
    }

    // ----------------------------FILTERS ------------------------------------

    @OptionsItem
    void order_by_number() {
        sortDrivers(DriversListAdapter.SortType.NUMBER);
    }

    @OptionsItem
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

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
