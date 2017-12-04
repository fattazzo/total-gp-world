package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail

import android.support.v4.view.ViewPager
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.config.Config
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment_
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*


/**
 * @author fattazzo
 *
 *
 * date: 19/04/17
 */
@EFragment(R.layout.view_pager_fragment)
open class DetailDriverFragment : BaseFragment() {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @FragmentArg
    lateinit internal var driver: F1Driver

    @ViewById(R.id.title)
    lateinit internal var driverNameView: TextView

    @ViewById(R.id.view_pager)
    lateinit internal var vpPager: ViewPager

    lateinit internal var adapterViewPager: DetailDriverPagerAdapter

    @AfterViews
    internal fun init() {
        driverNameView!!.text = driver!!.fullName

        adapterViewPager = DetailDriverPagerAdapter(childFragmentManager, activity, driver!!)
        vpPager!!.adapter = adapterViewPager
        if (Config.animationEnabled) {
            vpPager!!.setPageTransformer(true, preferenceManager!!.pagerTansactionAnimation)
        }

        vpPager!!.clipToPadding = false
        // set padding manually, the more you set the padding the more you see of prev & next page
        vpPager!!.setPadding(0, 0, 0, 0)
        // sets a margin b/w individual pages to ensure that there is a gap b/w them
        vpPager!!.pageMargin = 90

    }

    override fun backPressed() {
        val fragmentManager = activity.supportFragmentManager

        if (fragmentManager.findFragmentByTag(CurrentDriversFragment.TAG) != null) {
            FragmentUtils.replace(activity, HomeFragment_())
        } else {
            FragmentUtils.replace(activity, CurrentDriversFragment_())
        }
    }

    companion object {

        val TAG = DetailDriverFragment::class.java.simpleName
    }
}
