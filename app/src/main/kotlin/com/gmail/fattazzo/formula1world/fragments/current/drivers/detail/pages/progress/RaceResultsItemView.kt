package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail.pages.progress

import android.content.Context
import android.util.TypedValue
import android.widget.TableRow
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Result

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils

/**
 * @author fattazzo
 *
 *
 * date: 20/04/17
 */
@EViewGroup(R.layout.race_results_progress_row)
open class RaceResultsItemView(context: Context, private val result: F1Result, private val rowNumber: Int) : TableRow(context) {

    @ViewById(R.id.race_results_position)
    lateinit internal var positionView: TextView

    @ViewById(R.id.race_results_laps)
    lateinit internal var lapsView: TextView

    @ViewById(R.id.race_results_grid)
    lateinit internal var gridView: TextView

    @ViewById(R.id.race_results_time)
    lateinit internal var timeView: TextView

    @ViewById(R.id.race_results_status)
    lateinit internal var statusView: TextView

    @ViewById(R.id.race_results_points)
    lateinit internal var pointsView: TextView

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
        positionView!!.text = result.positionText
        lapsView!!.text = result.laps.toString()
        gridView!!.text = result.grid.toString()
        var time = "-"
        if (result.time != null) {
            time = StringUtils.defaultString(result.time!!.time, "-")
        }
        timeView!!.text = time
        statusView!!.text = result.status

        val points = ObjectUtils.defaultIfNull(result.points, 0f)
        val hasDecimals = points!! % 1 != 0f
        if (hasDecimals) {
            pointsView!!.text = points.toString()
        } else {
            pointsView!!.text = points.toInt().toString()
        }

        setBackgroundColor(if (rowNumber % 2 == 0) themeEvenRowColor else themeOddRowColor)
    }
}
