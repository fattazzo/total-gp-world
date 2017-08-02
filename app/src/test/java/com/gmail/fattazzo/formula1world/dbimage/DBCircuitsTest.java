package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.activeandroid.util.ReflectionUtils;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBCircuitsTest {

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
        int circuits = new Select().from(Circuit.class).count();
        assertNotEquals("No circuits in DB", 0, circuits);
        assertTrue("Total circuits < 72", circuits > 72);
        System.out.println("Circuits found: " + circuits + " OK");
    }

    @Test
    public void testMonza() {
        Circuit circuitMonza = new Select().from(Circuit.class).where("circuitRef = ?", "monza").executeSingle();
        assertNotNull("Circuit 'monza' not found in DB", circuitMonza);
        assertEquals("Wrong circuit loaded", "monza", circuitMonza.toF1Circuit().circuitRef);
        assertEquals("Wrong circuit loaded", "Autodromo Nazionale di Monza", circuitMonza.toF1Circuit().name);
        assertEquals("Wrong circuit loaded", "Italy", circuitMonza.toF1Circuit().location.country);
        System.out.println("Circuit 'monza' OK");
    }

    @Test
    public void testRedBullRing() {
        Circuit circuitRedBullRing = new Select().from(Circuit.class).where("circuitRef = ?", "red_bull_ring").executeSingle();
        assertNotNull("Circuit 'red_bull_ring' not found in DB", circuitRedBullRing);
        assertEquals("Wrong circuit loaded", "red_bull_ring", circuitRedBullRing.toF1Circuit().circuitRef);
        assertEquals("Wrong circuit loaded", "Red Bull Ring", circuitRedBullRing.toF1Circuit().name);
        assertEquals("Wrong circuit loaded", "Austria", circuitRedBullRing.toF1Circuit().location.country);
        System.out.println("Circuit 'red_bull_ring' OK");
    }
}
