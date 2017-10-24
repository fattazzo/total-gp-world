package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

@EBean
open class LapTimeListAdapter : BaseAdapter() {

    @RootContext
    lateinit internal var context: Context

    private var lapTimes: MutableList<F1LapTime>? = null

    fun setLapTimes(lapTimes: MutableList<F1LapTime>) {
        this.lapTimes = lapTimes
    }

    @AfterInject
    internal fun initAdapter() {
        lapTimes = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val lapTimeItemView: LapTimeItemView
        if (convertView == null) {
            lapTimeItemView = LapTimeItemView_.build(context)
        } else {
            lapTimeItemView = convertView as LapTimeItemView
        }

        lapTimeItemView.bind(getItem(position), position)

        return lapTimeItemView
    }

    override fun getCount(): Int {
        return lapTimes!!.size
    }

    override fun getItem(position: Int): F1LapTime {
        return lapTimes!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        lapTimes!!.clear()
    }

    internal fun removeItems(driver: F1Driver) {
        val iterator = lapTimes!!.iterator()
        while (iterator.hasNext()) {
            val lapTime = iterator.next()
            if (lapTime.driver!!.driverRef == driver.driverRef) {
                iterator.remove()
            }
        }
    }
}