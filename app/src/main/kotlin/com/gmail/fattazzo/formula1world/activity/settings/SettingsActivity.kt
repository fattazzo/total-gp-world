package com.gmail.fattazzo.formula1world.activity.settings

import android.os.Bundle
import android.preference.PreferenceActivity

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.PreferenceChange
import org.androidannotations.annotations.PreferenceScreen

@PreferenceScreen(R.xml.settings)
@EActivity
open class SettingsActivity : PreferenceActivity() {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(preferenceManager!!.appTheme)
        super.onCreate(savedInstanceState)
    }

    @PreferenceChange(R.string.key_appTheme)
    fun preferenceChangeKeyAppTheme() {
        setResult(RESULT_CODE_THEME_UPDATED)
    }

    companion object {

        val RESULT_CODE_THEME_UPDATED = 2
    }
}