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
import android.content.Context
import android.os.Bundle
import com.gmail.fattazzo.aboutlibrary.builder.AboutButtonBuilder
import com.gmail.fattazzo.aboutlibrary.builder.AboutViewBuilder
import com.gmail.fattazzo.aboutlibrary.builder.Action
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.config.Config
import com.gmail.fattazzo.formula1world.utils.IssueReporter
import org.androidannotations.annotations.EActivity
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 26/06/17
 */
@EActivity
open class AboutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reportIssueButton = AboutButtonBuilder()
                .withText(R.string.report_error)
                .withDrawable(R.drawable.bug)
                .withFlatStyle(true)
                .withAction(object : Action {
                    override fun run(context: Context) {
                        IssueReporter.openReportIssue(context, null, null, true)
                    }
                })
                .withTextColor(android.R.color.holo_red_dark)

        val aboutViewBuilder = AboutViewBuilder()
                .withInfoUrl(Config.PROJECTS_INFO_URL)
                .withAppId(this@AboutActivity.applicationContext.packageName)
                .withFlatStyleButtons(true)
                .withLang(Locale.getDefault().language)
                .withAdditionalAppButtons(listOf(reportIssueButton))
                .withExcludeThisAppFromProjects(true)

        setContentView(aboutViewBuilder.build(this))
    }
}
