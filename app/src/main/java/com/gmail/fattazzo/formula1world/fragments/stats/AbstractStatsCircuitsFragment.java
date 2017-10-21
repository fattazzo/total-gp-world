package com.gmail.fattazzo.formula1world.fragments.stats;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsCircuitsDataListAdapter;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.service.StatisticsService;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/10/17
 */
@EFragment(R.layout.fragment_stats_circuits)
public abstract class AbstractStatsCircuitsFragment extends BaseFragment {

    @FragmentArg
    public int seasonStart;

    @FragmentArg
    public int seasonEnd;

    @Bean
    protected StatisticsService statisticsService;

    @Bean
    protected DataService dataService;

    @Bean
    protected LocaleUtils localeUtils;

    @Bean
    protected ImageUtils imageUtils;

    @Bean
    protected Utils utils;

    @ViewById
    protected ExpandableListView dataListView;

    @ViewById
    protected RelativeLayout headerListView;

    private Date lastData;

    @AfterViews
    protected void initViews() {

        dataListView.setAdapter(createListAdapter(new ArrayList<StatsCircuitsData>()));

        headerListView.removeAllViews();
        View headerView = getHeaderListView();
        if (headerView != null) {
            headerListView.addView(headerView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            headerListView.invalidate();
            headerListView.forceLayout();
        }

        bindData();
    }

    @AfterInject
    protected void afterInjenct() {
        lastData = statisticsService.getLastRaceData();
    }

    protected BaseExpandableListAdapter createListAdapter(List<StatsCircuitsData> data) {
        return new StatsCircuitsDataListAdapter(getActivity(), data, dataService, localeUtils, imageUtils,utils);
    }

    void bindData() {
        List<StatsCircuitsData> data = loadData();

        setListData(data);
    }

    private void setListData(final List<StatsCircuitsData> data) {
        BaseExpandableListAdapter adapter = createListAdapter(data);
        dataListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected
    @Nullable
    View getHeaderListView() {
        return null;
    }

    protected abstract
    @NonNull
    List<StatsCircuitsData> loadData();
}
