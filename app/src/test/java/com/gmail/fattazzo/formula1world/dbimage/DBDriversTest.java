package com.gmail.fattazzo.formula1world.dbimage;

import android.util.SparseIntArray;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverConstructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings;

import org.junit.Test;
import org.junit.runner.RunWith;

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
public class DBDriversTest extends BaseDBTest {

    @Test
    public void testDrivers() {
        int drivers = new Select().from(Driver.class).count();
        assertNotEquals("No drivers in DB", 0, drivers);
        assertTrue("Total drivers < 840", drivers >= 840);
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
    public void testDriverConstructors() {
        int totalDrivers = new Select().from(DriverConstructor.class).where("year <= ?", 2017).count();
        assertEquals("DriverConstructor count not match", 3396, totalDrivers);
        System.out.println(totalDrivers + " DriverConstructor found");

        SparseIntArray driversNr = new SparseIntArray();
        driversNr.put(2000, 23);
        driversNr.put(2001, 29);
        driversNr.put(2002, 24);
        driversNr.put(2003, 25);
        driversNr.put(2004, 26);
        driversNr.put(2005, 27);
        driversNr.put(2006, 29);
        driversNr.put(2007, 27);
        driversNr.put(2008, 22);
        driversNr.put(2009, 26);
        driversNr.put(2010, 27);
        driversNr.put(2011, 28);
        driversNr.put(2012, 25);
        driversNr.put(2013, 23);
        driversNr.put(2014, 24);
        driversNr.put(2015, 22);
        driversNr.put(2016, 26);
        driversNr.put(2017, 24);
        for (int i = 2000; i <= 2017; i++) {
            int yearDrivers = new Select().from(DriverConstructor.class).where("year = ?", i).count();
            assertEquals("DriverConstructor count not match for year " + String.valueOf(i), driversNr.get(i), yearDrivers);
            System.out.println("DriverConstructor ok for year " + i);
        }
    }

    @Test
    public void testDriverStandings() {
        int totalResults = new Select().from(DriverStandings.class).count();
        assertTrue("DriverStandings count < 31500", totalResults >= 31500);
        System.out.println(totalResults + " DriverStandings found");
    }
}
