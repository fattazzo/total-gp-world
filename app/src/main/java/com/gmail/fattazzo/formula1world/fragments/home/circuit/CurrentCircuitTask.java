package com.gmail.fattazzo.formula1world.fragments.home.circuit;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1world.ergast.objects.Schedule;
import com.gmail.fattazzo.formula1world.service.CurrentSeasonDataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$FullScreenImageActivity;

@EBean
public class CurrentCircuitTask {

    @Bean
    ApplicationPreferenceManager preferenceManager;

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

    Schedule scheduleLoaded = null;

    @ViewById(R.id.current_circuit_info_button)
    void setupInfoButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkInfo = (String) circuitLocationView.getTag();
                utils.openLink(linkInfo);
            }
        });
    }

    @ViewById(R.id.home_current_circuit_layout)
    void setOneView(RelativeLayout layout) {
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
    public void loadCurrentSchedule(boolean reloadData) {
        scheduleLoaded = null;
        loadCurrentSchedule();
    }

    @Background
    public void loadCurrentSchedule() {
        try {
            if(scheduleLoaded == null) {
                start();
                scheduleLoaded = dataService.loadCurrentSchedule();
            }
        } finally {
            updateUI();
        }
    }

    @UiThread
    void updateUI() {
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
            if (scheduleLoaded != null) {
                circuitName = scheduleLoaded.getRaceName();

                String countryEn = scheduleLoaded.getCircuit().getLocation().getCountry();
                String countryLocale = localeUtils.getLocaleCountryName(countryEn);
                circuitLocation = countryLocale + ", " + scheduleLoaded.getCircuit().getLocation().getLocality();
                circuitInfoLink = scheduleLoaded.getCircuit().getUrl();
                flagImage = imageUtils.getFlagForCountryCode(localeUtils.getCountryCode(countryEn));
                circuitId = scheduleLoaded.getCircuit().getCircuitId();
                circuitImage = imageUtils.getCircuitForCode(circuitId);
                if(preferenceManager.isBitmapInvertedForCurrentTheme()) {
                    circuitImage = imageUtils.invertColor(circuitImage);
                }
                roundNumber = String.valueOf(scheduleLoaded.getRound());

                String dateUTCString = scheduleLoaded.getDate() + "T" + scheduleLoaded.getTime();
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

    void openCirtuitFullScreeenImage() {
        /**
        String circuitId = (String) circuitImageView.getTag();

        if (circuitId != null) {
            $FullScreenImageActivity().circuit_id(circuitId);
        }
         **/
        activity.openCirtuitFullScreeenImage((String) circuitImageView.getTag());
    }
}