package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.TableLayout
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1Result
import com.gmail.fattazzo.formula1world.service.DataService
import org.androidannotations.annotations.*

@EFragment(R.layout.fragment_driver_progress)
open class ProgressDriverFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @FragmentArg
    lateinit internal var driver: F1Driver

    @ViewById(R.id.progress_driver_table_layout)
    lateinit internal var tableLayout: TableLayout

    @ViewById
    lateinit internal var swipe_refresh_layout: SwipeRefreshLayout

    @Bean
    lateinit internal var dataService: DataService

    private var raceResults: List<F1Result>? = null

    @AfterViews
    internal fun init() {
        tableLayout!!.removeViews(1, tableLayout!!.childCount - 1)

        swipe_refresh_layout!!.setOnRefreshListener(this)

        load()
    }

    @UiThread
    internal open fun startLoad() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout!!.isRefreshing = true
        }
    }

    @Background
    internal open fun load() {
        startLoad()
        raceResults = dataService!!.loadDriverRacesResult(driver!!)
        updateUI()
    }

    @UiThread
    internal open fun updateUI() {
        try {
            if (tableLayout != null) {
                tableLayout!!.removeViews(1, tableLayout!!.childCount - 1)

                var rowNumber = 1
                for (result in raceResults!!) {
                    val row = RaceResultsItemView_.build(activity, result, rowNumber)
                    tableLayout!!.addView(row, TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT))
                    rowNumber++
                }
            }
        } finally {
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout!!.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        dataService!!.clearDriverRaceResultsCache(driver!!)
        load()
    }

    companion object {

        fun newInstance(driver: F1Driver): ProgressDriverFragment {
            val progressFragment = ProgressDriverFragment_()
            val args = Bundle()
            args.putSerializable("driver", driver)
            progressFragment.arguments = args
            return progressFragment
        }
    }
}