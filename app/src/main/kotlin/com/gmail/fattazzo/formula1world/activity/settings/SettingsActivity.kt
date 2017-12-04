package com.gmail.fattazzo.formula1world.activity.settings

import android.os.Bundle
import android.preference.PreferenceActivity
import com.activeandroid.ActiveAndroid
import com.activeandroid.util.ReflectionUtils
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.DBUtils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.PreferenceChange
import org.androidannotations.annotations.PreferenceScreen

@PreferenceScreen(R.xml.settings)
@EActivity
open class SettingsActivity : PreferenceActivity() {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var dbutils: DBUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(preferenceManager.appTheme)
        super.onCreate(savedInstanceState)
    }

    @PreferenceChange(R.string.key_appTheme)
    fun preferenceChangeKeyAppTheme() {
        setResult(RESULT_CODE_THEME_UPDATED)
    }

    @PreferenceChange(R.string.key_dbImportEnabled)
    fun preferenceChangeKeyDBImportEnabled(newValue: Any) {
        if(newValue == true) {
            dbutils.downloadDB()
        } else {
            ActiveAndroid.dispose()

            val dbName = ReflectionUtils.getMetaData<String>(applicationContext, "AA_DB_NAME")
            deleteDatabase(dbName)

            ActiveAndroid.initialize(this)

            preferenceManager.prefs.sharedPreferences.edit().putInt("lastVersionDBFilesImported",0).commit()
        }
    }

    companion object {

        val RESULT_CODE_THEME_UPDATED = 2
    }
}