package com.gmail.fattazzo.formula1world.dbimage

import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit

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
class DBCircuitsTest : BaseDBTest() {

    @Test
    fun testCount() {
        val circuits = Select().from(Circuit::class.java).count()
        assertNotEquals("No circuits in DB", 0, circuits.toLong())
        assertTrue("Total circuits < 72", circuits > 72)
        println("Circuits found: $circuits OK")
    }

    @Test
    fun testMonza() {
        val circuitMonza = Select().from(Circuit::class.java).where("circuitRef = ?", "monza").executeSingle<Circuit>()
        assertNotNull("Circuit 'monza' not found in DB", circuitMonza)
        assertEquals("Wrong circuit loaded", "monza", circuitMonza.toF1Circuit().circuitRef)
        assertEquals("Wrong circuit loaded", "Autodromo Nazionale di Monza", circuitMonza.toF1Circuit().name)
        assertEquals("Wrong circuit loaded", "Italy", circuitMonza.toF1Circuit().location!!.country)
        println("Circuit 'monza' OK")
    }

    @Test
    fun testRedBullRing() {
        val circuitRedBullRing = Select().from(Circuit::class.java).where("circuitRef = ?", "red_bull_ring").executeSingle<Circuit>()
        assertNotNull("Circuit 'red_bull_ring' not found in DB", circuitRedBullRing)
        assertEquals("Wrong circuit loaded", "red_bull_ring", circuitRedBullRing.toF1Circuit().circuitRef)
        assertEquals("Wrong circuit loaded", "Red Bull Ring", circuitRedBullRing.toF1Circuit().name)
        assertEquals("Wrong circuit loaded", "Austria", circuitRedBullRing.toF1Circuit().location!!.country)
        println("Circuit 'red_bull_ring' OK")
    }

    @Test
    fun testAll() {
        val circuits = Select().from(Circuit::class.java).execute<Circuit>()
        assertNotNull("No circuits loaded", circuits)
        assertNotEquals("0 circuits found", 0, circuits.size.toLong())

        for (circuit in circuits) {
            assertNotNull("Null name for circuit " + circuit.circuitRef!!, circuit.name)
            assertTrue("Wrong latitude for circuit " + circuit.name!!, circuit.lat != 0f)
            assertTrue("Wrong longitude for circuit " + circuit.name!!, circuit.lng != 0f)
            assertNotNull("Null location for circuit " + circuit.name!!, circuit.location)
            println("Circuit " + circuit.name + " ok")
        }

    }
}
