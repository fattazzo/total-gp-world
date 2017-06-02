package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.objects.Constructor;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress.ProgressConstructorFragment;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress.ProgressConstructorFragment_;

class DetailConstructorPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private Constructor constructor;

    private UrlViewerFragment urlViewerFragment;
    private ProgressConstructorFragment progressConstructorFragment;

    DetailConstructorPagerAdapter(FragmentManager fragmentManager, Context context, Constructor constructor) {
        super(fragmentManager);
        this.context = context;
        this.constructor = constructor;
    }

    private UrlViewerFragment getUrlViewerFragment() {
        if (urlViewerFragment == null) {
            urlViewerFragment = UrlViewerFragment_.newInstance(constructor.getUrl());
        }
        return urlViewerFragment;
    }

    private ProgressConstructorFragment getProgressConstructorFragment() {
        if (progressConstructorFragment == null) {
            progressConstructorFragment = ProgressConstructorFragment_.newInstance(constructor);
        }
        return progressConstructorFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getProgressConstructorFragment();
            case 1:
                return getUrlViewerFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.detail_driver_season_progress_tab_title);
            case 1:
                return context.getResources().getString(R.string.detail_driver_info_tab_title);
            default:
                return null;
        }
    }

}