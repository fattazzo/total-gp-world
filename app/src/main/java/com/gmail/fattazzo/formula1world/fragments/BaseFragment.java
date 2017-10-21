package com.gmail.fattazzo.formula1world.fragments;

import android.support.v4.app.Fragment;

import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

/**
 * @author fattazzo
 *         <p/>
 *         date: 19/10/17
 */
public class BaseFragment extends Fragment {

    public void backPressed() {
        FragmentUtils.replace(getActivity(), new HomeFragment_());
    }
}
