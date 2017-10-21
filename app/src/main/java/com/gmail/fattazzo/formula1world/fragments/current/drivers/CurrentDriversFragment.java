package com.gmail.fattazzo.formula1world.fragments.current.drivers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.DetailDriverFragment_;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

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

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/04/17
 */
@OptionsMenu(R.menu.drivers)
@EFragment(R.layout.fragment_swipe_listview)
public class CurrentDriversFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CurrentDriversFragment.class.getSimpleName();

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @Bean
    DataService dataService;

    @Bean
    DriversListAdapter driversListAdapter;

    @ViewById(R.id.list_view)
    ListView listView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.details_fragment_container)
    RelativeLayout detailsDriverFragmentContainer;

    @AfterViews
    void init() {
        setRetainInstance(true);

        listView.setAdapter(driversListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (driversListAdapter.isEmpty()) {
            loadDrivers();
        }
    }

    @Background
    public void loadDrivers() {
        List<F1Driver> result = null;
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
    void updateUI(List<F1Driver> result) {
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

    @ItemClick(R.id.list_view)
    public void itemClicked(int position) {
        F1Driver driver = driversListAdapter.getItem(position);

        int container;
        if (detailsDriverFragmentContainer == null) {
            container = R.id.container;
        } else {
            container = R.id.details_fragment_container;
        }
        FragmentUtils.replace(getActivity(), DetailDriverFragment_.builder().driver(driver).build(), container);
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

    @OptionsItem
    void order_by_nationality() {
        sortDrivers(DriversListAdapter.SortType.NATIONALITY);
    }

    @OptionsItem
    void order_by_date_of_birth() {
        sortDrivers(DriversListAdapter.SortType.DATE_OF_BIRTH);
    }

    private void sortDrivers(DriversListAdapter.SortType sortType) {
        driversListAdapter.setSortType(sortType);
        driversListAdapter.sortDrivers();
        driversListAdapter.notifyDataSetChanged();
    }

    // ------------------------------------------------------------------------

    @Override
    public void onRefresh() {
        dataService.clearDriversCache();
        loadDrivers();
    }
}
