/*
 * Project: total-gp-world
 * File: SettingsActivity.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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