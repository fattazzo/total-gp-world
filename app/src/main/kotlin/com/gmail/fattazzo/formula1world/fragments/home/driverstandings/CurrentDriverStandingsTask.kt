package com.gmail.fattazzo.formula1world.fragments.home.driverstandings

import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ViewFlipper
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings
import com.gmail.fattazzo.formula1world.service.DataService
import org.androidannotations.annotations.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.ListUtils
import java.util.*

@EBean
open class CurrentDriverStandingsTask : View.OnTouchListener {

    @Bean
    lateinit internal var dataService: DataService

    @RootContext
    lateinit internal var activity: HomeActivity

    @ViewById(R.id.home_driverstandings_progressBar)
    lateinit internal var progressBar: ProgressBar

    @Bean
    lateinit internal var adapterFront: DriverStandingsListAdapter
    @Bean
    lateinit internal var adapterBack: DriverStandingsListAdapter

    private var viewFlipper: ViewFlipper? = null

    private var initialX = 0f
    private var initialY = 0f

    private var driverStandings: List<F1DriverStandings>? = null

    @ViewById(R.id.home_driver_standings_layout)
    internal fun setOneView(layout: ViewFlipper) {
        this.viewFlipper = layout
        val listViewBack = viewFlipper!!.findViewById<ListView>(R.id.standing_listview_back)
        val listViewFront = viewFlipper!!.findViewById<ListView>(R.id.standing_listview_front)

        val titleViewFront = viewFlipper!!.findViewById<TextView>(R.id.standing_title_front)
        titleViewFront.text = activity!!.getString(R.string.driverStandings)
        val titleViewBack = viewFlipper!!.findViewById<TextView>(R.id.standing_title_back)
        titleViewBack.text = activity!!.getString(R.string.driverStandings)

        listViewFront.adapter = adapterFront
        listViewFront.setOnTouchListener(this)
        listViewBack.adapter = adapterBack
        listViewBack.setOnTouchListener(this)
    }

    @UiThread
    internal open fun start() {
        progressBar!!.visibility = View.VISIBLE
    }

    @Background
    open fun loadCurrentStandings(reloadData: Boolean) {
        if (reloadData) {
            dataService!!.clearDriverStandingsCache()
        }
        driverStandings = null
        loadCurrentStandings()
    }

    @Background
    open fun loadCurrentStandings() {

        try {
            if (CollectionUtils.isEmpty(driverStandings)) {
                start()
                driverStandings = dataService!!.loadDriverStandings()
            }
        } finally {
            updateUI()
        }
    }

    @UiThread
    internal open fun updateUI() {
        try {
            val listFront = ArrayList<F1DriverStandings>()
            val listBack = ArrayList<F1DriverStandings>()
            var nr = 0
            for (standings in ListUtils.emptyIfNull(driverStandings)) {
                if (nr < 5) {
                    listFront.add(standings)
                } else {
                    listBack.add(standings)
                }
                nr++
            }
            adapterFront!!.clearItems()
            adapterFront!!.setDrivers(listFront)
            adapterFront!!.notifyDataSetChanged()

            adapterBack!!.clearItems()
            adapterBack!!.setDrivers(listBack)
            adapterBack!!.notifyDataSetChanged()
        } finally {
            progressBar!!.visibility = View.INVISIBLE
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val deltaY: Float
        val deltaX: Float
        if (event.action == MotionEvent.ACTION_DOWN) {
            initialX = event.rawX
            initialY = event.rawY
        }

        if (event.action == MotionEvent.ACTION_UP) {
            deltaX = event.rawX - initialX
            deltaY = event.rawY - initialY

            if (deltaY == 0f && deltaX == 0f) {

                viewFlipper!!.setFlipInterval(1000)
                if (v.id == R.id.standing_listview_front) {
                    viewFlipper!!.showNext()
                } else {
                    viewFlipper!!.showPrevious()
                }
                viewFlipper!!.invalidate()
            }
        }
        return false
    }
}