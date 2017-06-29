package com.gmail.fattazzo.formula1world.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_webview_layout)
public class UrlViewerFragment extends Fragment implements ITitledFragment {

    @Bean
    Utils utils;

    @FragmentArg
    String url;

    @ViewById(R.id.webview)
    WebView infoWebView;

    @ViewById
    ProgressBar progressBar;

    public static UrlViewerFragment newInstance(String url) {
        UrlViewerFragment urlViewerFragment = new UrlViewerFragment_();
        Bundle args = new Bundle();
        args.putString("url", url);
        urlViewerFragment.setArguments(args);
        return urlViewerFragment;
    }

    @AfterViews
    void init() {
        progressBar.setVisibility(View.VISIBLE);
        infoWebView.getSettings().setJavaScriptEnabled(true);
        infoWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        infoWebView.loadUrl(utils.getLocalizedLink(url));
    }

    @Override
    public int getTitleResId() {
        return R.string.info_fragment_title;
    }
}