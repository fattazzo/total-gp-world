package com.gmail.fattazzo.formula1world.fragments.current.races.detail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.ITitledFragment
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment
import com.gmail.fattazzo.formula1world.fragments.UrlViewerFragment_
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications.QualificationsRaceFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results.ResultsRaceFragment
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.StatisticsRaceFragment
import java.util.*

internal class DetailRacePagerAdapter(fragmentManager: FragmentManager, private val context: Context, private val race: F1Race, hasResults: Boolean, hasQualifications: Boolean) : FragmentPagerAdapter(fragmentManager) {

    private val detailFragments = ArrayList<Fragment>()

    init {

        initSection(hasResults, hasQualifications)
    }

    private fun initSection(hasResults: Boolean, hasQualifications: Boolean) {
        if (hasResults) {
            detailFragments.add(ResultsRaceFragment.newInstance(race))
            detailFragments.add(StatisticsRaceFragment.newInstance(race))
        }
        if (hasQualifications) {
            detailFragments.add(QualificationsRaceFragment.newInstance(race))
        }
        detailFragments.add(UrlViewerFragment.newInstance(race.circuit.url!!))
    }

    override fun getCount(): Int {
        return detailFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return detailFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString((detailFragments[position] as ITitledFragment).titleResId)
    }

}