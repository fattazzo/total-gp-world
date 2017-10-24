package com.gmail.fattazzo.formula1world.view.chart.marker

import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.gmail.fattazzo.formula1world.R

class F1MarkerYValueView(context: Context) : MarkerView(context, R.layout.chart_marker_view) {

    private val tvContent: TextView

    init {

        tvContent = findViewById(R.id.tvContent) as TextView
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        tvContent.text = Utils.formatNumber(e!!.y, 0, false)

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
