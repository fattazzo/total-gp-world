package com.gmail.fattazzo.formula1world.fragments.current.drivers

import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ListView
import android.widget.RelativeLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.DetailDriverFragment_
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 15/04/17
 */
@OptionsMenu(R.menu.drivers)
@EFragment(R.layout.fragment_swipe_listview)
open class CurrentDriversFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var driversListAdapter: DriversListAdapter

    @ViewById(R.id.list_view)
    lateinit internal var listView: ListView

    @ViewById(R.id.swipe_refresh_layout)
    lateinit internal var swipeRefreshLayout: SwipeRefreshLayout

    @ViewById(R.id.details_fragment_container)
    @JvmField internal var detailsDriverFragmentContainer: RelativeLayout? = null

    @AfterViews
    internal fun init() {
        retainInstance = true

        listView!!.adapter = driversListAdapter
        swipeRefreshLayout!!.setOnRefreshListener(this)
        if (driversListAdapter!!.isEmpty) {
            loadDrivers()
        }
    }

    @Background
    open fun loadDrivers() {
        var result: List<F1Driver>? = null
        try {
            startLoad()
            result = dataService!!.loadDrivers()
        } finally {
            updateUI(result)
        }
    }

    @UiThread
    internal open fun startLoad() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout!!.isRefreshing = true
        }
    }

    @UiThread
    internal open fun updateUI(result: List<F1Driver>?) {
        try {
            driversListAdapter!!.clearItems()
            driversListAdapter!!.setDrivers(result!!)
            driversListAdapter!!.notifyDataSetChanged()
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
    }

    @ItemClick(R.id.list_view)
    fun itemClicked(position: Int) {
        val driver = driversListAdapter!!.getItem(position)

        val container: Int
        if (detailsDriverFragmentContainer == null) {
            container = R.id.container
        } else {
            container = R.id.details_fragment_container
        }
        FragmentUtils.replace(activity, DetailDriverFragment_.builder().driver(driver).build(), container)
    }

    // ----------------------------FILTERS ------------------------------------

    @OptionsItem
    internal fun order_by_numberSelected() {
        sortDrivers(DriversListAdapter.SortType.NUMBER)
    }

    @OptionsItem
    internal fun order_by_nameSelected() {
        sortDrivers(DriversListAdapter.SortType.NAME)
    }

    @OptionsItem
    internal fun order_by_nationalitySelected() {
        sortDrivers(DriversListAdapter.SortType.NATIONALITY)
    }

    @OptionsItem
    internal fun order_by_date_of_birthSelected() {
        sortDrivers(DriversListAdapter.SortType.DATE_OF_BIRTH)
    }

    private fun sortDrivers(sortType: DriversListAdapter.SortType) {
        driversListAdapter!!.setSortType(sortType)
        driversListAdapter!!.sortDrivers()
        driversListAdapter!!.notifyDataSetChanged()
    }

    // ------------------------------------------------------------------------

    override fun onRefresh() {
        dataService!!.clearDriversCache()
        loadDrivers()
    }

    companion object {

        val TAG = CurrentDriversFragment::class.java.simpleName
    }
}
