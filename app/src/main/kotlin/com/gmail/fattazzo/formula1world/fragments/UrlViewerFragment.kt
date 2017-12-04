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