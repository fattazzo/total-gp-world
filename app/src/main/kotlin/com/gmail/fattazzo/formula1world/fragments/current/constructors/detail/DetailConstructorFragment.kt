package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail

import android.support.v4.view.ViewPager
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.config.Config
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.constructors.CurrentConstructorsFragment
import com.gmail.fattazzo.formula1world.fragments.current.constructors.CurrentConstructorsFragment_
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
open class DetailConstructorFragment : BaseFragment() {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @FragmentArg
    lateinit internal var constructor: F1Constructor

    @ViewById(R.id.title)
    lateinit internal var constructorNameView: TextView

    @ViewById(R.id.view_pager)
    lateinit internal var vpPager: ViewPager

    lateinit internal var adapterViewPager: DetailConstructorPagerAdapter

    @AfterViews
    internal fun init() {
        constructorNameView!!.text = constructor!!.name

        adapterViewPager = DetailConstructorPagerAdapter(childFragmentManager, activity, constructor!!)
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

        if (fragmentManager.findFragmentByTag(CurrentConstructorsFragment.TAG) != null) {
            FragmentUtils.replace(activity, HomeFragment_())
        } else {
            FragmentUtils.replace(activity, CurrentConstructorsFragment_())
        }
    }

    companion object {

        val TAG = DetailConstructorFragment::class.java.simpleName
    }
}
