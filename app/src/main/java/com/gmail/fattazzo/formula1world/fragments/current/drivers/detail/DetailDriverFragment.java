package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$CurrentDriversFragment;
import static com.dspot.declex.Action.$HomeFragment;


/**
 * @author fattazzo
 *         <p/>
 *         date: 19/04/17
 */
@EFragment(R.layout.detail_driver_fragment)
public class DetailDriverFragment extends Fragment {

    public static final String TAG = DetailDriverFragment.class.getSimpleName();

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @FragmentArg
    F1Driver driver;

    @ViewById(R.id.detail_driver_name)
    TextView driverNameView;

    @ViewById(R.id.detail_driver_pager)
    ViewPager vpPager;

    DetailDriverPagerAdapter adapterViewPager;

    @AfterViews
    void init() {
        driverNameView.setText(driver.getFullName());

        adapterViewPager = new DetailDriverPagerAdapter(getChildFragmentManager(),getActivity(),driver);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(true, preferenceManager.getPagerTansactionAnimation());

        vpPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager.setPadding(0, 0, 0, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager.setPageMargin(90);

    }

    @Event
    void onBackPressedEvent() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag(CurrentDriversFragment.TAG) != null) {
            $HomeFragment(HomeFragment.TAG);
        } else {
            $CurrentDriversFragment(CurrentDriversFragment.TAG);
        }
    }
}
