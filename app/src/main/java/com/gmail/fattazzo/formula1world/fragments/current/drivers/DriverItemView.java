package com.gmail.fattazzo.formula1world.fragments.current.drivers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.utils.CountryNationality;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;

@EViewGroup(R.layout.drivers_item_list)
public class DriverItemView extends LinearLayout {

    @Bean
    Utils utils;

    @Bean
    ImageUtils imageUtils;

    @ViewById(R.id.driver_item_number)
    TextView numberView;

    @ViewById(R.id.driver_item_name)
    TextView nameView;

    @ViewById(R.id.driver_item_dateOfBirth)
    TextView dateOfBirthView;

    @ViewById(R.id.driver_item_flag)
    ImageView flagImageView;

    public DriverItemView(Context context) {
        super(context);
    }

    public void bind(F1Driver driver) {
        numberView.setText(String.valueOf(driver.number));
        nameView.setText(driver.getFullName());

        String localeDateString;
        try {
            DateFormat localDateFormat = android.text.format.DateFormat.getDateFormat(this.getContext().getApplicationContext());
            localeDateString = localDateFormat.format(driver.dateOfBirth);
        } catch (Exception e) {
            localeDateString = "";
        }

        dateOfBirthView.setText(driver.code + " " + localeDateString);

        CountryNationality countryNationality = utils.getCountryNationality(driver.nationality);
        if (countryNationality != null) {
            flagImageView.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code()));
        }
    }
}