package com.gmail.fattazzo.formula1world.dbimage;

import android.util.SparseIntArray;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
public class DBResultsTest extends BaseDBTest {

    private SparseIntArray resultsByYears = null;

    @Before
    public void initDB() {
        super.initDB();

        resultsByYears = new SparseIntArray();
        resultsByYears.put(1950, 160);
        resultsByYears.put(1951, 179);
        resultsByYears.put(1952, 215);
        resultsByYears.put(1953, 246);
        resultsByYears.put(1954, 230);
        resultsByYears.put(1955, 180);
        resultsByYears.put(1956, 190);
        resultsByYears.put(1957, 171);
        resultsByYears.put(1958, 241);
        resultsByYears.put(1959, 195);
        resultsByYears.put(1960, 223);
        resultsByYears.put(1961, 219);
        resultsByYears.put(1962, 223);
        resultsByYears.put(1963, 254);
        resultsByYears.put(1964, 206);
        resultsByYears.put(1965, 205);
        resultsByYears.put(1966, 166);
        resultsByYears.put(1967, 205);
        resultsByYears.put(1968, 233);
        resultsByYears.put(1969, 177);
        resultsByYears.put(1970, 294);
        resultsByYears.put(1971, 262);
        resultsByYears.put(1972, 309);
        resultsByYears.put(1973, 357);
        resultsByYears.put(1974, 436);
        resultsByYears.put(1975, 363);
        resultsByYears.put(1976, 434);
        resultsByYears.put(1977, 477);
        resultsByYears.put(1978, 471);
        resultsByYears.put(1979, 403);
        resultsByYears.put(1980, 383);
        resultsByYears.put(1981, 449);
        resultsByYears.put(1982, 465);
        resultsByYears.put(1983, 424);
        resultsByYears.put(1984, 430);
        resultsByYears.put(1985, 406);
        resultsByYears.put(1986, 412);
        resultsByYears.put(1987, 419);
        resultsByYears.put(1988, 495);
        resultsByYears.put(1989, 620);
        resultsByYears.put(1990, 542);
        resultsByYears.put(1991, 539);
        resultsByYears.put(1992, 478);
        resultsByYears.put(1993, 412);
        resultsByYears.put(1994, 444);
        resultsByYears.put(1995, 418);
        resultsByYears.put(1996, 340);
        resultsByYears.put(1997, 376);
        resultsByYears.put(1998, 349);
        resultsByYears.put(1999, 352);
        resultsByYears.put(2000, 373);
        resultsByYears.put(2001, 374);
        resultsByYears.put(2002, 362);
        resultsByYears.put(2003, 320);
        resultsByYears.put(2004, 360);
        resultsByYears.put(2005, 376);
        resultsByYears.put(2006, 396);
        resultsByYears.put(2007, 374);
        resultsByYears.put(2008, 368);
        resultsByYears.put(2009, 340);
        resultsByYears.put(2010, 456);
        resultsByYears.put(2011, 456);
        resultsByYears.put(2012, 480);
        resultsByYears.put(2013, 418);
        resultsByYears.put(2014, 407);
        resultsByYears.put(2015, 378);
        resultsByYears.put(2016, 462);
    }

    @Test
    public void testCount() {
        int results = new Select().from(Result.class).count();
        assertNotEquals("No results in DB", 0, results);
        System.out.println("Results found: " + results + " OK");
    }

    @Test
    public void testRace841() {
        int resultRace841 = new Select().from(Result.class).where("raceId = ?", 841).count();
        assertEquals("Wrong Result for race 841", 24, resultRace841);
        System.out.println(resultRace841 + " Result for race 841");
    }

    @Test
    public void testCountByYear() {
        for (int i = 0; i < resultsByYears.size(); i++) {
            int year = resultsByYears.keyAt(i);
            int count = resultsByYears.get(year);

            int countDB = new Select("rs.*").from(Result.class).as("rs")
                    .innerJoin(Race.class).as("race").on("race.Id = rs.raceId")
                    .where("race.year = ?", year).count();
            assertEquals("Wrong Result for year " + year, count, countDB);
            System.out.println(countDB + " Result for year " + year);
        }
    }
}
