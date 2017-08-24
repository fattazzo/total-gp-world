package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

import android.support.annotation.NonNull;
import android.util.Log;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result;

import org.androidannotations.annotations.EBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class LocalDBStatsService {

    private static final String TAG = LocalDBStatsService.class.getSimpleName();

    public @NonNull Date getLastRaceData() {
        Date lastDate;
        try {
            Result dbResult = new Select("res.*").from(Result.class).as("res")
                    .innerJoin(Race.class).as("rac").on("rac.Id = res.raceId")
                    .orderBy("rac.year desc,rac.round desc")
                    .limit(1)
                    .executeSingle();

            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            lastDate = utcFormat.parse(dbResult.toF1Result().race.date);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            lastDate = new GregorianCalendar(1950, Calendar.JANUARY, 1).getTime();
        }
        return lastDate;
    }
}
