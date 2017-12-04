package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics

import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.View
import android.widget.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.DriverSpinnerModel
import com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions.DriversSpinnerAdapter
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ThemeUtils
import com.gmail.fattazzo.formula1world.view.chart.marker.F1MarkerLapTimeView
import org.androidannotations.annotations.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.map.HashedMap
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 19/09/17
 */
@EFragment(R.layout.fragment_race_stat_chart_drivers)
abstract class AbstractRaceLapTimeFragment : Fragment() {

    @FragmentArg
    lateinit protected var race: F1Race

    @Bean
    lateinit protected var dataService: DataService

    @Bean
    lateinit protected var themeUtils: ThemeUtils

    @Bean
    lateinit protected var spinnerAdapter: DriversSpinnerAdapter

    @ViewById
    lateinit protected var drivers_spinner: Spinner

    @ViewById
    lateinit protected var listHeader: ConstraintLayout

    @Bean
    lateinit protected var adapter: LapTimeListAdapter

    @ViewById
    lateinit protected var chart: LineChart
    @ViewById
    lateinit protected var listview: ListView

    @ViewById
    lateinit protected var view_flipper: ViewFlipper

    @ViewById
    lateinit protected var refresh_progressBar: ProgressBar
    protected var textColor: Int = 0
    protected var textSize: Float = 0.toFloat()
    private var allDriversSpinnerModel: DriverSpinnerModel? = null

    @AfterInject
    protected fun init() {
        textColor = themeUtils!!.getThemeTextColor(context)
        textSize = themeUtils!!.getThemeTextSize(context, R.dimen.font_size_small)

        val f1Driver = F1Driver()
        f1Driver.driverRef = "___"
        f1Driver.givenName = getString(R.string.all_drivers)
        f1Driver.familyName = ""
        val f1Constructor = F1Constructor()
        f1Constructor.constructorRef = "___"
        allDriversSpinnerModel = DriverSpinnerModel(f1Driver, f1Constructor)
    }

    @AfterViews
    protected fun initViews() {
        val driversSpinner = TreeSet(Comparator<DriverSpinnerModel> { o1, o2 -> o1.driver!!.fullName.compareTo(o2.driver!!.fullName) })

        val results = dataService!!.loadRaceResult(race!!)
        if (dataService!!.hasLocalLapsData(race!!)) {
            driversSpinner.add(allDriversSpinnerModel)
        }
        for (result in results) {
            driversSpinner.add(DriverSpinnerModel(result.driver, result.constructor))
        }

        spinnerAdapter!!.setDrivers(driversSpinner)
        drivers_spinner!!.adapter = spinnerAdapter

        configureChart(chart)

        (listHeader!!.findViewById<TextView>(R.id.driverName)).text = getString(R.string.drivers)
        (listHeader!!.findViewById<TextView>(R.id.lap)).text = getString(R.string.lap)
        (listHeader!!.findViewById<TextView>(R.id.position)).text = getString(R.string.stats_pos)
        (listHeader!!.findViewById<TextView>(R.id.time)).text = getString(R.string.time2)
    }

    @CheckedChange
    protected fun chartListviewSwitch(chartListviewSwitch: Switch) {
        if (chartListviewSwitch.isChecked) {
            view_flipper!!.showNext()
        } else {
            view_flipper!!.showPrevious()
        }
        view_flipper!!.invalidate()
    }

    @Click
    protected fun add_button() {
        val driversToAdd = ArrayList<DriverSpinnerModel>()
        if (drivers_spinner!!.selectedItem == allDriversSpinnerModel) {
            driversToAdd.addAll((drivers_spinner!!.adapter as DriversSpinnerAdapter).items!!)
        } else {
            driversToAdd.add(drivers_spinner!!.selectedItem as DriverSpinnerModel)
        }

        val driversForChart = ArrayList<DriverSpinnerModel>()
        for (driverSelected in driversToAdd) {
            if (driverSelected.driver!!.driverRef == "___") {
                // skip "all drivers" entry
                continue
            }

            val dataSetDriver = if (chart!!.lineData != null) chart!!.lineData.getDataSetByLabel(driverSelected.driver!!.fullName, false) else null
            if (dataSetDriver != null) {
                // dataset fro driver selected already added
                continue
            }

            driversForChart.add(driverSelected)
        }
        loadDriverLapTimes(race, driversForChart)
    }

    @Click
    protected fun remove_button() {
        val driverSelected = drivers_spinner!!.selectedItem as DriverSpinnerModel

        try {
            chart!!.marker = null

            if (driverSelected.driver!!.driverRef == "___") {
                chart!!.clear()
                adapter!!.clearItems()
                adapter!!.notifyDataSetChanged()
            } else {
                val dataSetByLabel = if (chart!!.lineData != null) chart!!.lineData.getDataSetByLabel(driverSelected.driver!!.fullName, false) else null
                if (dataSetByLabel != null) {
                    chart!!.lineData.removeDataSet(dataSetByLabel)
                    chart!!.lineData.notifyDataChanged()
                    chart!!.notifyDataSetChanged()

                    adapter!!.removeItems(driverSelected.driver!!)
                    adapter!!.notifyDataSetChanged()
                }
            }
        } finally {
            chart!!.animateX(1000, Easing.EasingOption.EaseInSine)

            Handler().postDelayed({
                if (chart != null) {
                    chart!!.marker = F1MarkerLapTimeView(context)
                }
            }, 2000)
        }
    }

    @Background
    protected open fun loadDriverLapTimes(race: F1Race?, driversModel: List<DriverSpinnerModel>) {
        startAdd()

        val lapsMap = HashedMap<F1Driver, List<F1LapTime>>()
        for (model in driversModel) {
            val driverLapTimes = dataService!!.loadLaps(race!!, model.driver!!)
            if (CollectionUtils.isNotEmpty(driverLapTimes)) {
                lapsMap.put(model.driver, driverLapTimes)
            }
        }

        updateChart(lapsMap)
        updateListView(lapsMap)
    }

    @UiThread
    protected open fun startAdd() {
        if (refresh_progressBar != null) {
            refresh_progressBar!!.visibility = View.VISIBLE
        }
    }

    @UiThread
    protected open fun updateListView(laps: Map<F1Driver, List<F1LapTime>>) {
        val lapsListView = ArrayList<F1LapTime>()
        for ((key, value) in laps) {
            for (lapTime in value) {
                lapTime.driver = key
                lapsListView.add(lapTime)
            }
        }

        sortLapTimesForList(lapsListView)

        adapter!!.setLapTimes(lapsListView)
        listview!!.adapter = adapter
    }

    @UiThread
    protected open fun updateChart(laps: Map<F1Driver, List<F1LapTime>>) {
        try {
            for ((key, value) in laps) {
                val dataSet = buildDataSet(value, key)
                if (chart == null) {
                    return
                }
                if (chart!!.lineData == null) {
                    chart!!.data = LineData(dataSet)
                } else {
                    chart!!.lineData.addDataSet(dataSet)
                }
            }
            chart!!.notifyDataSetChanged()
            chart!!.animateX(1000, Easing.EasingOption.EaseInSine)
        } finally {
            if (refresh_progressBar != null) {
                refresh_progressBar!!.visibility = View.INVISIBLE
            }
        }
    }

    protected abstract fun configureChart(chart: LineChart?)

    protected abstract fun buildDataSet(results: List<F1LapTime>, driver: F1Driver): LineDataSet

    protected abstract fun sortLapTimesForList(lapsListView: List<F1LapTime>)
}
