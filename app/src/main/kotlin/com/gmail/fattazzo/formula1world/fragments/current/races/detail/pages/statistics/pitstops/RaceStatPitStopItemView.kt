package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops

import android.content.Context
import android.util.TypedValue
import android.widget.TableRow
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1PitStop

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

/**
 * @author fattazzo
 *
 *
 * date: 20/04/17
 */
@EViewGroup(R.layout.race_stat_pitstops_row)
open class RaceStatPitStopItemView(context: Context, private val pitStop: F1PitStop, private val rowNumber: Int) : TableRow(context) {

    @ViewById
    lateinit internal var driver_tv: TextView

    @ViewById
    lateinit internal var stop_tv: TextView

    @ViewById
    lateinit internal var lap_tv: TextView

    @ViewById
    lateinit internal var time_tv: TextView

    @ViewById
    lateinit internal var duration_tv: TextView

    private val themeEvenRowColor: Int
        get() {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.evenRowColor, value, true)
            return value.data
        }

    private val themeOddRowColor: Int
        get() {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.oddRowColor, value, true)
            return value.data
        }

    @AfterViews
    internal fun bind() {
        driver_tv!!.text = pitStop.driver!!.fullName
        stop_tv!!.text = pitStop.stop.toString()
        lap_tv!!.text = pitStop.lap.toString()
        time_tv!!.text = pitStop.time
        duration_tv!!.text = pitStop.duration

        setBackgroundColor(if (rowNumber % 2 == 0) themeEvenRowColor else themeOddRowColor)
    }
}
