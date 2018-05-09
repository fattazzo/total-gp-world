/*
 * Project: total-gp-world
 * File: DBUtils.kt
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

package com.gmail.fattazzo.formula1world.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.gmail.fattazzo.formula1world.activity.dbupdate.DBUpdateActivity_
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import org.androidannotations.annotations.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * @author fattazzo
 *         <p/>
 *         date: 27/11/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class DBUtils {

    @RootContext
    lateinit internal var context: Context

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    private var props: Properties? = null

    fun downloadDBIfNeeded() {
        if (preferenceManager.isDBImportEnabled()) {
            downloadDBProperties()
        }
    }

    fun downloadDB() {
        downloadDBProperties()
    }

    @Background
    open fun downloadDBProperties() {
        try {
            val url = URL(DB_PROPS_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                props = null
                return
            }

            val input = connection.inputStream

            props = Properties()
            props!!.load(input)
        } catch (e: Exception) {
            props = null
        }

        checkDBVersions()
    }

    @UiThread
    open fun checkDBVersions() {
        if(props == null) {
            return
        }

        val currentDBVersion = preferenceManager.getLastVersionDBFilesImported()
        val onlineDBVersion : Int = props!!.getProperty(VERSION).toInt()

        val onlineMinAppVersion = props!!.getProperty(MIN_APP_VERSION,"0.0")
        val currentVersione = getCurrentAppVersion()

        if (onlineDBVersion > currentDBVersion) {
            DBUpdateActivity_.intent(context).flags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .versionToUpdate(onlineDBVersion)
                    .onlineMinAppVersion(onlineMinAppVersion)
                    .currentVersione(currentVersione).start()
        }
    }

    private fun getCurrentAppVersion(): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "0.0"
        }
    }

    companion object {
        val DB_PROPS_URL = "https://raw.githubusercontent.com/fattazzo/total-gp-world/master/db/db.properties"

        val VERSION = "version"
        val MIN_APP_VERSION = "appMinVersionCode"
    }
}