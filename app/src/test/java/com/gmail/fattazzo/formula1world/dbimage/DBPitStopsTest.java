package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.activeandroid.util.ReflectionUtils;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.PitStop;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBPitStopsTest {

    @Before
    public void initDB() {
        Cache.clear();
        ActiveAndroid.dispose();

        String aaName = ReflectionUtils.getMetaData(RuntimeEnvironment.application, "AA_DB_NAME");

        RuntimeEnvironment.application.deleteDatabase(aaName);
        ActiveAndroid.initialize(RuntimeEnvironment.application);
    }

    @Test
    public void testCount() {
        int totalPit = new Select().from(PitStop.class).count();
        assertTrue("PitStop count < 5900", totalPit >= 5900);
        System.out.println(totalPit + " PitStop found");
    }

    @Test
    public void testRace841() {
        int pitRace841 = new Select().from(PitStop.class).where("raceId = ?", 841).count();
        assertEquals("Wrong PitStop for race 841", 45, pitRace841);
        System.out.println(pitRace841 + " PitStop for race 841");
    }

    @Test
    public void test2016() {
        int pits2016 = new Select("ps.*").from(PitStop.class).as("ps")
                .innerJoin(Race.class).as("race").on("race.Id = ps.raceId")
                .where("race.year = ?", 2016).count();
        assertEquals("Wrong PitStop for year 2016", 989, pits2016);
        System.out.println(pits2016 + " PitStop for year 2016");
    }
}
