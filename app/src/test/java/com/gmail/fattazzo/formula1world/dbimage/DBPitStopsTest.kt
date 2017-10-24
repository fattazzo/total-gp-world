package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.PitStop
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
class DBPitStopsTest : BaseDBTest() {

    @Test
    fun testCount() {
        val totalPit = Select().from(PitStop::class.java).count()
        assertTrue("PitStop count < 5900", totalPit >= 5900)
        println(totalPit.toString() + " PitStop found")
    }

    @Test
    fun testRace841() {
        val pitRace841 = Select().from(PitStop::class.java).where("raceId = ?", 841).count()
        assertEquals("Wrong PitStop for race 841", 45, pitRace841.toLong())
        println(pitRace841.toString() + " PitStop for race 841")
    }

    @Test
    fun test2016() {
        val pits2016 = Select("ps.*").from(PitStop::class.java).`as`("ps")
                .innerJoin(Race::class.java).`as`("race").on("race.Id = ps.raceId")
                .where("race.year = ?", 2016).count()
        assertEquals("Wrong PitStop for year 2016", 989, pits2016.toLong())
        println(pits2016.toString() + " PitStop for year 2016")
    }
}
