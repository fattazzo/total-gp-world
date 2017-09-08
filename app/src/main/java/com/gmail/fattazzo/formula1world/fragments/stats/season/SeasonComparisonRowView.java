package com.gmail.fattazzo.formula1world.fragments.stats.season;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/09/17
 */
@EViewGroup(R.layout.season_comparison_row)
public class SeasonComparisonRowView extends ConstraintLayout {

    private final int titleResId;

    private final float prevValue;
    private final float value;
    private final float nextValue;

    @ViewById
    TextView seasonComparisonTitle;

    @ViewById
    TextView prevSeasonDataTV, seasonDataTV, nextSeasonDataTV;

    public SeasonComparisonRowView(Context context, int titleResId, int prevValue, int value, int nextValue) {
        super(context);
        this.titleResId = titleResId;
        this.prevValue = prevValue;
        this.value = value;
        this.nextValue = nextValue;
    }

    public SeasonComparisonRowView(Context context, int titleResId, float prevValue, float value, float nextValue) {
        super(context);
        this.titleResId = titleResId;
        this.prevValue = prevValue;
        this.value = value;
        this.nextValue = nextValue;
    }

    @AfterViews
    void bind() {
        seasonComparisonTitle.setText(getContext().getString(titleResId));

        DecimalFormat format = new DecimalFormat("0.##");
        prevSeasonDataTV.setText(format.format(prevValue));
        seasonDataTV.setText(format.format(value));
        nextSeasonDataTV.setText(format.format(nextValue));

        float prevDiff = value - prevValue;
        prevSeasonDataTV.setTextColor(getColor(prevDiff));

        float nextDiff = value - nextValue;
        nextSeasonDataTV.setTextColor(getColor(nextDiff));
    }

    private ColorStateList getColor(float diff) {
        int result;
        if (diff > 0) {
            result = R.color.red;
        } else if (diff < 0) {
            result = R.color.green;
        } else {
            result = R.color.gray;
        }
        return getResources().getColorStateList(result);
    }
}
