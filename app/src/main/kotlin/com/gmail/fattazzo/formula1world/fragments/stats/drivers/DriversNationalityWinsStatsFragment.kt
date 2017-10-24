package com.gmail.fattazzo.formula1world.fragments.stats.drivers

import android.graphics.Bitmap
import android.widget.BaseAdapter
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData
import com.gmail.fattazzo.formula1world.fragments.stats.AbstractStatsChartFragment
import com.gmail.fattazzo.formula1world.fragments.stats.adapters.StatsDataImageLabelListAdapter
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EFragment
import java.text.DecimalFormat

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
@EFragment(R.layout.fragment_stats_chart)
open class DriversNationalityWinsStatsFragment : AbstractStatsChartFragment() {
    override val chartValueFormatter: IValueFormatter
        get() = DefaultValueFormatter(0)
    override val listValueFormat: DecimalFormat
        get() = DecimalFormat("0")

    @Bean
    lateinit protected var imageUtils: ImageUtils

    @Bean
    lateinit protected var utils: Utils

    public override fun loadData(): List<StatsData> {
        return statisticsService.loadDriversWinsNationality(this!!.seasonStart!!, this!!.seasonEnd!!)
    }

    override fun createListAdapter(data: List<StatsData>, valueFormat: DecimalFormat): BaseAdapter {
        return object : StatsDataImageLabelListAdapter(activity, data, valueFormat) {
            override fun getImage(data: StatsData): Bitmap? {
                val country = utils!!.getCountryNationality(data.label)
                return if (country != null) {
                    imageUtils!!.getFlagForCountryCode(country.alpha2Code!!)
                } else null
            }
        }
    }
}
