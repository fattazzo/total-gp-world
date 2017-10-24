package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.LapTime
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

/**
 * @author fattazzo
 *
 *
 * date: 16/06/17
 */
@RunWith(CustomRobolectricRunner::class)
class DBLapTimesTest : BaseDBTest() {

    @Test
    fun testCount() {
        val totalLapTimes = Select().from(LapTime::class.java).count()
        assertTrue("LapTime count < 417000", totalLapTimes >= 417000)
        println(totalLapTimes.toString() + " LapTime found")
    }

    @Test
    fun testRace841() {
        val lapRace841 = Select().from(LapTime::class.java).where("raceId = ?", 841).count()
        assertEquals("Wrong lap times for race 841", 1083, lapRace841.toLong())
        println(lapRace841.toString() + " LapTime for race 841")
    }

    @Test
    fun test2016() {
        val laptimes2016 = Select("lt.*").from(LapTime::class.java).`as`("lt")
                .innerJoin(Race::class.java).`as`("race").on("race.Id = lt.raceId")
                .where("race.year = ?", 2016).count()
        assertEquals("Wrong lap times for year 2016", 24513, laptimes2016.toLong())
        println(laptimes2016.toString() + " LapTime for year 2016")
    }
}
