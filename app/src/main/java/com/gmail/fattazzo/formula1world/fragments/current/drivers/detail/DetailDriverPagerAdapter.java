package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.ProgressDriverFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking.RankingDriverFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.ranking.RankingDriverFragment_;

class DetailDriverPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private F1Driver driver;
    private UrlViewerFragment urlViewerFragment;
    private ProgressDriverFragment progressDriverFragment;
    private RankingDriverFragment rankingDriverFragment;

    DetailDriverPagerAdapter(FragmentManager fragmentManager, Context context, F1Driver driver) {
        super(fragmentManager);
        this.context = context;
        this.driver = driver;
    }

    public UrlViewerFragment getUrlViewerFragment() {
        if (urlViewerFragment == null) {
            urlViewerFragment = UrlViewerFragment_.newInstance(driver.url);
        }
        return urlViewerFragment;
    }

    public ProgressDriverFragment getProgressDriverFragment() {
        if (progressDriverFragment == null) {
            progressDriverFragment = ProgressDriverFragment_.newInstance(driver);
        }
        return progressDriverFragment;
    }

    public RankingDriverFragment getRankingDriverFragment() {
        if (rankingDriverFragment == null) {
            rankingDriverFragment = RankingDriverFragment_.newInstance(driver);
        }
        return rankingDriverFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return getProgressDriverFragment();
            case 1:
                return getRankingDriverFragment();
            case 2:
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
                return context.getResources().getString(R.string.detail_driver_ranking_tab_title);
            case 2:
                return context.getResources().getString(R.string.info_fragment_title);
            default:
                return null;
        }
    }

}