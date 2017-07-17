package com.gmail.fattazzo.formula1world.fragments.home.circuit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.fullscreen.FullScreenImageActivity_;
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.domain.F1Season;
import com.gmail.fattazzo.formula1world.service.DataService;
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
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    DataService dataService;

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

    @ViewById
    View home_current_circuit_layout;

    @ViewById
    WebView season_wv;

    private F1Race raceLoaded = null;
    private F1Season season = null;

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
                if (circuitImageView.getTag() != null) {
                    FullScreenImageActivity_.intent(activity).circuit_id((String) circuitImageView.getTag()).start();
                }
            }
        });
    }


    @UiThread
    void start() {
        progressBar.setVisibility(View.VISIBLE);
    }


    public void loadCurrentSchedule(boolean reloadData) {
        if (reloadData) {
            dataService.clearRacesCache();
        }
        loadCurrentSchedule();
    }

    @Background
    public void loadCurrentSchedule() {
        try {
            start();
            raceLoaded = dataService.loadCurrentSchedule();

            season = dataService.loadSeason(dataService.getSelectedSeasons());

            if(StringUtils.isBlank(season.description)) {
                Document doc = Jsoup.connect(season.url.replace("en.wikipedia", "en.m.wikipedia")).get();
                season.description = doc.outerHtml();
                dataService.updateSeason(season);
            }
        } catch (Exception e) {
            season.description = "";
        } finally {
            if (season.current) {
                updateRaceUI();
            } else {
                updateSeasonUI();
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @UiThread
    void updateSeasonUI() {
        home_current_circuit_layout.setVisibility(View.GONE);
        season_wv.setVisibility(View.VISIBLE);

        season_wv.getSettings().setJavaScriptEnabled(true);
        season_wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        if(StringUtils.isNotBlank(season.description)) {
            season_wv.loadDataWithBaseURL("https://en.m.wikipedia.org", season.description, "text/html; charset=UTF-8", null, null);
        } else {
            season_wv.loadData("<html>No data</html>", "text/html; charset=UTF-8", null);
        }
    }

    @UiThread
    void updateRaceUI() {
        season_wv.setVisibility(View.GONE);
        home_current_circuit_layout.setVisibility(View.VISIBLE);

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
            if (raceLoaded != null) {
                circuitName = raceLoaded.name;

                String countryEn = raceLoaded.circuit.location.country;
                String countryLocale = localeUtils.getLocaleCountryName(countryEn);
                circuitLocation = countryLocale + ", " + raceLoaded.circuit.location.locality;
                circuitInfoLink = raceLoaded.circuit.url;
                flagImage = imageUtils.getFlagForCountryCode(localeUtils.getCountryCode(countryEn));
                circuitId = raceLoaded.circuit.circuitRef;
                circuitImage = imageUtils.getCircuitForCode(circuitId);
                if (preferenceManager.isBitmapInvertedForCurrentTheme()) {
                    circuitImage = imageUtils.invertColor(circuitImage);
                }
                roundNumber = String.valueOf(raceLoaded.round);

                String dateUTCString = raceLoaded.date + "T" + raceLoaded.time;
                roundDate = utils.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss", "EEEE dd MMMM yyyy");
                roundTime = utils.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss", "HH:mm");
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
}