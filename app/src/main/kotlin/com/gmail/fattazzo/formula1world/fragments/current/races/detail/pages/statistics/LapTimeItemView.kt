package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ThemeUtils

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

@EViewGroup(R.layout.lap_time_item_list)
open class LapTimeItemView(context: Context) : ConstraintLayout(context) {

    @Bean
    lateinit internal var themeUtils: ThemeUtils

    @Bean
    lateinit internal var dataService: DataService

    @ViewById
    lateinit internal var driverName: TextView
    @ViewById
    lateinit internal var lap: TextView
    @ViewById
    lateinit internal var position: TextView
    @ViewById
    lateinit internal var time: TextView

    @ViewById
    lateinit internal var teamColor: ImageView

    fun bind(lapTime: F1LapTime, rowNumber: Int) {

        driverName!!.text = lapTime.driver!!.fullName
        lap!!.text = lapTime.lap.toString()
        position!!.text = lapTime.position.toString()
        time!!.text = lapTime.time

        setBackgroundColor(if (rowNumber % 2 == 0) themeUtils!!.getThemeEvenRowColor(context) else themeUtils!!.getThemeOddRowColor(context))

        val color = dataService!!.loadDriverColor(lapTime.driver)
        teamColor!!.setColorFilter(color)
    }
}