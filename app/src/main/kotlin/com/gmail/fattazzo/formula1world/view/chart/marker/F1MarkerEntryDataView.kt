package com.gmail.fattazzo.formula1world.view.chart.marker

import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.gmail.fattazzo.formula1world.R

import org.apache.commons.lang3.StringUtils

class F1MarkerEntryDataView(context: Context, private val xValueLabel: String?, private val yValueLabel: String) : MarkerView(context, R.layout.chart_marker_view_large) {

    private val tvContent: TextView
    private val tvContent2: TextView

    init {

        tvContent = findViewById(R.id.tvContent) as TextView
        tvContent2 = findViewById(R.id.tvContent2) as TextView
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        tvContent.text = if (e!!.data != null) e.data.toString() else ""

        var text2 = ""
        if (StringUtils.isNotBlank(xValueLabel)) {
            text2 = text2 + xValueLabel + " " + Utils.formatNumber(e.x, 0, false)
        }
        if (StringUtils.isNotBlank(yValueLabel)) {
            text2 = text2 + yValueLabel + " " + Utils.formatNumber(e.y, 0, false)
        }
        tvContent2.text = text2

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
