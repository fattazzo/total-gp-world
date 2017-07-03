package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
 */
@EViewGroup(R.layout.race_stat_pitstops_row)
public class RaceStatPitStopItemView extends TableRow {

    @ViewById
    TextView driver_tv;

    @ViewById
    TextView stop_tv;

    @ViewById
    TextView lap_tv;

    @ViewById
    TextView time_tv;

    @ViewById
    TextView duration_tv;

    private F1PitStop pitStop;

    private int rowNumber;

    public RaceStatPitStopItemView(Context context, F1PitStop pitStop, int rowNumber) {
        super(context);
        this.pitStop = pitStop;
        this.rowNumber = rowNumber;
    }

    @AfterViews
    void bind() {
        driver_tv.setText(pitStop.driver.getFullName());
        stop_tv.setText(String.valueOf(pitStop.stop));
        lap_tv.setText(String.valueOf(pitStop.lap));
        time_tv.setText(pitStop.time);
        duration_tv.setText(pitStop.duration);

        setBackgroundColor(rowNumber % 2 == 0 ? getThemeEvenRowColor() : getThemeOddRowColor());
    }

    private int getThemeEvenRowColor() {
        final TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.evenRowColor, value, true);
        return value.data;
    }

    private int getThemeOddRowColor() {
        final TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.oddRowColor, value, true);
        return value.data;
    }
}
