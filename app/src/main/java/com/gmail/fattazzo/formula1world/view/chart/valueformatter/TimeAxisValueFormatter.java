package com.gmail.fattazzo.formula1world.view.chart.valueformatter;

import com.activeandroid.util.Log;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeAxisValueFormatter implements IAxisValueFormatter {

    private static final String TAG = TimeAxisValueFormatter.class.getSimpleName();

    private String pattern;

    private List<String> entries;

    public TimeAxisValueFormatter(String pattern, List<String> entries) {
        this.pattern = pattern;
        this.entries = entries;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        //String result;
        //try {
            int idx = (int) value % entries.size();
            //int millisec = entries.get(idx);
            //Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(0);
            //calendar.add(Calendar.MILLISECOND,millisec);
            //result = DateFormatUtils.format(calendar,pattern);
            //result = String.valueOf(millisec);
            return entries.get(idx);
        //} catch (Exception e) {
        //    result = "";
        //}
        //Log.e(TAG,"Value: " + value + " - formatted: " + result);
        //return result;
    }
}
