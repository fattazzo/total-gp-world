/*
 * Project: total-gp-world
 * File: UrlViewerFragment.kt
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

package com.gmail.fattazzo.formula1world.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.*

@EFragment(R.layout.fragment_webview_layout)
open class UrlViewerFragment : Fragment(), ITitledFragment {

    @Bean
    lateinit internal var utils: Utils

    @FragmentArg
    lateinit internal var url: String

    @ViewById(R.id.webview)
    lateinit internal var infoWebView: WebView

    @ViewById
    @JvmField internal var progressBar: ProgressBar? = null

    override val titleResId: Int
        get() = R.string.info_fragment_title

    @AfterViews
    internal fun init() {
        progressBar!!.visibility = View.VISIBLE
        infoWebView!!.settings.javaScriptEnabled = true
        infoWebView!!.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (progressBar != null) {
                    progressBar!!.visibility = View.INVISIBLE
                }
            }
        })
        infoWebView!!.loadUrl(utils!!.getLocalizedLink(url))
    }

    companion object {

        fun newInstance(url: String): UrlViewerFragment {
            val urlViewerFragment = UrlViewerFragment_()
            val args = Bundle()
            args.putString("url", url)
            urlViewerFragment.arguments = args
            return urlViewerFragment
        }
    }
}