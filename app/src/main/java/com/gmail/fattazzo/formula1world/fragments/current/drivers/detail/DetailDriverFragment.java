package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment_;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;


/**
 * @author fattazzo
 *         <p/>
 *         date: 19/04/17
 */
@EFragment(R.layout.view_pager_fragment)
public class DetailDriverFragment extends BaseFragment {

    public static final String TAG = DetailDriverFragment.class.getSimpleName();

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @FragmentArg
    F1Driver driver;

    @ViewById(R.id.title)
    TextView driverNameView;

    @ViewById(R.id.view_pager)
    ViewPager vpPager;

    DetailDriverPagerAdapter adapterViewPager;

    @AfterViews
    void init() {
        driverNameView.setText(driver.getFullName());

        adapterViewPager = new DetailDriverPagerAdapter(getChildFragmentManager(), getActivity(), driver);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(true, preferenceManager.getPagerTansactionAnimation());

        vpPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager.setPadding(0, 0, 0, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager.setPageMargin(90);

    }

    @Override
    public void backPressed() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(CurrentDriversFragment.TAG) != null) {
            FragmentUtils.replace(getActivity(), new HomeFragment_());
        } else {
            FragmentUtils.replace(getActivity(), new CurrentDriversFragment_());
        }
    }
}
