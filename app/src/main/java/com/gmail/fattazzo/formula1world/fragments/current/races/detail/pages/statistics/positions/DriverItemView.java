package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.standings_item_list)
public class DriverItemView extends LinearLayout {

    @Bean
    ImageUtils imageUtils;

    @ViewById
    TextView standings_item_points;

    @ViewById
    ImageView standings_item_color;

    @ViewById
    TextView standings_item_name;

    public DriverItemView(Context context) {
        super(context);
    }

    public void bind(DriverSpinnerModel model) {
        standings_item_points.setVisibility(GONE);

        int color = imageUtils.getColorForConstructorRef(model.getConstructor() != null ? model.getConstructor().constructorRef : "");
        standings_item_color.setColorFilter(ContextCompat.getColor(standings_item_color.getContext(), color));

        standings_item_name.setText(model.getDriver().getFullName());
    }
}