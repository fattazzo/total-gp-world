package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.service.DataService

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

@EViewGroup(R.layout.standings_item_list)
open class DriverItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var dataService: DataService

    @ViewById
    lateinit internal var standings_item_points: TextView

    @ViewById
    lateinit internal var standings_item_color: ImageView

    @ViewById
    lateinit internal var standings_item_name: TextView

    fun bind(model: DriverSpinnerModel) {
        standings_item_points!!.visibility = View.GONE

        val color = dataService!!.loadContructorColor(model.constructor)
        standings_item_color!!.setColorFilter(color)

        standings_item_name!!.text = model.driver!!.fullName
    }
}