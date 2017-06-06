package com.gmail.fattazzo.formula1world.ergast.json;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.Ergast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.gmail.fattazzo.formula1world.ergast.json.config.ErgastConfig.BASE_REQ;
import static com.gmail.fattazzo.formula1world.ergast.json.config.ErgastConfig.USER_AGENT;

/**
 * @author fattazzo
 *         <p/>
 *         date: 14/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
class ErgastConnection {

    private static final String TAG = ErgastConnection.class.getSimpleName();

    @RootContext
    Context context;

    /**
     * Build url for connection.
     *
     * @param ergast  ergast definition
     * @param request request resource
     * @param round   round
     * @return url
     */
    @NonNull
    private String buildUrl(@NonNull Ergast ergast, @NonNull String request, int round) {
        String url = BASE_REQ.
                replace("{SERIES}", ergast.getSeries()).
                replace("{SEASON}", ergast.getSeason() == Ergast.CURRENT_SEASON ? "current" : String.valueOf(ergast.getSeason())).
                replace("{LIMIT}", String.valueOf(ergast.getLimit())).
                replace("{OFFSET}", String.valueOf(ergast.getOffset())).
                replace("{REQUEST}", request).
                replace(ergast.getSeries() + "//", ergast.getSeries() + "/").
                replace("{ROUND}/", round == Ergast.NO_ROUND ? "" : String.valueOf(round) + "/").
                replace("/.json", ".json");

        Log.d(TAG, "Build url: " + url);

        return url;
    }

    /**
     * Open connection to ergast api and download json in according with parameters.
     *
     * @param ergast  ergast param
     * @param request request
     * @param round   round
     * @return json result, null on error
     */
    @Nullable
    String getJson(@NonNull Ergast ergast, @NonNull String request, int round) {

        String url = buildUrl(ergast, request, round);
        try {
            URL obj = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);

            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();
        } catch (Exception e) {
            showServiceError();
        }

        return null;
    }

    private void showServiceError() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, context.getResources().getString(R.string.service_error),
                        Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        }).start();
    }
}
