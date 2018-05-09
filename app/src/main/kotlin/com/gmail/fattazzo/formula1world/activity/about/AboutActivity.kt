/*
 * Project: total-gp-world
 * File: AboutActivity.kt
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

package com.gmail.fattazzo.formula1world.activity.about

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.json.Info
import com.gmail.fattazzo.formula1world.service.ProjectsInfoService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 26/06/17
 */
@EActivity(R.layout.activity_about)
open class AboutActivity : Activity() {

    @Bean
    lateinit var projectsInfoService: ProjectsInfoService

    @Bean
    lateinit var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit var utils: Utils

    @ViewById
    internal lateinit var appView: AppView

    @ViewById
    internal lateinit var authorView: AuthorView

    @ViewById
    internal lateinit var faqView: FaqView

    @ViewById
    internal lateinit var otherAppsView: OtherAppsView

    @ViewById
    internal lateinit var attributionView: AttributionView

    var expand_space_indicator: View? = null

    @JvmField
    @InstanceState
    var info: Info? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(preferenceManager.appTheme)
        super.onCreate(savedInstanceState)
    }

    @AfterViews
    internal fun init() {
        expand_space_indicator = findViewById(R.id.expand_space_indicator)

        if (info == null) {
            loadProjectsInfo()
        } else {
            bindInfo()
        }
    }

    @Background
    open fun loadProjectsInfo() {
        info = projectsInfoService.loadInfo()
        bindInfo()
    }

    @UiThread
    open fun bindInfo() {
        appView.bind(info?.getAppById(this@AboutActivity.applicationContext.packageName), Locale.getDefault().language)
        authorView.bind(info?.author)
        otherAppsView.bind(info?.apps.orEmpty())

        if (expand_space_indicator != null) {
            faqView.toggleFaqPanel()
            attributionView.toggleAttributionPanel()
        }
    }

}
