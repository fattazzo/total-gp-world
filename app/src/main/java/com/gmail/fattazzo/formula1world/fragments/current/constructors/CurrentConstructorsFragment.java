package com.gmail.fattazzo.formula1world.fragments.current.constructors;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.DetailConstructorFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.dspot.declex.Action.$DetailConstructorFragment;
import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 26/05/17
 */
@EFragment(R.layout.fragment_current_constructors)
public class CurrentConstructorsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CurrentConstructorsFragment.class.getSimpleName();

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @Bean
    DataService dataService;

    @Bean
    ConstructorsListAdapter constructorsListAdapter;

    @ViewById(R.id.current_constructors_list_view)
    ListView listView;

    @ViewById(R.id.current_constructors_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.details_constructor_fragment_container)
    RelativeLayout detailsConstructorFragmentContainer;

    @AfterViews
    void init() {
        listView.setAdapter(constructorsListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (constructorsListAdapter.isEmpty()) {
            onRefresh();
        }
    }

    @Background
    public void loadConstructors() {
        List<F1Constructor> result = null;
        try {
            startLoad();
            result = dataService.loadConstructors();
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
    void updateUI(List<F1Constructor> result) {
        try {
            constructorsListAdapter.clearItems();
            constructorsListAdapter.setConstructors(result);
            constructorsListAdapter.notifyDataSetChanged();
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @ItemClick(R.id.current_constructors_list_view)
    public void itemClicked(int position) {
        F1Constructor constructor = constructorsListAdapter.getItem(position);

        if(detailsConstructorFragmentContainer == null) {
            $DetailConstructorFragment(DetailConstructorFragment.TAG).constructor(constructor);
        } else {
            $DetailConstructorFragment(DetailConstructorFragment.TAG).constructor(constructor).container(R.id.details_constructor_fragment_container).add();
        }
    }

    @Override
    public void onRefresh() {
        loadConstructors();
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
