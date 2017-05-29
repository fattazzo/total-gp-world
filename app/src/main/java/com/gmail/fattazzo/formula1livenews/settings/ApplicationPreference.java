package com.gmail.fattazzo.formula1livenews.settings;

import com.gmail.fattazzo.formula1livenews.R;

import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface ApplicationPreference {

    @DefaultRes(R.string.default_fragmentTransitionAnimation)
    String fragmentTransitionAnimation();

    @DefaultRes(R.string.default_pagerTransitionAnimation)
    String pagerTransitionAnimation();

}