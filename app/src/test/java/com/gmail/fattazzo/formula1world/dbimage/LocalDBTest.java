package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.query.Select;
import com.activeandroid.util.ReflectionUtils;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorColors;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDBTest {

    @Before
    public void initDB() {
        Cache.clear();
        ActiveAndroid.dispose();

        String aaName = ReflectionUtils.getMetaData(RuntimeEnvironment.application, "AA_DB_NAME");

        RuntimeEnvironment.application.deleteDatabase(aaName);
        ActiveAndroid.initialize(RuntimeEnvironment.application);
    }

    @Test
    public void testDrivers() {
        int drivers = new Select().from(Driver.class).count();
        assertNotEquals("No drivers in DB", 0, drivers);
        System.out.println("Drivers found: " + drivers + " OK");

        Driver seb = new Select().from(Driver.class).where("surname = ?", "Vettel").executeSingle();
        assertNotNull("Driver Vettel not found in DB", seb);
        assertEquals("Wrong driver loaded", "Sebastian", seb.forename);
        assertEquals("Wrong driver loaded", "VET", seb.code);
        System.out.println("Driver: " + seb.toF1Driver().getFullName() + " OK");

        Driver farina = new Select().from(Driver.class).where("surname = ?", "Farina").executeSingle();
        assertNotNull("Driver Farina not found in DB", farina);
        assertEquals("Wrong driver loaded", "Nino", farina.forename);
        System.out.println("Driver: " + farina.toF1Driver().getFullName() + " OK");
    }

    @Test
    public void testSeasons() {
        int seasons = new Select().from(Season.class).count();
        assertNotEquals("No seasons in DB", 0, seasons);
        System.out.println("Seasons found: " + seasons + " OK");

        Season season1950 = new Select().from(Season.class).where("id = ?", 1950).executeSingle();

        assertNotNull("Season 1950 not found in DB", season1950);
        assertEquals("Wrong season loaded", 1950, season1950.getId().intValue());
        System.out.println("Seasons 1950 OK");
    }

    @Test
    public void testCricuits() {
        int circuits = new Select().from(Circuit.class).count();
        assertNotEquals("No circuits in DB", 0, circuits);
        System.out.println("Circuits found: " + circuits + " OK");

        Circuit circuitMonza = new Select().from(Circuit.class).where("circuitRef = ?","monza").executeSingle();
        assertNotNull("Circuit 'monza' not found in DB", circuitMonza);
        assertEquals("Wrong circuit loaded", "monza", circuitMonza.toF1Circuit().circuitRef);
        assertEquals("Wrong circuit loaded", "Autodromo Nazionale di Monza", circuitMonza.toF1Circuit().name);
        assertEquals("Wrong circuit loaded", "Italy", circuitMonza.toF1Circuit().location.country);
        System.out.println("Circuit 'monza' OK");

        Circuit circuitRedBullRing = new Select().from(Circuit.class).where("circuitRef = ?","red_bull_ring").executeSingle();
        assertNotNull("Circuit 'red_bull_ring' not found in DB", circuitRedBullRing);
        assertEquals("Wrong circuit loaded", "red_bull_ring", circuitRedBullRing.toF1Circuit().circuitRef);
        assertEquals("Wrong circuit loaded", "Red Bull Ring", circuitRedBullRing.toF1Circuit().name);
        assertEquals("Wrong circuit loaded", "Austria", circuitRedBullRing.toF1Circuit().location.country);
        System.out.println("Circuit 'red_bull_ring' OK");
    }

    @Test
    public void testConstructorColors() {
        int colors = new Select().from(ConstructorColors.class).count();
        assertNotEquals("No contructor colors in DB", 0, colors);
        System.out.println("Constructor colors found: " + colors + " OK");

        List<ConstructorColors> ferrariColors = new Select().from(ConstructorColors.class).where("constructorId = ?","6").where("year = ?",2017).execute();
        assertNotNull("Ferrari constructor colors not found in DB", ferrariColors);
        assertEquals("Ferrari constructor colors not found in DB", 2,ferrariColors.size());

        for (ConstructorColors cc: ferrariColors) {
            assertEquals("Constructor not valid for " + cc.driver.forename, "ferrari",cc.constructor.toF1Constructor().constructorRef);
            assertEquals("Year not valid for " + cc.driver.forename, 2017,cc.year);
            assertEquals("Color not valid for " + cc.driver.forename, "#c30000",ferrariColors.get(0).hex);

            System.out.println("Constructor colors for " + cc.driver.forename + " OK");
        }

    }
}
