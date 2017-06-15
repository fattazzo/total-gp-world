package com.gmail.fattazzo.formula1world.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author fattazzo
 *         <p/>
 *         date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocaleUtils {

    @RootContext
    Context context;

    private Map<String, String> countries;

    /**
     * Translate english country name to current locale.
     *
     * @param enCountryName name to translate
     * @return name translated, enCountryName on error
     */
    @NonNull
    public String getLocaleCountryName(@NonNull String enCountryName) {

        String i18nCountryName;
        try {
            String code = getCountryCode(enCountryName);

            Locale loc = new Locale(Locale.getDefault().getISO3Language(), code);

            i18nCountryName = loc.getDisplayCountry();
        } catch (Exception e) {
            i18nCountryName = enCountryName;
        }
        return i18nCountryName;
    }

    /**
     * Get country code from country name.
     *
     * @param enCountryName country name
     * @return country code
     */
    @NonNull
    public String getCountryCode(@NonNull String enCountryName) {
        String code;
        try {
            code = getCountries().get(enCountryName);
            if(code == null) {
                code = enCountryName;
            }
        } catch (Exception e) {
            code = "zz";
        }
        return code;
    }

    /**
     * @return cached countries map
     */
    @NonNull
    private Map<String, String> getCountries() {
        if (countries == null) {
            countries = new HashMap<>();
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("en", iso);
                countries.put(l.getDisplayCountry(l), iso);
            }
            // non iso country variants
            countries.put("UK","GB");
            countries.put("USA","US");
            countries.put("UAE","AE");
            countries.put("Korea","KR");
        }
        return countries;
    }
}
