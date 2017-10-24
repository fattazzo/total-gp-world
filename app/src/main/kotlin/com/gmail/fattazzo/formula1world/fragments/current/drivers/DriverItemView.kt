package com.gmail.fattazzo.formula1world.fragments.current.drivers

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.ViewById
import org.apache.commons.lang3.StringUtils

@EViewGroup(R.layout.drivers_item_list)
open class DriverItemView(context: Context) : LinearLayout(context) {

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var dataService: DataService

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @ViewById(R.id.driver_item_number)
    lateinit internal var numberView: TextView

    @ViewById(R.id.driver_item_name)
    lateinit internal var nameView: TextView

    @ViewById(R.id.driver_item_dateOfBirth)
    lateinit internal var dateOfBirthView: TextView

    @ViewById(R.id.driver_item_flag)
    lateinit internal var flagImageView: ImageView

    @ViewById
    lateinit internal var driver_color: ImageView

    fun bind(driver: F1Driver) {
        numberView!!.text = driver.number.toString()
        nameView!!.text = driver.fullName

        var localeDateString: String
        try {
            val localDateFormat = android.text.format.DateFormat.getDateFormat(this.context.applicationContext)
            localeDateString = localDateFormat.format(driver.dateOfBirth)
        } catch (e: Exception) {
            localeDateString = ""
        }

        dateOfBirthView!!.text = (if (StringUtils.isNotBlank(driver.code)) driver.code!! + " " else "") + localeDateString

        val countryNationality = utils!!.getCountryNationality(driver.nationality)
        if (countryNationality != null) {
            flagImageView!!.setImageBitmap(imageUtils!!.getFlagForCountryCode(countryNationality.alpha2Code!!))
        }

        val color = dataService!!.loadDriverColor(driver)
        driver_color!!.setBackgroundColor(color)
    }
}