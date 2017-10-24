package com.gmail.fattazzo.formula1world.settings

import com.gmail.fattazzo.formula1world.R

import org.androidannotations.annotations.sharedpreferences.DefaultInt
import org.androidannotations.annotations.sharedpreferences.DefaultRes
import org.androidannotations.annotations.sharedpreferences.DefaultString
import org.androidannotations.annotations.sharedpreferences.SharedPref

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
interface ApplicationPreference {

    @DefaultRes(R.string.default_appTheme)
    fun appTheme(): String

    @DefaultRes(R.string.default_fragmentTransitionAnimation)
    fun fragmentTransitionAnimation(): String

    @DefaultRes(R.string.default_pagerTransitionAnimation)
    fun pagerTransitionAnimation(): String

    @DefaultInt(0)
    fun lastVersionDBFilesImported(): Int

    @DefaultString("3")
    fun statisticsChartColorTheme(): String

    @DefaultString("")
    fun newsLanguage(): String
}