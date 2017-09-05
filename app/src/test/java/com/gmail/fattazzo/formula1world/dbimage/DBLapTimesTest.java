package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.LapTime;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBLapTimesTest extends BaseDBTest {

    @Test
    public void testCount() {
        int totalLapTimes = new Select().from(LapTime.class).count();
        assertTrue("LapTime count < 417000", totalLapTimes >= 417000);
        System.out.println(totalLapTimes + " LapTime found");
    }

    @Test
    public void testRace841() {
        int lapRace841 = new Select().from(LapTime.class).where("raceId = ?", 841).count();
        assertEquals("Wrong lap times for race 841", 1083, lapRace841);
        System.out.println(lapRace841 + " LapTime for race 841");
    }

    @Test
    public void test2016() {
        int laptimes2016 = new Select("lt.*").from(LapTime.class).as("lt")
                .innerJoin(Race.class).as("race").on("race.Id = lt.raceId")
                .where("race.year = ?", 2016).count();
        assertEquals("Wrong lap times for year 2016", 24513, laptimes2016);
        System.out.println(laptimes2016 + " LapTime for year 2016");
    }
}
