package com.gmail.fattazzo.formula1world.fragments.home.constructorstandings

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings
import com.gmail.fattazzo.formula1world.service.DataService

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.ObjectUtils

@EViewGroup(R.layout.standings_item_list)
open class ConstructorStandingsItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var dataService: DataService

    @ViewById(R.id.standings_item_points)
    lateinit internal var pointsView: TextView

    @ViewById(R.id.standings_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.standings_item_color)
    lateinit internal var teamColorView: ImageView

    fun bind(standings: F1ConstructorStandings) {
        val points = ObjectUtils.defaultIfNull(standings.points, 0f)
        val hasDecimals = points!! % 1 != 0f
        if (hasDecimals) {
            pointsView!!.text = points.toString()
        } else {
            pointsView!!.text = points.toInt().toString()
        }

        if (standings.constructor != null) {
            nameView!!.text = standings.constructor!!.name
        }

        val color = dataService!!.loadContructorColor(standings.constructor)
        teamColorView!!.setColorFilter(color)
    }
}