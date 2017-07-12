package com.gmail.fattazzo.formula1world.ergast.json.parser;

import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser<T> {
    private static Pattern timePattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})");

    private String json;
    private String[] jsonObjects;
    private Class<T> type;

    public Parser(String json, String[] jsonObjects, Class<T> type) {
        this.json = json;
        this.jsonObjects = jsonObjects;
        this.type = type;
    }

    /**
     * Parse time string to milliseconds.
     * Accepted format: ss.SSS , mm:ss.SSS , hh:mm:ss.SSS
     *
     * @param timeString time string format
     * @return milliseconds
     */
    public static int parseTimeToMillisec(String timeString) {
        int milliseconds = 0;

        if (StringUtils.isNotBlank(timeString)) {
            int millisec;

            String[] timeArray = new String[4];
            timeArray[0] = "00";
            timeArray[1] = "00";
            timeArray[2] = "00";
            timeArray[3] = "000";

            String[] split1 = StringUtils.split(timeString, '.');
            timeArray[3] = StringUtils.leftPad(split1[1],3,'0');

            String[] split2 = StringUtils.split(split1[0], ':');

            timeArray[2] = split2[split2.length - 1];
            if (split2.length > 1) {
                timeArray[1] = StringUtils.leftPad(split2[split2.length - 2].trim(),2,'0');
            }
            if (split2.length > 2) {
                timeArray[0] = StringUtils.leftPad(split2[split2.length - 3].trim(),2,'0');
            }

            Matcher matcher = timePattern.matcher(timeArray[0] + ":" + timeArray[1] + ":" + timeArray[2] + "." + timeArray[3]);
            if (matcher.matches()) {
                millisec = Integer.parseInt(matcher.group(1)) * 3600000
                        + Integer.parseInt(matcher.group(2)) * 60000
                        + Integer.parseInt(matcher.group(3)) * 1000
                        + Integer.parseInt(matcher.group(4));
            } else {
                throw new IllegalArgumentException("Invalid time format");
            }
            milliseconds = millisec;
        }

        return milliseconds;
    }

    @NonNull
    public List<T> parse() {
        List<T> entities = new ArrayList<>();

        if (StringUtils.isNotBlank(json)) {
            fixJson();
            JsonArray jarray = getJsonArray();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setExclusionStrategies(new FieldExclusionStrategy()).create();
            for (int i = 0; i < jarray.size(); i++) {
                entities.add(gson.fromJson(jarray.get(i).getAsJsonObject(), type));
            }
        }

        return entities;
    }

    private JsonArray getJsonArray() {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("MRData");

        if (type == RaceResult.class || type == Qualification.class
                || type == DriverStandings.class || type == ConstructorStandings.class) {
            for (int i = 0; i < jsonObjects.length - 2; i++) {
                jobject = jobject.getAsJsonObject(jsonObjects[i]);
            }
            jobject = jobject.getAsJsonArray(jsonObjects[jsonObjects.length - 2]).get(0).getAsJsonObject();
        } else {
            for (int i = 0; i < jsonObjects.length - 1; i++) {
                jobject = jobject.getAsJsonObject(jsonObjects[i]);
            }
        }
        return jobject.getAsJsonArray(jsonObjects[jsonObjects.length - 1]);
    }

    private void fixJson() {
        json = json.
                replace("\"Location\"", "\"location\"").
                replace("\"Circuit\"", "\"circuit\"").
                replace("\"Constructor\"", "\"constructor\"").
                replace("\"Driver\"", "\"driver\"").
                replace("\"Time\"", "\"time\"").
                replace("\"AverageSpeed\"", "\"averageSpeed\"").
                replace("\"FastestLap\"", "\"fastestLap\"").
                replace("\"Q1\"", "\"q1\"").
                replace("\"Q2\"", "\"q2\"").
                replace("\"Q3\"", "\"q3\"").
                replace("\"Constructors\"", "\"constructors\"").
                replace("\"Laps\"", "\"laps\"").
                replace("\"Timings\"", "\"timings\"").
                replace("\"PitStops\"", "\"pitStops\"").
                replace("\"Results\"", "\"results\"");
    }
}
