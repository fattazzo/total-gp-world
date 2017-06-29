package com.gmail.fattazzo.formula1world.fragments.current.races.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;

import static com.dspot.declex.Action.$CurrentRacesFragment;
import static com.dspot.declex.Action.$HomeFragment;


/**
 * @author fattazzo
 *         <p/>
 *         date: 19/04/17
 */
@EFragment(R.layout.view_pager_fragment)
public class DetailRaceFragment extends Fragment {

    public static final String TAG = DetailRaceFragment.class.getSimpleName();

    @Bean
    DataService dataService;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @FragmentArg
    F1Race race;

    @ViewById(R.id.title)
    TextView driverNameView;

    @ViewById(R.id.view_pager)
    ViewPager vpPager;

    DetailRacePagerAdapter adapterViewPager;

    private boolean hasResults = false;
    private boolean hasQualifications = false;

    @AfterViews
    void initView() {
        driverNameView.setText(race.name);

        vpPager.setPageTransformer(true, preferenceManager.getPagerTansactionAnimation());

        vpPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager.setPadding(0, 0, 0, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager.setPageMargin(90);

        init();
    }

    @Background
    void loadRacesData() {
        hasResults = CollectionUtils.isNotEmpty(dataService.loadRaceResult(race));
        hasQualifications = CollectionUtils.isNotEmpty(dataService.loadQualification(race));

        initPagerAdapter();
    }

    @UiThread
    void init() {
        loadRacesData();
    }

    @UiThread
    void initPagerAdapter() {
        adapterViewPager = new DetailRacePagerAdapter(getChildFragmentManager(), getActivity(), race, hasResults,hasQualifications);
        vpPager.setAdapter(adapterViewPager);
    }

    @Event
    void onBackPressedEvent() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(CurrentRacesFragment.TAG) != null) {
            $HomeFragment(HomeFragment.TAG);
        } else {
            $CurrentRacesFragment(CurrentRacesFragment.TAG);
        }
    }
}
