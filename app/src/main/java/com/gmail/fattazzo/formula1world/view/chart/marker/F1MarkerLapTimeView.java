package com.gmail.fattazzo.formula1world.view.chart.marker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;

public class F1MarkerLapTimeView extends MarkerView {

    private TextView tvContent;
    private TextView tvContent2;

    public F1MarkerLapTimeView(Context context) {
        super(context, R.layout.chart_marker_view_large);

        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent2 = (TextView) findViewById(R.id.tvContent2);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e.getData() instanceof F1LapTime) {
            F1LapTime lapTime = (F1LapTime) e.getData();

            tvContent.setText(lapTime.driver.getFullName());
            tvContent2.setText(lapTime.time);
        } else {
            tvContent.setText("");
            tvContent2.setText("");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
