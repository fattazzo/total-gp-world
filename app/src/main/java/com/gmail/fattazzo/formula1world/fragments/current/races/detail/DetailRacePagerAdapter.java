package com.gmail.fattazzo.formula1world.fragments.current.races.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment_;
import com.gmail.fattazzo.formula1world.service.IDataService;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

class DetailRacePagerAdapter extends FragmentPagerAdapter {
    private Context context;

    private F1Race race;
    private UrlViewerFragment urlViewerFragment;
    private ResultsRaceFragment resultsRaceFragment;

    private List<Fragment> detailFragments = new ArrayList<>();

    DetailRacePagerAdapter(FragmentManager fragmentManager, Context context, F1Race race, boolean hasResults) {
        super(fragmentManager);
        this.context = context;
        this.race = race;

        initSection(hasResults);
    }

    private void initSection(boolean hasResults) {
        if (hasResults) {
            detailFragments.add(getResultsRaceFragment());
        }
        detailFragments.add(getUrlViewerFragment());
    }

    private UrlViewerFragment getUrlViewerFragment() {
        if (urlViewerFragment == null) {
            urlViewerFragment = UrlViewerFragment_.newInstance(race.circuit.url);
        }
        return urlViewerFragment;
    }

    private ResultsRaceFragment getResultsRaceFragment() {
        if (resultsRaceFragment == null) {
            resultsRaceFragment = ResultsRaceFragment_.newInstance(race);
        }
        return resultsRaceFragment;
    }

    @Override
    public int getCount() {
        return detailFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return detailFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(((ITitledFragment) detailFragments.get(position)).getTitleResId());
    }

}