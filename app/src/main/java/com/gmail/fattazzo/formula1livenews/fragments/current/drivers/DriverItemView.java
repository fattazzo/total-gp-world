package com.gmail.fattazzo.formula1livenews.fragments.current.drivers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.utils.CountryNationality;
import com.gmail.fattazzo.formula1livenews.utils.ImageUtils;
import com.gmail.fattazzo.formula1livenews.utils.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

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

    public void bind(Driver driver) {
        numberView.setText(String.valueOf(driver.getPermanentNumber()));
        nameView.setText(driver.getGivenName() + " " + driver.getFamilyName());

        String localeDateString;
        try {
            DateFormat localDateFormat = android.text.format.DateFormat.getDateFormat(this.getContext().getApplicationContext());
            Date date = DateUtils.parseDate(driver.getDateOfBirth(),"yyyy-MM-dd");
            localeDateString = localDateFormat.format(date);
        } catch (ParseException e) {
            localeDateString = driver.getDateOfBirth();
        }

        dateOfBirthView.setText(driver.getCode() + " " + localeDateString);

        CountryNationality countryNationality = utils.getCountryNationality(driver.getNationality());
        if(countryNationality != null) {
            flagImageView.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code()));
        }
    }
}