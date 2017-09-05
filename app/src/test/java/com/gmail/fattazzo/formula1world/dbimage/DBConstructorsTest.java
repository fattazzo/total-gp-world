package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorColors;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorStandings;

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
public class DBConstructorsTest extends BaseDBTest {

    @Test
    public void testConstructorColors() {
        int colors = new Select().from(ConstructorColors.class).count();
        assertNotEquals("No contructor colors in DB", 0, colors);
        System.out.println("Constructor colors found: " + colors + " OK");

        List<ConstructorColors> ferrariColors = new Select().from(ConstructorColors.class).where("constructorId = ?", "6").where("year = ?", 2017).execute();
        assertNotNull("Ferrari constructor colors not found in DB", ferrariColors);
        assertEquals("Ferrari constructor colors not found in DB", 2, ferrariColors.size());

        for (ConstructorColors cc : ferrariColors) {
            assertEquals("Constructor not valid for " + cc.driver.forename, "ferrari", cc.constructor.toF1Constructor().constructorRef);
            assertEquals("Year not valid for " + cc.driver.forename, 2017, cc.year);
            assertEquals("Color not valid for " + cc.driver.forename, "#c30000", ferrariColors.get(0).hex);

            System.out.println("Constructor colors for " + cc.driver.forename + " OK");
        }
    }

    @Test
    public void testConstructorStandings() {
        int totalResults = new Select().from(ConstructorStandings.class).count();
        assertTrue("ConstructorStandings count < 11800", totalResults > 11800);
        System.out.println(totalResults + " ConstructorStandings found");
    }

    @Test
    public void testConstructors() {
        int constructors = new Select().from(Constructor.class).count();
        assertNotEquals("No constructors in DB", 0, constructors);
        assertTrue("Total constructors < 208", constructors >= 208);
        System.out.println("Constructors found: " + constructors + " OK");

        Constructor ferrari = new Select().from(Constructor.class).where("constructorRef = ?", "ferrari").executeSingle();
        assertNotNull("Constructor Ferrari not found in DB", ferrari);
        assertEquals("Wrong constructor loaded", "Ferrari", ferrari.name);
        assertEquals("Wrong constructor loaded", "Italian", ferrari.nationality);
        System.out.println("Constructor: " + ferrari.name + " OK");

        Constructor haas = new Select().from(Constructor.class).where("constructorRef = ?", "haas").executeSingle();
        assertNotNull("Constructor Haas F1 Team not found in DB", haas);
        assertEquals("Wrong constructor loaded", "Haas F1 Team", haas.name);
        assertEquals("Wrong constructor loaded", "American", haas.nationality);
        System.out.println("Constructor: " + haas.name + " OK");
    }
}
