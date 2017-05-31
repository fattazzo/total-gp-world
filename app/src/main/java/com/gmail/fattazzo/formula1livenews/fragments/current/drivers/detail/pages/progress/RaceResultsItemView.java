package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
 */
@EViewGroup(R.layout.race_results_progress_row)
public class RaceResultsItemView extends TableRow {

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

    private RaceResult raceResult;

    private int rowNumber;

    public RaceResultsItemView(Context context, RaceResult raceResult, int rowNumber) {
        super(context);
        this.raceResult = raceResult;
        this.rowNumber = rowNumber;
    }

    @AfterViews
    void bind() {
        positionView.setText(raceResult.getPositionText());
        lapsView.setText(String.valueOf(raceResult.getLaps()));
        gridView.setText(String.valueOf(raceResult.getGrid()));
        String time = "-";
        if (raceResult.getTime() != null) {
            time = raceResult.getTime().getTime();
        }
        timeView.setText(time);
        statusView.setText(raceResult.getStatus());
        pointsView.setText(String.valueOf(raceResult.getPoints()));

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
