package com.gmail.fattazzo.formula1world.fragments.home.driverstandings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

@EBean
internal open class DriverStandingsListAdapter : BaseAdapter() {

    private var drivers: MutableList<F1DriverStandings>? = null

    @RootContext
    lateinit var context: Context

    fun setDrivers(drivers: MutableList<F1DriverStandings>) {
        this.drivers = drivers
    }

    @AfterInject
    fun initAdapter() {
        drivers = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val driverStandingsItemView: DriverStandingsItemView
        if (convertView == null) {
            driverStandingsItemView = DriverStandingsItemView_.build(context)
        } else {
            driverStandingsItemView = convertView as DriverStandingsItemView
        }

        driverStandingsItemView.bind(getItem(position))

        return driverStandingsItemView
    }

    override fun getCount(): Int {
        return drivers!!.size
    }

    override fun getItem(position: Int): F1DriverStandings {
        return drivers!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        drivers!!.clear()
    }
}