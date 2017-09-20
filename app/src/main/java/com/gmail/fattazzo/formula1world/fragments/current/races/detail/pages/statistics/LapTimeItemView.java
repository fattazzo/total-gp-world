package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.utils.ThemeUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.lap_time_item_list)
public class LapTimeItemView extends ConstraintLayout {

    @Bean
    ThemeUtils themeUtils;

    @Bean
    DataService dataService;

    @ViewById
    TextView driverName, lap, position, time;

    @ViewById
    ImageView teamColor;

    public LapTimeItemView(Context context) {
        super(context);
    }

    public void bind(F1LapTime lapTime, int rowNumber) {

        driverName.setText(lapTime.driver.getFullName());
        lap.setText(String.valueOf(lapTime.lap));
        position.setText(String.valueOf(lapTime.position));
        time.setText(lapTime.time);

        setBackgroundColor(rowNumber % 2 == 0 ? themeUtils.getThemeEvenRowColor(getContext()) : themeUtils.getThemeOddRowColor(getContext()));

        int color = dataService.loadDriverColor(lapTime.driver);
        teamColor.setColorFilter(color);
    }
}