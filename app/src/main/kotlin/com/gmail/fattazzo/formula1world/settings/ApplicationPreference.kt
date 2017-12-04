package com.gmail.fattazzo.formula1world.settings

import com.gmail.fattazzo.formula1world.R
import org.androidannotations.annotations.sharedpreferences.*

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
interface ApplicationPreference {

    @DefaultRes(R.string.default_appTheme)
    fun appTheme(): String

    @DefaultRes(R.string.default_fragmentTransitionAnimation)
    fun fragmentTransitionAnimation(): String

    @DefaultRes(R.string.default_pagerTransitionAnimation)
    fun pagerTransitionAnimation(): String

    @DefaultString("3")
    fun statisticsChartColorTheme(): String

    @DefaultString("")
    fun newsLanguage(): String

    // DB Preferences
    @DefaultInt(0)
    fun lastVersionDBFilesImported(): Int

    @DefaultBoolean(true)
    fun dbImportEnabled(): Boolean
}