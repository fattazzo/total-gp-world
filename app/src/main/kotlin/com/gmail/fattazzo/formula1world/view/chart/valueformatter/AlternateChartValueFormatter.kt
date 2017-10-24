package com.gmail.fattazzo.formula1world.view.chart.valueformatter

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * @author fattazzo
 *
 *
 * date: 07/06/17
 */
class AlternateChartValueFormatter : DefaultChartValueFormatter() {

    override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?): String {

        var evenData = true
        if (entry!!.data is Boolean) {
            evenData = entry.data as Boolean
        }

        return if (evenData) {
            super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler)
        } else ""
    }
}
