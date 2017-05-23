package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;


/**
 * @author fattazzo
 *         <p/>
 *         date: 19/04/17
 */
@EFragment(R.layout.detail_driver_fragment)
public class DetailDriverFragment extends Fragment {

    public static final String TAG = DetailDriverFragment.class.getSimpleName();

    @FragmentArg
    Driver driver;

    @ViewById(R.id.detail_driver_name)
    TextView driverNameView;

    @ViewById(R.id.detail_driver_pager)
    ViewPager vpPager;

    DetailDriverPagerAdapter adapterViewPager;

    @AfterViews
    void init() {
        driverNameView.setText(driver.getGivenName() + " " + driver.getFamilyName());

        adapterViewPager = new DetailDriverPagerAdapter(getChildFragmentManager(),getActivity(),driver);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(true, new ZoomOutSlideTransformer());

        vpPager.setClipToPadding(false);
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager.setPadding(0, 0, 0, 0);
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager.setPageMargin(90);

    }

}
