package com.gmail.fattazzo.formula1world.activity.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;

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