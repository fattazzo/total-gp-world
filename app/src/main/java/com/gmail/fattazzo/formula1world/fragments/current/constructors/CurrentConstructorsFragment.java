package com.gmail.fattazzo.formula1world.fragments.current.constructors;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.DetailConstructorFragment_;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 26/05/17
 */
@EFragment(R.layout.fragment_swipe_listview)
public class CurrentConstructorsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CurrentConstructorsFragment.class.getSimpleName();

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @Bean
    DataService dataService;

    @Bean
    ConstructorsListAdapter constructorsListAdapter;

    @ViewById(R.id.list_view)
    ListView listView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.details_fragment_container)
    RelativeLayout detailsConstructorFragmentContainer;

    @AfterViews
    void init() {
        listView.setAdapter(constructorsListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (constructorsListAdapter.isEmpty()) {
            loadConstructors();
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

    @ItemClick(R.id.list_view)
    public void itemClicked(int position) {
        F1Constructor constructor = constructorsListAdapter.getItem(position);

        int container;
        if (detailsConstructorFragmentContainer == null) {
            container = R.id.container;
        } else {
            container = R.id.details_fragment_container;
        }

        FragmentUtils.replace(getActivity(), DetailConstructorFragment_.builder().constructor(constructor).build(), container);
    }

    @Override
    public void onRefresh() {
        dataService.clearConstructorsCache();
        loadConstructors();
    }
}
