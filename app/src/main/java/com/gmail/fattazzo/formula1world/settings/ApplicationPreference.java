package com.gmail.fattazzo.formula1world.settings;

import com.gmail.fattazzo.formula1world.R;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface ApplicationPreference {

    @DefaultRes(R.string.default_appTheme)
    String appTheme();

    @DefaultRes(R.string.default_fragmentTransitionAnimation)
    String fragmentTransitionAnimation();

    @DefaultRes(R.string.default_pagerTransitionAnimation)
    String pagerTransitionAnimation();

    @DefaultInt(0)
    int lastVersionDBFilesImported();
}