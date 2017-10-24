package com.gmail.fattazzo.formula1world.view.chart.valueformatter

import com.activeandroid.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

import org.apache.commons.lang3.time.DateFormatUtils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class TimeAxisValueFormatter(private val pattern: String, private val entries: List<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        //String result;
        //try {
        val idx = value.toInt() % entries.size
        //int millisec = entries.get(idx);
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(0);
        //calendar.add(Calendar.MILLISECOND,millisec);
        //result = DateFormatUtils.format(calendar,pattern);
        //result = String.valueOf(millisec);
        return entries[idx]
        //} catch (Exception e) {
        //    result = "";
        //}
        //Log.e(TAG,"Value: " + value + " - formatted: " + result);
        //return result;
    }

    companion object {

        private val TAG = TimeAxisValueFormatter::class.java.simpleName
    }
}
