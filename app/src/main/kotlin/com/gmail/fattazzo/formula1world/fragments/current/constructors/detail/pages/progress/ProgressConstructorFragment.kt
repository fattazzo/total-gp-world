package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail.pages.progress

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.domain.F1Result
import com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress.RaceResultsItemView_
import com.gmail.fattazzo.formula1world.service.DataService
import org.androidannotations.annotations.*
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap

@EFragment(R.layout.fragment_driver_progress)
open class ProgressConstructorFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @FragmentArg
    lateinit internal var constructor: F1Constructor

    @ViewById(R.id.race_result_progress_main_layout)
    lateinit internal var mainLayout: LinearLayout

    @ViewById
    lateinit internal var swipe_refresh_layout: SwipeRefreshLayout

    @Bean
    lateinit internal var dataService: DataService

    private var raceResults: List<F1Result>? = null

    @AfterViews
    internal fun init() {
        mainLayout!!.removeAllViews()

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
        raceResults = dataService!!.loadConstructorRacesResult(constructor!!)
        updateUI()
    }

    @UiThread
    internal open fun updateUI() {
        try {
            if (mainLayout != null) {
                mainLayout!!.removeAllViews()

                // 1 table for each driver
                val resultsMap = ArrayListValuedHashMap<String, F1Result>()

                for (result in raceResults!!) {
                    val driverName = if (result.driver != null) result.driver!!.fullName else ""
                    resultsMap.put(driverName, result)
                }

                for (driverName in resultsMap.keySet()) {

                    val tableLayout = activity.layoutInflater.inflate(R.layout.race_result_progress_table_layout, null).findViewById(R.id.progress_driver_table_layout) as TableLayout

                    // Title: driver name
                    val driverTextView = TextView(context)
                    driverTextView.text = driverName

                    if (Build.VERSION.SDK_INT < 23) {
                        driverTextView.setTextAppearance(context, R.style.TextAppearance_Medium)
                    } else {
                        driverTextView.setTextAppearance(R.style.TextAppearance_Medium)
                    }

                    tableLayout.addView(driverTextView, 0, TableLayout.LayoutParams())
                    val mlp = driverTextView.layoutParams as ViewGroup.MarginLayoutParams
                    mlp.setMargins(0, 50, 0, 0)
                    driverTextView.setTypeface(null, Typeface.BOLD)

                    var rowNumber = 1
                    for (result in resultsMap.get(driverName)) {
                        val row = RaceResultsItemView_.build(activity, result, rowNumber)
                        tableLayout.addView(row, TableLayout.LayoutParams())
                        rowNumber++
                    }

                    mainLayout!!.addView(tableLayout)
                }

                //tableLayout.getChildAt(0).setVisibility(View.INVISIBLE);
            }
        } finally {
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout!!.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        dataService!!.clearConstructorRaceResultsCache(constructor!!)
        load()
    }

    companion object {

        fun newInstance(constructor: F1Constructor): ProgressConstructorFragment {
            val progressFragment = ProgressConstructorFragment_()
            val args = Bundle()
            args.putSerializable("constructor", constructor)
            progressFragment.arguments = args
            return progressFragment
        }
    }
}