package com.gmail.fattazzo.formula1world.view.chart.valueformatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/06/17
 */
public class AlternateChartValueFormatter extends DefaultChartValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        boolean evenData = true;
        if (entry.getData() instanceof Boolean) {
            evenData = (boolean) entry.getData();
        }

        if (evenData) {
            return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
        }
        return "";
    }
}
