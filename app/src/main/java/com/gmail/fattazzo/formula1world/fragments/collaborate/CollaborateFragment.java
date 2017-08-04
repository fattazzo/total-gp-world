package com.gmail.fattazzo.formula1world.fragments.collaborate;

import android.support.v4.app.Fragment;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;

import org.androidannotations.annotations.EFragment;

import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 04/08/17
 */
@EFragment(R.layout.fragment_collaborate)
public class CollaborateFragment extends Fragment {

    public static final String TAG = CollaborateFragment.class.getSimpleName();

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
