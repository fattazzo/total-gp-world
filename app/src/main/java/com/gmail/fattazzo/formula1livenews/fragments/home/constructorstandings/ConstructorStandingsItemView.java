package com.gmail.fattazzo.formula1livenews.fragments.home.constructorstandings;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.ConstructorStandings;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.standings_item_list)
public class ConstructorStandingsItemView extends LinearLayout {

    @ViewById(R.id.standings_item_points)
    TextView pointsView;

    @ViewById(R.id.standings_item_name)
    TextView nameView;

    @ViewById(R.id.standings_item_color)
    ImageView teamColorView;

    public ConstructorStandingsItemView(Context context) {
        super(context);
    }

    public void bind(ConstructorStandings standings) {
        pointsView.setText(String.valueOf(standings.getPoints()));
        nameView.setText(standings.getConstructor().getName());

        int color;
        try {
            color = teamColorView.getResources().getIdentifier(standings.getConstructor().getConstructorId(), "color", teamColorView.getContext().getPackageName());
        } catch (Exception e) {
            color = R.color.background_color;
        }
        teamColorView.setColorFilter(ContextCompat.getColor(teamColorView.getContext(), color));
    }
}