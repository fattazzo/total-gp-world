package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

import android.util.Log

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result

import org.androidannotations.annotations.EBean

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

/**
 * @author fattazzo
 *
 *
 * date: 21/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class LocalDBStatsService {

    val lastRaceData: Date
        get() {
            var lastDate: Date
            try {
                val dbResult = Select("res.*").from(Result::class.java).`as`("res")
                        .innerJoin(Race::class.java).`as`("rac").on("rac.Id = res.raceId")
                        .orderBy("rac.year desc,rac.round desc")
                        .limit(1)
                        .executeSingle<Result>()

                val utcFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                utcFormat.timeZone = TimeZone.getTimeZone("UTC")

                lastDate = utcFormat.parse(dbResult.toF1Result().race!!.date)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                lastDate = GregorianCalendar(1950, Calendar.JANUARY, 1).time
            }

            return lastDate
        }

    companion object {

        private val TAG = LocalDBStatsService::class.java.simpleName
    }
}
