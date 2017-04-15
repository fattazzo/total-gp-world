package com.gmail.fattazzo.formula1livenews.fragments.home.circuit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.activity.fullscreen.FullScreenImageActivity;
import com.gmail.fattazzo.formula1livenews.activity.fullscreen.FullScreenImageActivity_;
import com.gmail.fattazzo.formula1livenews.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Schedule;
import com.gmail.fattazzo.formula1livenews.service.CurrentSeasonDataService;
import com.gmail.fattazzo.formula1livenews.utils.ImageUtils;
import com.gmail.fattazzo.formula1livenews.utils.LocaleUtils;
import com.gmail.fattazzo.formula1livenews.utils.Utils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EBean
public class CurrentCircuitTask {

    @Bean
    Utils utils;

    @Bean
    LocaleUtils localeUtils;

    @Bean
    ImageUtils imageUtils;

    @Bean
    CurrentSeasonDataService dataService;

    @RootContext
    HomeActivity activity;

    @ViewById(R.id.home_current_circuit_progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.current_circuit_name)
    TextView circuitNameView;

    @ViewById(R.id.current_circuit_location)
    TextView circuitLocationView;

    @ViewById(R.id.current_circuit_country_flag)
    ImageView flagView;

    @ViewById(R.id.current_circuit_image)
    ImageView circuitImageView;

    @ViewById(R.id.current_circuit_round_number_label)
    TextView roundNumberView;

    @ViewById(R.id.current_circuit_date)
    TextView roundDateView;

    @ViewById(R.id.current_circuit_time)
    TextView roundTimeView;

    @ViewById(R.id.home_current_circuit_layout)
    void setOneView(RelativeLayout layout) {
        Button infoButton = (Button) layout.findViewById(R.id.current_circuit_info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCircuitInfo();
            }
        });
        circuitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCirtuitFullScreeenImage();
            }
        });
    }

    @UiThread
    void start() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Background
    public void loadCurrentSchedule() {

        Schedule result = null;
        try {
            start();

            result = dataService.loadCurrentSchedule();
        } finally {
            updateUI(result);
        }
    }

    @UiThread
    void updateUI(Schedule result) {
        try {

            String circuitName = activity.getString(R.string.no_race_scheduled);
            String circuitLocation = "";
            String circuitInfoLink = "";
            Bitmap flagImage = null;
            Bitmap circuitImage = null;
            String roundNumber = "";
            String roundDate = "";
            String roundTime = "";
            String circuitId = null;
            if (result != null) {
                circuitName = result.getRaceName();

                String countryEn = result.getCircuit().getLocation().getCountry();
                String countryLocale = localeUtils.getLocaleCountryName(countryEn);
                circuitLocation = countryLocale + ", " + result.getCircuit().getLocation().getLocality();
                circuitInfoLink = result.getCircuit().getUrl();
                flagImage = imageUtils.getFlagForCountryCode(localeUtils.getCountryCode(countryEn));
                circuitId = result.getCircuit().getCircuitId();
                circuitImage = imageUtils.getCircuitForCode(circuitId);
                roundNumber = String.valueOf(result.getRound());

                String dateUTCString = result.getDate() + "T" + result.getTime();
                roundDate = utils.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss'Z'", "EEEE dd MMMM yyyy");
                roundTime = utils.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss'Z'", "HH:mm");
            }

            circuitNameView.setText(circuitName);
            circuitLocationView.setText(circuitLocation);
            circuitLocationView.setTag(circuitInfoLink);
            flagView.setImageBitmap(flagImage);
            circuitImageView.setImageBitmap(circuitImage);
            circuitImageView.setTag(circuitId);
            roundNumberView.setText(roundNumber);
            roundDateView.setText(roundDate);
            roundTimeView.setText(roundTime);
        } finally {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void openCircuitInfo() {
        String linkInfo = (String) circuitLocationView.getTag();
        utils.openLink(linkInfo);
    }

    private void openCirtuitFullScreeenImage() {
        String circuitId = (String) circuitImageView.getTag();

        if (circuitId != null) {
            Intent intent = new Intent(activity, FullScreenImageActivity_.class);

            Bundle extras = new Bundle();
            extras.putString(FullScreenImageActivity.EXTRA_CIRCUIT_ID, circuitId);
            intent.putExtras(extras);
            activity.startActivity(intent);
        }
    }
}