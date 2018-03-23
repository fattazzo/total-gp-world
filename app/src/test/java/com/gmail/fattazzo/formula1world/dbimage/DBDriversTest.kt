package com.gmail.fattazzo.formula1world.dbimage

import android.util.SparseIntArray
import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverConstructor
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author fattazzo
 *
 *
 * date: 16/06/17
 */
@RunWith(CustomRobolectricRunner::class)
class DBDriversTest : BaseDBTest() {

    @Test
    fun testDrivers() {
        val drivers = Select().from(Driver::class.java).count()
        assertNotEquals("No drivers in DB", 0, drivers.toLong())
        assertTrue("Total drivers < 840", drivers >= 840)
        println("Drivers found: $drivers OK")

        val seb = Select().from(Driver::class.java).where("surname = ?", "Vettel").executeSingle<Driver>()
        assertNotNull("Driver Vettel not found in DB", seb)
        assertEquals("Wrong driver loaded", "Sebastian", seb.forename)
        assertEquals("Wrong driver loaded", "VET", seb.code)
        println("Driver: " + seb.toF1Driver().fullName + " OK")

        val farina = Select().from(Driver::class.java).where("surname = ?", "Farina").executeSingle<Driver>()
        assertNotNull("Driver Farina not found in DB", farina)
        assertEquals("Wrong driver loaded", "Nino", farina.forename)
        println("Driver: " + farina.toF1Driver().fullName + " OK")
    }

    @Test
    fun testDriverConstructors() {
        val totalDrivers = Select().from(DriverConstructor::class.java).where("year <= ?", 2017).count()
        assertEquals("DriverConstructor count not match", 3398, totalDrivers.toLong())
        println(totalDrivers.toString() + " DriverConstructor found")

        val driversNr = SparseIntArray()
        driversNr.put(2000, 23)
        driversNr.put(2001, 29)
        driversNr.put(2002, 24)
        driversNr.put(2003, 25)
        driversNr.put(2004, 26)
        driversNr.put(2005, 27)
        driversNr.put(2006, 29)
        driversNr.put(2007, 27)
        driversNr.put(2008, 22)
        driversNr.put(2009, 26)
        driversNr.put(2010, 27)
        driversNr.put(2011, 28)
        driversNr.put(2012, 25)
        driversNr.put(2013, 23)
        driversNr.put(2014, 24)
        driversNr.put(2015, 22)
        driversNr.put(2016, 26)
        driversNr.put(2017, 26)
        for (i in 2000..2017) {
            val yearDrivers = Select().from(DriverConstructor::class.java).where("year = ?", i).count()
            assertEquals("DriverConstructor count not match for year " + i.toString(), driversNr.get(i).toLong(), yearDrivers.toLong())
            println("DriverConstructor ok for year " + i)
        }
    }

    @Test
    fun testDriverStandings() {
        val totalResults = Select().from(DriverStandings::class.java).count()
        assertTrue("DriverStandings count < 31500", totalResults >= 31500)
        println(totalResults.toString() + " DriverStandings found")
    }
}
