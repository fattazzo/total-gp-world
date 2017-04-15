package com.gmail.fattazzo.formula1livenews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    /**
     * Open link in external activity.
     *
     * @param link link to open
     */
    public void openLink(@Nullable String link) {
        if (StringUtils.isNotBlank(link)) {
            link = StringUtils.replaceOnce(link, "en.wikipedia.org", Locale.getDefault().getLanguage() + ".wikipedia.org");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setData(Uri.parse(link));
            context.startActivity(i);
        }
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

            DateFormat pstFormat = new SimpleDateFormat(dateLocalPattern,Locale.getDefault());
            pstFormat.setTimeZone(TimeZone.getDefault());

            dateLocal = pstFormat.format(date);
        } catch (Exception e) {
            dateLocal = "";
        }

        return dateLocal;
    }
}
