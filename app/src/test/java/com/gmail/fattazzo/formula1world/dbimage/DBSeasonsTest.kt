package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull

/**
 * @author fattazzo
 *
 *
 * date: 16/06/17
 */
@RunWith(CustomRobolectricRunner::class)
class DBSeasonsTest : BaseDBTest() {

    @Test
    fun testCount() {
        val seasons = Select().from(Season::class.java).count()
        assertNotEquals("No seasons in DB", 0, seasons.toLong())
        println("Seasons found: $seasons OK")
    }

    @Test
    fun test1950() {
        val season1950 = Select().from(Season::class.java).where("id = ?", 1950).executeSingle<Season>()

        assertNotNull("Season 1950 not found in DB", season1950)
        assertEquals("Wrong season loaded", 1950, season1950.id!!.toInt().toLong())
        println("Season 1950 OK")
    }

    @Test
    fun testAll() {
        val lastSeason = Select().from(Season::class.java).orderBy("id desc").limit(1).executeSingle<Season>()

        for (i in 1950..lastSeason.id) {
            val season = Select().from(Season::class.java).where("id = ?", i).executeSingle<Season>()
            assertNotNull("Season $i not found in DB", season)
            assertEquals("Wrong season $i loaded", i.toLong(), season.id!!.toInt().toLong())
            println("Season $i OK")
        }
    }
}
