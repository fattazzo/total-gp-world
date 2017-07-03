package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1PitStop;
import com.gmail.fattazzo.formula1world.domain.F1Race;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class PitStop {
    private static Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})");

    private String driverId;
    private int stop;
    private int lap;
    private String time;
    private String duration;

    public F1PitStop toF1PitStop(F1Race race, List<F1Driver> drivers) {
        F1PitStop f1PitStop = new F1PitStop();
        f1PitStop.race = race;
        F1Driver f1Driver = new F1Driver();
        f1Driver.driverRef = driverId;
        if (drivers.contains(f1Driver)) {
            f1PitStop.driver = drivers.get(drivers.indexOf(f1Driver));
        }
        f1PitStop.duration = duration;
        f1PitStop.lap = lap;
        f1PitStop.stop = stop;
        f1PitStop.time = time;

        f1PitStop.milliseconds = 0;
        if(StringUtils.isNotBlank(duration)) {
            int millisec;

            String[] timeArray = new String[4];
            timeArray[0] = "00";
            timeArray[1] = "00";
            timeArray[2] = "00";
            timeArray[3] = "000";

            // format from json ss.SSS or mm:ss.SSS or hh:mm:ss.SSS
            String[] split1 = StringUtils.split(duration,'.');
            timeArray[3] = split1[1];

            String[] split2 = StringUtils.split(split1[0],':');

            timeArray[2] = split2[split2.length-1];
            if(split2.length > 1) {
                timeArray[1] = split2[split2.length-2];
            }
            if(split2.length > 2) {
                timeArray[0] = split2[split2.length-3];
            }

            Matcher matcher = pattern.matcher(timeArray[0] + ":" + timeArray[1] + ":" + timeArray[2] + "." + timeArray[3]);
            if (matcher.matches()) {
                millisec = Integer.parseInt(matcher.group(1)) * 3600000
                        + Integer.parseInt(matcher.group(2)) * 60000
                        + Integer.parseInt(matcher.group(3)) * 1000
                        + Integer.parseInt(matcher.group(4));
            } else {
                throw new IllegalArgumentException("Invalid time format");
            }
            f1PitStop.milliseconds = millisec;
        }
        return f1PitStop;
    }

    @Override
    public String toString() {
        return "PitStop{" +
                "driverId='" + driverId + '\'' +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
