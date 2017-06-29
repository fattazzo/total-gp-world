package com.gmail.fattazzo.formula1world.fragments.current.races.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment;
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications.QualificationsRaceFragment;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications.QualificationsRaceFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment;
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment_;

import java.util.ArrayList;
import java.util.List;

class DetailRacePagerAdapter extends FragmentPagerAdapter {
    private Context context;

    private F1Race race;
    private UrlViewerFragment urlViewerFragment;
    private ResultsRaceFragment resultsRaceFragment;
    private QualificationsRaceFragment qualificationsRaceFragment;

    private List<Fragment> detailFragments = new ArrayList<>();

    DetailRacePagerAdapter(FragmentManager fragmentManager, Context context, F1Race race, boolean hasResults,boolean hasQualifications) {
        super(fragmentManager);
        this.context = context;
        this.race = race;

        initSection(hasResults,hasQualifications);
    }

    private void initSection(boolean hasResults,boolean hasQualifications) {
        if (hasResults) {
            detailFragments.add(getResultsRaceFragment());
        }
        if(hasQualifications) {
            detailFragments.add(getQualificationsRaceFragment());
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

    private QualificationsRaceFragment getQualificationsRaceFragment() {
        if (qualificationsRaceFragment == null) {
            qualificationsRaceFragment = QualificationsRaceFragment_.newInstance(race);
        }
        return qualificationsRaceFragment;
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