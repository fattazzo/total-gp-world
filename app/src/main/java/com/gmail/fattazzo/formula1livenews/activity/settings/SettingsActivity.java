package com.gmail.fattazzo.formula1livenews.activity.settings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

@PreferenceScreen(R.xml.settings)
@EActivity
public class SettingsActivity extends PreferenceActivity {

    public static final int RESULT_CODE_THEME_UPDATED = 2;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(preferenceManager.getAppTheme());
        super.onCreate(savedInstanceState);
    }

    @PreferenceChange
    void key_appTheme() {
        setResult(RESULT_CODE_THEME_UPDATED);
    }
}