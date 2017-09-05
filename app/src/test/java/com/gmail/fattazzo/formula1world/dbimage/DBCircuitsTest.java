package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
public class DBCircuitsTest extends BaseDBTest {

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

    @Test
    public void testAll() {
        List<Circuit> circuits = new Select().from(Circuit.class).execute();
        assertNotNull("No circuits loaded", circuits);
        assertNotEquals("0 circuits found", 0, circuits.size());

        for (Circuit circuit : circuits) {
            assertNotNull("Null name for circuit " + circuit.circuitRef, circuit.name);
            assertTrue("Wrong latitude for circuit " + circuit.name, circuit.lat != 0);
            assertTrue("Wrong longitude for circuit " + circuit.name, circuit.lng != 0);
            assertNotNull("Null location for circuit " + circuit.name, circuit.location);
            System.out.println("Circuit " + circuit.name + " ok");
        }

    }
}
