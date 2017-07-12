package com.gmail.fattazzo.formula1world.view.chart.marker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.gmail.fattazzo.formula1world.R;

public class F1MarkerYValueView extends MarkerView {

    private TextView tvContent;

    public F1MarkerYValueView(Context context) {
        super(context, R.layout.chart_marker_view);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(Utils.formatNumber(e.getY(),0,false));

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
