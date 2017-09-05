package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBQualificationsTest extends BaseDBTest {

    @Test
    public void testCount() {
        int total = new Select().from(Qualification.class).count();
        assertTrue("Qualification count < 7337", total >= 7337);
        System.out.println(total + " Qualification found");
    }

    @Test
    public void testRace841() {
        int qual841 = new Select().from(Qualification.class).where("raceId = ?", 841).count();
        assertEquals("Wrong Qualification for race 841", 24, qual841);
        System.out.println(qual841 + " Qualification for race 841");
    }

    @Test
    public void test2016() {
        int qual2016 = new Select("qual.*").from(Qualification.class).as("qual")
                .innerJoin(Race.class).as("race").on("race.Id = qual.raceId")
                .where("race.year = ?", 2016).count();
        assertEquals("Wrong Qualification for year 2016", 457, qual2016);
        System.out.println(qual2016 + " Qualification for year 2016");
    }
}
