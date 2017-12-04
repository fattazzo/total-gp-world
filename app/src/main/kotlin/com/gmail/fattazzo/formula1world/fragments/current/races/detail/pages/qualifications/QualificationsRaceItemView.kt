package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Qualification
import com.gmail.fattazzo.formula1world.service.DataService

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

/**
 * @author fattazzo
 *
 *
 * date: 20/04/17
 */
@EViewGroup(R.layout.race_qualifications_row)
open class QualificationsRaceItemView(context: Context, private val qualification: F1Qualification, private val rowNumber: Int) : TableRow(context) {

    @Bean
    lateinit internal var dataService: DataService

    @ViewById
    lateinit internal var driver_tv: TextView

    @ViewById
    lateinit internal var position_tv: TextView

    @ViewById
    lateinit internal var q1_tv: TextView

    @ViewById
    lateinit internal var q2_tv: TextView

    @ViewById
    lateinit internal var q3_tv: TextView

    @ViewById
    lateinit internal var driver_item_color: ImageView

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
        var constructor_tv : TextView? = rootView.findViewById<TextView?>(R.id.constructor_tv)
        if (qualification.constructor != null && constructor_tv != null) {
            constructor_tv!!.text = qualification.constructor!!.name
        }
        if (qualification.driver != null) {
            driver_tv!!.text = qualification.driver!!.fullName
        }
        position_tv!!.text = qualification.position.toString()
        q1_tv!!.text = qualification.q1
        q2_tv!!.text = qualification.q2
        q3_tv!!.text = qualification.q3

        val color = dataService!!.loadContructorColor(qualification.constructor)
        driver_item_color!!.setColorFilter(color)

        setBackgroundColor(if (rowNumber % 2 == 0) themeEvenRowColor else themeOddRowColor)
    }
}
