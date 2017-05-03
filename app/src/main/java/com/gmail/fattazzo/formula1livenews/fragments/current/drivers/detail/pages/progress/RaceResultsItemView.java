package com.gmail.fattazzo.formula1livenews.fragments.current.drivers.detail.pages.progress;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResult;
import com.gmail.fattazzo.formula1livenews.ergast.objects.RaceResults;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
 */
@EViewGroup(R.layout.race_results_progress_row)
public class RaceResultsItemView extends TableRow {

    @ColorRes
    int odd_row;

    @ColorRes
    int even_row;

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

    private RaceResults raceResults;

    private int rowNumber;

    public RaceResultsItemView(Context context, RaceResults raceResults, int rowNumber) {
        super(context);
        this.raceResults = raceResults;
        this.rowNumber = rowNumber;
    }

    @AfterViews
    void bind() {
        RaceResult result = raceResults.getResults().get(0);
        positionView.setText(result.getPositionText());
        lapsView.setText(String.valueOf(result.getLaps()));
        gridView.setText(String.valueOf(result.getGrid()));
        String time = "-";
        if(result.getTime() != null ){
            time = result.getTime().getTime();
        }
        timeView.setText(time);
        statusView.setText(result.getStatus());
        pointsView.setText(String.valueOf(result.getPoints()));

        setBackgroundColor(rowNumber % 2 == 0 ? even_row : odd_row);
    }
}
