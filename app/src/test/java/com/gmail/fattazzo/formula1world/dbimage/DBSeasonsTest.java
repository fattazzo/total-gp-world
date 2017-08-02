package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.activeandroid.util.ReflectionUtils;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBSeasonsTest {

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
        int seasons = new Select().from(Season.class).count();
        assertNotEquals("No seasons in DB", 0, seasons);
        System.out.println("Seasons found: " + seasons + " OK");
    }

    @Test
    public void test1950() {
        Season season1950 = new Select().from(Season.class).where("id = ?", 1950).executeSingle();

        assertNotNull("Season 1950 not found in DB", season1950);
        assertEquals("Wrong season loaded", 1950, season1950.getId().intValue());
        System.out.println("Season 1950 OK");
    }

    @Test
    public void testAll() {
        Season lastSeason = new Select().from(Season.class).orderBy("id desc").limit(1).executeSingle();

        for (int i = 1950; i <= lastSeason.getId(); i++) {
            Season season = new Select().from(Season.class).where("id = ?", i).executeSingle();
            assertNotNull("Season " + i + " not found in DB", season);
            assertEquals("Wrong season " + i + " loaded", i, season.getId().intValue());
            System.out.println("Season " + i + " OK");
        }
    }
}
