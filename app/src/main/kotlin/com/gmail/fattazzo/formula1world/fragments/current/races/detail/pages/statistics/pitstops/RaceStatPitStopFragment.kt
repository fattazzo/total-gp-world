package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops

import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.TableLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1PitStop
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopDriverComparator
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopDurationComparator
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator.F1PitStopTimeComparator
import com.gmail.fattazzo.formula1world.service.DataService
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 03/07/17
 */
@EFragment(R.layout.fragment_race_stat_pitstops)
open class RaceStatPitStopFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @FragmentArg
    lateinit internal var race: F1Race

    @Bean
    lateinit internal var dataService: DataService

    @ViewById(R.id.stat_pitstops_table_layout)
    lateinit internal var tableLayout: TableLayout

    @ViewById(R.id.swipe_refresh_layout)
    lateinit internal var swipeRefreshLayout: SwipeRefreshLayout

    private var pitStops: List<F1PitStop>? = null

    private var pitStopsComparator: Comparator<F1PitStop> = F1PitStopTimeComparator()

    @AfterViews
    internal fun init() {
        tableLayout!!.removeViews(1, tableLayout!!.childCount - 1)

        swipeRefreshLayout!!.setOnRefreshListener(this)

        load()
    }

    @UiThread
    internal open fun startLoad() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout!!.isRefreshing = true
        }
    }

    @Background
    internal open fun load() {
        startLoad()
        pitStops = dataService!!.loadPitStops(race!!)
        Collections.sort(pitStops!!, pitStopsComparator)
        updateUI()
    }

    @UiThread
    internal open fun updateUI() {
        try {
            if (tableLayout != null) {
                tableLayout!!.removeViews(1, tableLayout!!.childCount - 1)

                var rowNumber = 1
                for (pit in pitStops!!) {
                    val row = RaceStatPitStopItemView_.build(activity, pit, rowNumber)
                    tableLayout!!.addView(row, TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT))
                    rowNumber++
                }
            }
        } finally {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        dataService!!.clearRacePitStopsCache(race!!)
        load()
    }

    @Click(R.id.order_by_driver_tv, R.id.order_by_driver_img)
    internal fun order_by_driver() {
        pitStopsComparator = F1PitStopDriverComparator()
        load()
    }

    @Click(R.id.order_by_time_tv, R.id.order_by_time_img)
    internal fun order_by_time() {
        pitStopsComparator = F1PitStopTimeComparator()
        load()
    }

    @Click(R.id.order_by_duration_tv, R.id.order_by_duration_img)
    internal fun order_by_duration() {
        pitStopsComparator = F1PitStopDurationComparator()
        load()
    }
}
