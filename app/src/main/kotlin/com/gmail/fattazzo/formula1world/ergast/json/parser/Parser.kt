/*
 * Project: total-gp-world
 * File: Parser.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.ergast.json.parser

import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings
import com.gmail.fattazzo.formula1world.ergast.json.objects.DriverStandings
import com.gmail.fattazzo.formula1world.ergast.json.objects.Qualification
import com.gmail.fattazzo.formula1world.ergast.json.objects.RaceResult
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.apache.commons.lang3.StringUtils
import java.util.*
import java.util.regex.Pattern

class Parser<T>(private var json: String?, private val jsonObjects: Array<String>, private val type: Class<T>) {

    private val jsonArray: JsonArray
        get() {
            val jelement = JsonParser().parse(json)
            var jobject = jelement.asJsonObject
            jobject = jobject.getAsJsonObject("MRData")

            if (type == RaceResult::class.java || type == Qualification::class.java
                    || type == DriverStandings::class.java || type == ConstructorStandings::class.java) {
                for (i in 0 until jsonObjects.size - 2) {
                    jobject = jobject.getAsJsonObject(jsonObjects[i])
                }
                jobject = jobject.getAsJsonArray(jsonObjects[jsonObjects.size - 2]).get(0).asJsonObject
            } else {
                for (i in 0 until jsonObjects.size - 1) {
                    jobject = jobject.getAsJsonObject(jsonObjects[i])
                }
            }
            return jobject.getAsJsonArray(jsonObjects[jsonObjects.size - 1])
        }

    fun parse(): List<T> {
        val entities = ArrayList<T>()

        if (StringUtils.isNotBlank(json)) {
            fixJson()
            val jarray = jsonArray
            val builder = GsonBuilder()
            val gson = builder.setExclusionStrategies(FieldExclusionStrategy()).create()
            for (i in 0 until jarray.size()) {
                entities.add(gson.fromJson(jarray.get(i).asJsonObject, type))
            }
        }

        return entities
    }

    private fun fixJson() {
        json = json!!.replace("\"Location\"", "\"location\"")
                .replace("\"Circuit\"", "\"circuit\"")
                .replace("\"Constructor\"", "\"constructor\"")
                .replace("\"Driver\"", "\"driver\"")
                .replace("\"Time\"", "\"time\"")
                .replace("\"AverageSpeed\"", "\"averageSpeed\"")
                .replace("\"FastestLap\"", "\"fastestLap\"")
                .replace("\"Q1\"", "\"q1\"")
                .replace("\"Q2\"", "\"q2\"")
                .replace("\"Q3\"", "\"q3\"")
                .replace("\"Constructors\"", "\"constructors\"")
                .replace("\"Laps\"", "\"laps\"")
                .replace("\"Timings\"", "\"timings\"")
                .replace("\"PitStops\"", "\"pitStops\"")
                .replace("\"Results\"", "\"results\"")
    }

    companion object {
        private val timePattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})")

        /**
         * Parse time string to milliseconds.
         * Accepted format: ss.SSS , mm:ss.SSS , hh:mm:ss.SSS
         *
         * @param timeString time string format
         * @return milliseconds
         */
        fun parseTimeToMillisec(timeString: String?): Int {
            var milliseconds = 0

            if (StringUtils.isNotBlank(timeString)) {
                val millisec: Int

                val timeArray = arrayOfNulls<String>(4)
                timeArray[0] = "00"
                timeArray[1] = "00"
                timeArray[2] = "00"
                timeArray[3] = "000"

                val split1 = StringUtils.split(timeString, '.')
                timeArray[3] = StringUtils.leftPad(split1[1], 3, '0')

                val split2 = StringUtils.split(split1[0], ':')

                timeArray[2] = split2[split2.size - 1]
                if (split2.size > 1) {
                    timeArray[1] = StringUtils.leftPad(split2[split2.size - 2].trim { it <= ' ' }, 2, '0')
                }
                if (split2.size > 2) {
                    timeArray[0] = StringUtils.leftPad(split2[split2.size - 3].trim { it <= ' ' }, 2, '0')
                }

                val matcher = timePattern.matcher(timeArray[0] + ":" + timeArray[1] + ":" + timeArray[2] + "." + timeArray[3])
                if (matcher.matches()) {
                    millisec = (Integer.parseInt(matcher.group(1)) * 3600000
                            + Integer.parseInt(matcher.group(2)) * 60000
                            + Integer.parseInt(matcher.group(3)) * 1000
                            + Integer.parseInt(matcher.group(4)))
                } else {
                    throw IllegalArgumentException("Invalid time format")
                }
                milliseconds = millisec
            }

            return milliseconds
        }
    }
}
