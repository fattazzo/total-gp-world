package com.gmail.fattazzo.formula1world.fragments.home.driverstandings;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.ObjectUtils;

@EViewGroup(R.layout.standings_item_list)
public class DriverStandingsItemView extends LinearLayout {

    @Bean
    ImageUtils imageUtils;

    @ViewById(R.id.standings_item_points)
    TextView pointsView;

    @ViewById(R.id.standings_item_name)
    TextView nameView;

    @ViewById(R.id.standings_item_color)
    ImageView teamColorView;

    public DriverStandingsItemView(Context context) {
        super(context);
    }

    public void bind(F1DriverStandings driverStandings) {
        Float points = ObjectUtils.defaultIfNull(driverStandings.points,0f);
        boolean hasDecimals = points % 1 != 0;
        if(hasDecimals) {
            pointsView.setText(String.valueOf(points));
        } else {
            pointsView.setText(String.valueOf(points.intValue()));
        }

        if (driverStandings.driver != null) {
            nameView.setText(driverStandings.driver.getFullName());
        }

        int color = imageUtils.getColorForConstructorRef(driverStandings.constructor != null ? driverStandings.constructor.constructorRef : "");
        teamColorView.setColorFilter(ContextCompat.getColor(teamColorView.getContext(), color));
    }
}