package com.gmail.fattazzo.formula1world.view.chart.marker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.gmail.fattazzo.formula1world.R;

import org.apache.commons.lang3.StringUtils;

public class F1MarkerEntryDataView extends MarkerView {

    private final String xValueLabel;
    private final String yValueLabel;

    private TextView tvContent;
    private TextView tvContent2;

    public F1MarkerEntryDataView(Context context, String xValueLabel, String yValueLabel) {
        super(context, R.layout.chart_marker_view_large);
        this.xValueLabel = xValueLabel;
        this.yValueLabel = yValueLabel;

        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent2 = (TextView) findViewById(R.id.tvContent2);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(e.getData() != null ? e.getData().toString() : "");

        String text2 = "";
        if (StringUtils.isNotBlank(xValueLabel)) {
            text2 = text2 + xValueLabel + " " + Utils.formatNumber(e.getX(),0,false);
        }
        if (StringUtils.isNotBlank(yValueLabel)) {
            text2 = text2 + yValueLabel + " " + Utils.formatNumber(e.getY(),0,false);
        }
        tvContent2.setText(text2);

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
