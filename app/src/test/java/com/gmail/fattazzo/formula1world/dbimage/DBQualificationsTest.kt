package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Qualification
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
class DBQualificationsTest : BaseDBTest() {

    @Test
    fun testCount() {
        val total = Select().from(Qualification::class.java).count()
        assertTrue("Qualification count < 7337", total >= 7337)
        println(total.toString() + " Qualification found")
    }

    @Test
    fun testRace841() {
        val qual841 = Select().from(Qualification::class.java).where("raceId = ?", 841).count()
        assertEquals("Wrong Qualification for race 841", 24, qual841.toLong())
        println(qual841.toString() + " Qualification for race 841")
    }

    @Test
    fun test2016() {
        val qual2016 = Select("qual.*").from(Qualification::class.java).`as`("qual")
                .innerJoin(Race::class.java).`as`("race").on("race.Id = qual.raceId")
                .where("race.year = ?", 2016).count()
        assertEquals("Wrong Qualification for year 2016", 457, qual2016.toLong())
        println(qual2016.toString() + " Qualification for year 2016")
    }
}
