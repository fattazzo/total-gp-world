package com.gmail.fattazzo.formula1world.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author fattazzo
 *         <p/>
 *         date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class Utils {

    @RootContext
    Context context;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    private Map<String, CountryNationality> countriesNationalitiesMap;

    /**
     * Open link in external activity.
     *
     * @param link link to open
     */
    public void openLink(@Nullable String link) {
        if (StringUtils.isNotBlank(link)) {
            link = getLocalizedLink(link);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setData(Uri.parse(link));
            context.startActivity(i);
        }
    }

    public String getLocalizedLink(@Nullable String link) {
        return StringUtils.replaceOnce(link, "en.wikipedia.org", Locale.getDefault().getLanguage() + ".wikipedia.org");
    }

    public void openCoordinates(@Nullable float latitude, @Nullable float longitude) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
    }

    /**
     * Convert UTC date with the specific pattern in local date.
     *
     * @param dateUTCString    UTC string date
     * @param dateUTCPattern   UTC pattern
     * @param dateLocalPattern Local pattern
     * @return local date, empty on error
     */
    @NonNull
    public String convertUTCDateToLocal(@NonNull String dateUTCString, @NonNull String dateUTCPattern, @NonNull String dateLocalPattern) {
        String dateLocal;

        try {
            DateFormat utcFormat = new SimpleDateFormat(dateUTCPattern, Locale.getDefault());
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = utcFormat.parse(dateUTCString);

            DateFormat pstFormat = new SimpleDateFormat(dateLocalPattern, Locale.getDefault());
            pstFormat.setTimeZone(TimeZone.getDefault());

            dateLocal = pstFormat.format(date);
        } catch (Exception e) {
            dateLocal = "";
        }

        return dateLocal;
    }

    /**
     * {@link CountryNationality} by given nationality
     *
     * @param nationality nationality
     * @return CountryNationality, {@code null} if doesnt exist
     */
    @Nullable
    public CountryNationality getCountryNationality(@Nullable String nationality) {
        return getCountriesNationalitiesMap().get(nationality);
    }

    private Map<String, CountryNationality> getCountriesNationalitiesMap() {
        if (countriesNationalitiesMap == null) {
            countriesNationalitiesMap = new HashMap<>();
            try (InputStream is = context.getAssets().open("countries-nationalities.json"); InputStreamReader isr = new InputStreamReader(is)) {
                Type type = new TypeToken<Collection<CountryNationality>>() {
                }.getType();
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(isr);
                Collection<CountryNationality> cn = gson.fromJson(reader, type);
                for (CountryNationality countryNationality : cn) {
                    String[] nationalities = StringUtils.split(countryNationality.getNationality(), ",");
                    for (String nat : nationalities) {
                        countriesNationalitiesMap.put(StringUtils.trim(nat), countryNationality);
                    }
                }
            } catch (IOException e) {
                countriesNationalitiesMap = new HashMap<>();
            }
        }
        return countriesNationalitiesMap;
    }
}
