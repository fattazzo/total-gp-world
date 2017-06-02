package com.gmail.fattazzo.formula1world.fragments.home.driverstandings;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.objects.DriverStandings;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.standings_item_list)
public class DriverStandingsItemView extends LinearLayout {

    @ViewById(R.id.standings_item_points)
    TextView pointsView;

    @ViewById(R.id.standings_item_name)
    TextView nameView;

    @ViewById(R.id.standings_item_color)
    ImageView teamColorView;

    public DriverStandingsItemView(Context context) {
        super(context);
    }

    public void bind(DriverStandings driverStandings) {
        pointsView.setText(String.valueOf(driverStandings.getPoints()));
        nameView.setText(driverStandings.getDriver().getGivenName() + " " + driverStandings.getDriver().getFamilyName());

        int color;
        try {
            color = teamColorView.getResources().getIdentifier(driverStandings.getConstructors().get(0).getConstructorId(), "color", teamColorView.getContext().getPackageName());
        } catch (Exception e) {
            color = R.color.background_color;
        }
        teamColorView.setColorFilter(ContextCompat.getColor(teamColorView.getContext(), color));
    }
}