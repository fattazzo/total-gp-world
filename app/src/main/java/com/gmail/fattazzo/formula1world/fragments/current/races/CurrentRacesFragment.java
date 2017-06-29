package com.gmail.fattazzo.formula1world.fragments.current.races;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.DetailRaceFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.dspot.declex.Action.$DetailRaceFragment;
import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 26/05/17
 */
@EFragment(R.layout.fragment_swipe_listview)
public class CurrentRacesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CurrentRacesFragment.class.getSimpleName();

    @Bean
    DataService dataService;

    @Bean
    RacesListAdapter racesListAdapter;

    @ViewById(R.id.list_view)
    ListView listView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.details_fragment_container)
    RelativeLayout detailsRaceFragmentContainer;

    @AfterViews
    void init() {
        listView.setAdapter(racesListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (racesListAdapter.isEmpty()) {
            loadRaces();
        }
    }

    @Background
    public void loadRaces() {
        List<F1Race> result = null;
        try {
            startLoad();
            result = dataService.loadRaces();
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
    void updateUI(List<F1Race> result) {
        try {
            racesListAdapter.clearItems();
            racesListAdapter.setRaces(result);
            racesListAdapter.notifyDataSetChanged();
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @ItemClick(R.id.list_view)
    public void itemClicked(int position) {
        F1Race race = racesListAdapter.getItem(position);

        if (detailsRaceFragmentContainer == null) {
            $DetailRaceFragment(DetailRaceFragment.TAG).race(race);
        } else {
            $DetailRaceFragment(DetailRaceFragment.TAG).race(race).container(R.id.details_fragment_container).add();
        }
    }

    @Override
    public void onRefresh() {
        dataService.clearRacesCache();
        loadRaces();
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
