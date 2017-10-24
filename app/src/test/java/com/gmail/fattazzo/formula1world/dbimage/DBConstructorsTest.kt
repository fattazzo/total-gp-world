package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorColors
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorStandings

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

/**
 * @author fattazzo
 *
 *
 * date: 16/06/17
 */
@RunWith(CustomRobolectricRunner::class)
class DBConstructorsTest : BaseDBTest() {

    @Test
    fun testConstructorColors() {
        val colors = Select().from(ConstructorColors::class.java).count()
        assertNotEquals("No contructor colors in DB", 0, colors.toLong())
        println("Constructor colors found: $colors OK")

        val ferrariColors = Select().from(ConstructorColors::class.java).where("constructorId = ?", "6").where("year = ?", 2017).execute<ConstructorColors>()
        assertNotNull("Ferrari constructor colors not found in DB", ferrariColors)
        assertEquals("Ferrari constructor colors not found in DB", 2, ferrariColors.size.toLong())

        for (cc in ferrariColors) {
            assertEquals("Constructor not valid for " + cc.driver!!.forename!!, "ferrari", cc.constructor!!.toF1Constructor().constructorRef)
            assertEquals("Year not valid for " + cc.driver!!.forename!!, 2017, cc.year.toLong())
            assertEquals("Color not valid for " + cc.driver!!.forename!!, "#c30000", ferrariColors[0].hex)

            println("Constructor colors for " + cc.driver!!.forename + " OK")
        }
    }

    @Test
    fun testConstructorStandings() {
        val totalResults = Select().from(ConstructorStandings::class.java).count()
        assertTrue("ConstructorStandings count < 11800", totalResults > 11800)
        println(totalResults.toString() + " ConstructorStandings found")
    }

    @Test
    fun testConstructors() {
        val constructors = Select().from(Constructor::class.java).count()
        assertNotEquals("No constructors in DB", 0, constructors.toLong())
        assertTrue("Total constructors < 208", constructors >= 208)
        println("Constructors found: $constructors OK")

        val ferrari = Select().from(Constructor::class.java).where("constructorRef = ?", "ferrari").executeSingle<Constructor>()
        assertNotNull("Constructor Ferrari not found in DB", ferrari)
        assertEquals("Wrong constructor loaded", "Ferrari", ferrari.name)
        assertEquals("Wrong constructor loaded", "Italian", ferrari.nationality)
        println("Constructor: " + ferrari.name + " OK")

        val haas = Select().from(Constructor::class.java).where("constructorRef = ?", "haas").executeSingle<Constructor>()
        assertNotNull("Constructor Haas F1 Team not found in DB", haas)
        assertEquals("Wrong constructor loaded", "Haas F1 Team", haas.name)
        assertEquals("Wrong constructor loaded", "American", haas.nationality)
        println("Constructor: " + haas.name + " OK")
    }
}
