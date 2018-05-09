/*
 * Project: total-gp-world
 * File: AppView.kt
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

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.json.App
import com.gmail.fattazzo.formula1world.utils.AppRater
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/05/18
 */
@EViewGroup(R.layout.view_app)
open class AppView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @Bean
    lateinit var utils: Utils

    @ViewById
    internal lateinit var titleTV: TextView

    @ViewById
    internal lateinit var descriptionTV: TextView

    @ViewById
    internal lateinit var wikiButton: Button

    var app: App? = null

    fun bind(app: App?, lang: String) {
        this.app = app

        val i18n = app?.getI18n(lang) ?: app?.getI18n()

        titleTV.text = i18n?.title.orEmpty()
        descriptionTV.text = i18n?.description.orEmpty()

        wikiButton.visibility = if (app?.wiki.isNullOrBlank()) View.INVISIBLE else View.VISIBLE
    }

    @Click
    fun rateButtonClicked() {
        AppRater.rate(context!!, forceOpen = true)
    }

    @Click
    fun wikiButtonClicked() {
        utils.openLink(app?.wiki)
    }
}