package com.gmail.fattazzo.formula1world.fragments.stats

import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import com.appyvet.rangebar.RangeBar
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity.Companion.PREF_ACTIVITY_RESULT
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity_
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.service.StatisticsService
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 04/09/17
 */
@OptionsMenu(R.menu.statistics)
@EFragment(R.layout.fragment_statistics)
abstract class AbstractStatisticsFragment : BaseFragment() {

    @ViewById
    lateinit protected var seasonsRangeBar: RangeBar

    @ViewById
    lateinit protected var statsSpinner: Spinner

    @ViewById
    lateinit protected var statistics_container: FrameLayout

    @Bean
    lateinit protected var statisticsService: StatisticsService

    /**
     * Multi seasons selection.
     *
     * @return `true` for multi seasons, `false` for single season
     */
    abstract val isMultiSeasons: Boolean

    /**
     * Array of available statistics.
     *
     * @return res id
     */
    abstract val statsListResId: Int

    @AfterViews
    protected fun init() {
        val cal = Calendar.getInstance()
        cal.time = statisticsService!!.lastRaceData
        var year = cal.get(Calendar.YEAR)
        if (year == 1950) {
            year = Calendar.getInstance().get(Calendar.YEAR)
        }
        seasonsRangeBar!!.tickEnd = year.toFloat()
        seasonsRangeBar!!.tickStart = 1950f
        seasonsRangeBar!!.setRangeBarEnabled(isMultiSeasons)
        seasonsRangeBar!!.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                statsSpinner(true, statsSpinner!!.selectedItemPosition)
            }
            false
        }

        val arraySpinner = resources.getStringArray(statsListResId)
        val adapter = ArrayAdapter(activity,
                R.layout.f1_simple_item, arraySpinner)
        statsSpinner!!.adapter = adapter
    }

    @ItemSelect
    fun statsSpinner(selected: Boolean, position: Int) {
        val seasonStart = Integer.valueOf(seasonsRangeBar!!.leftPinValue)
        val seasonEnd = Integer.valueOf(seasonsRangeBar!!.rightPinValue)

        showStat(position, seasonStart, seasonEnd, R.id.statistics_container, statistics_container)
    }

    abstract fun showStat(index: Int, seasonStart: Int?, seasonEnd: Int?, containerResId: Int, container: FrameLayout?)

    @OptionsItem
    protected fun action_settings() {
        SettingsActivity_.intent(context).startForResult(PREF_ACTIVITY_RESULT)
    }
}
