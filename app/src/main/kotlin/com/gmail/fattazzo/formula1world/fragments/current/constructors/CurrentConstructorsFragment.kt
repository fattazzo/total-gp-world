package com.gmail.fattazzo.formula1world.fragments.current.constructors

import android.support.v4.widget.SwipeRefreshLayout
import android.widget.ListView
import android.widget.RelativeLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.DetailConstructorFragment_
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.FragmentUtils
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 26/05/17
 */
@EFragment(R.layout.fragment_swipe_listview)
open class CurrentConstructorsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var constructorsListAdapter: ConstructorsListAdapter

    @ViewById(R.id.list_view)
    lateinit internal var listView: ListView

    @ViewById(R.id.swipe_refresh_layout)
    @JvmField internal var swipeRefreshLayout: SwipeRefreshLayout? = null

    @ViewById(R.id.details_fragment_container)
    @JvmField internal var detailsConstructorFragmentContainer: RelativeLayout? = null

    @AfterViews
    internal fun init() {
        listView.adapter = constructorsListAdapter
        swipeRefreshLayout!!.setOnRefreshListener(this)
        if (constructorsListAdapter.isEmpty) {
            loadConstructors()
        }
    }

    @Background
    open fun loadConstructors() {
        var result: List<F1Constructor>? = null
        try {
            startLoad()
            result = dataService.loadConstructors()
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
    internal open fun updateUI(result: List<F1Constructor>?) {
        try {
            constructorsListAdapter.clearItems()
            constructorsListAdapter.setConstructors(result!!)
            constructorsListAdapter.notifyDataSetChanged()
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
    }

    @ItemClick(R.id.list_view)
    fun itemClicked(position: Int) {
        val constructor = constructorsListAdapter.getItem(position)

        val container: Int = if (detailsConstructorFragmentContainer == null) {
            R.id.container
        } else {
            R.id.details_fragment_container
        }

        FragmentUtils.replace(activity, DetailConstructorFragment_.builder().constructor(constructor).build(), container)
    }

    override fun onRefresh() {
        dataService.clearConstructorsCache()
        loadConstructors()
    }

    companion object {

        val TAG = CurrentConstructorsFragment::class.java.simpleName
    }
}
