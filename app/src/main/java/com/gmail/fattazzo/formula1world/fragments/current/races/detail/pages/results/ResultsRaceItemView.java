package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.results;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Result;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
 */
@EViewGroup(R.layout.race_results_progress_row2)
public class ResultsRaceItemView extends TableRow {

    @ViewById(R.id.race_results_driver)
    TextView driverView;

    @ViewById(R.id.race_results_position)
    TextView positionView;

    @ViewById(R.id.race_results_laps)
    TextView lapsView;

    @ViewById(R.id.race_results_grid)
    TextView gridView;

    @ViewById(R.id.race_results_time)
    TextView timeView;

    @ViewById(R.id.race_results_status)
    TextView statusView;

    @ViewById(R.id.race_results_points)
    TextView pointsView;

    private F1Result result;

    private int rowNumber;

    public ResultsRaceItemView(Context context, F1Result result, int rowNumber) {
        super(context);
        this.result = result;
        this.rowNumber = rowNumber;
    }

    @AfterViews
    void bind() {
        driverView.setText(result.driver.getFullName());
        positionView.setText(result.positionText);
        lapsView.setText(String.valueOf(result.laps));
        gridView.setText(String.valueOf(result.grid));
        String time = "-";
        if (result.time != null) {
            time = StringUtils.defaultString(result.time.time);
        }
        timeView.setText(time);
        statusView.setText(result.status);

        Float points = ObjectUtils.defaultIfNull(result.points,0f);
        boolean hasDecimals = points % 1 != 0;
        if(hasDecimals) {
            pointsView.setText(String.valueOf(points));
        } else {
            pointsView.setText(String.valueOf(points.intValue()));
        }

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
