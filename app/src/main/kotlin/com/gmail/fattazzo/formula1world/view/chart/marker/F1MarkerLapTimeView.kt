package com.gmail.fattazzo.formula1world.view.chart.marker

import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.domain.F1LapTime

class F1MarkerLapTimeView(context: Context) : MarkerView(context, R.layout.chart_marker_view_large) {

    private val tvContent: TextView
    private val tvContent2: TextView

    init {

        tvContent = findViewById(R.id.tvContent) as TextView
        tvContent2 = findViewById(R.id.tvContent2) as TextView
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        if (e!!.data is F1LapTime) {
            val lapTime = e.data as F1LapTime

            tvContent.text = lapTime.driver!!.fullName
            tvContent2.text = lapTime.time
        } else {
            tvContent.text = ""
            tvContent2.text = ""
        }

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
