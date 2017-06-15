package com.gmail.fattazzo.formula1world.fragments.current.races;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;

@EViewGroup(R.layout.race_item_list)
public class RaceItemView extends LinearLayout {

    @Bean
    Utils utils;

    @Bean
    LocaleUtils localeUtils;

    @Bean
    ImageUtils imageUtils;

    @ViewById(R.id.race_item_name)
    TextView nameView;

    @ViewById(R.id.race_item_info)
    TextView infoView;

    @ViewById(R.id.race_item_flag)
    ImageView flagImageView;

    public RaceItemView(Context context) {
        super(context);
    }

    public void bind(F1Race f1Race) {
        nameView.setText(f1Race.name);

        Calendar currentDateEnd = Calendar.getInstance();
        currentDateEnd.add(Calendar.HOUR_OF_DAY, 2);
        String currentDate = DateFormatUtils.format(currentDateEnd, "yyyy-MM-dd");

        String localeDateString;
        try {
            localeDateString = utils.convertUTCDateToLocal(f1Race.date, "yyyy-MM-dd", "dd/MM/yyyy");
        } catch (Exception e) {
            localeDateString = "";
        }
        infoView.setText(localeDateString);

        String countryCode = localeUtils.getCountryCode(f1Race.circuit.location.country);
        flagImageView.setImageBitmap(imageUtils.getFlagForCountryCode(countryCode));

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1);
        if(f1Race.date != null && f1Race.date.startsWith(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))) && currentDate.compareTo(f1Race.date) > 0) {
            matrix.setSaturation(0);
        }
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        flagImageView.setColorFilter(filter);
    }
}