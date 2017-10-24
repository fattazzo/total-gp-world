package com.gmail.fattazzo.formula1world.ergast.json

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.activeandroid.util.Log
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.Ergast
import com.gmail.fattazzo.formula1world.ergast.json.config.ErgastConfig.BASE_REQ
import com.gmail.fattazzo.formula1world.ergast.json.config.ErgastConfig.USER_AGENT
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author fattazzo
 *
 *
 * date: 14/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
internal open class ErgastConnection {

    @RootContext
    lateinit var context: Context

    /**
     * Build url for connection.
     *
     * @param ergast  ergast definition
     * @param request request resource
     * @param round   round
     * @return url
     */
    private fun buildUrl(ergast: Ergast, request: String, round: Int): String {
        val url = BASE_REQ.replace("{SERIES}", ergast.series).replace("{SEASON}", if (ergast.season == Ergast.CURRENT_SEASON) "current" else ergast.season.toString()).replace("{LIMIT}", ergast.getLimit().toString()).replace("{OFFSET}", ergast.getOffset().toString()).replace("{REQUEST}", request).replace(ergast.series + "//", ergast.series + "/").replace("{ROUND}/", if (round == Ergast.NO_ROUND) "" else round.toString() + "/").replace("/.json", ".json")

        Log.d(TAG, "Build url: " + url)

        return url
    }

    /**
     * Open connection to ergast api and download json in according with parameters.
     *
     * @param ergast  ergast param
     * @param request request
     * @param round   round
     * @return json result, null on error
     */
    fun getJson(ergast: Ergast, request: String, round: Int): String? {

        val url = buildUrl(ergast, request, round)
        try {
            val obj = URL(url)
            val httpURLConnection = obj.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT)

            val response = StringBuilder()
            BufferedReader(InputStreamReader(httpURLConnection.inputStream)).use { `in` ->
                var inputLine: String? = null;

                while ({ inputLine = `in`.readLine(); inputLine }() != null) response.append(inputLine)
            }

            return response.toString()
        } catch (e: Exception) {
            showServiceError()
        }

        return null
    }

    private fun showServiceError() {
        Thread(Runnable {
            Looper.prepare()
            Toast.makeText(context, context!!.resources.getString(R.string.service_error),
                    Toast.LENGTH_SHORT).show()

            Looper.loop()
        }).start()
    }

    companion object {

        private val TAG = ErgastConnection::class.java.simpleName
    }
}
