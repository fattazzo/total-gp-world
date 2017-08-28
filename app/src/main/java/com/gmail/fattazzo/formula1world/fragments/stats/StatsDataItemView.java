package com.gmail.fattazzo.formula1world.fragments.stats;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;

@EViewGroup(R.layout.stats_row)
public class StatsDataItemView extends ConstraintLayout {

    private final StatsData data;
    private final int position;
    private final DecimalFormat format;
    @ViewById
    TextView textLeft;

    @ViewById
    TextView textCenter;

    @ViewById
    TextView textRight;

    public StatsDataItemView(Context context,StatsData data, int position, DecimalFormat format) {
        super(context);
        this.data = data;
        this.position = position;
        this.format = format;
    }

    @AfterViews
    void bind() {
        textLeft.setText(String.valueOf(position));
        textCenter.setText(data.getLabel());
        textRight.setText(format.format(data.getValue()));
    }
}