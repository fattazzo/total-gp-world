package com.gmail.fattazzo.formula1world.fragments.current.constructors

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById

@EViewGroup(R.layout.constructors_item_list)
open class ConstructorItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @ViewById(R.id.constructor_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.constructor_item_flag)
    lateinit internal var flagImageView: ImageView

    @ViewById
    lateinit internal var constructor_color: ImageView

    fun bind(constructor: F1Constructor) {
        nameView!!.text = constructor.name

        val countryNationality = utils!!.getCountryNationality(constructor.nationality)
        if (countryNationality != null) {
            flagImageView!!.setImageBitmap(imageUtils!!.getFlagForCountryCode(countryNationality.alpha2Code!!))
        }

        val color = dataService!!.loadContructorColor(constructor)
        constructor_color!!.setBackgroundColor(color)
    }
}