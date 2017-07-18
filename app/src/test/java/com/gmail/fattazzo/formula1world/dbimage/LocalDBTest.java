package com.gmail.fattazzo.formula1world.dbimage;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;

import org.apache.commons.lang3.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RuntimeEnvironment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/06/17
 */
@RunWith(CustomRobolectricRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDBTest {

    @Test
    public void _importDB() {

        /**

         Cache.clear();

         dbImporter = ErgastDBImporter_.getInstance_(RuntimeEnvironment.application.getApplicationContext());

         int seasonsInDB = new Select().from(Season.class).count();
         assertEquals("DB must be empty!", 0, seasonsInDB);

         // drop table
         dbImporter.dropTables();
         try {
         new Select().from(Season.class).execute();
         } catch (Exception e) {
         assertTrue(e.getCause().getMessage().contains("no such table"));
         }

         // recreate db
         dbImporter.recreateDatabase();
         try {
         new Select().from(Season.class).execute();
         } catch (Exception e) {
         Assert.fail(e.getMessage());
         }

         // import data
         for (Map.Entry<Class<? extends Model>, String> entry : dbImporter.getObjectImportMap().entrySet()) {
         String[] inserts = readInsert(entry.getValue());
         System.out.println("Insert " + inserts.length + " " + entry.getKey().getSimpleName());
         for (String insert : inserts) {
         SQLiteUtils.execSql(insert);
         }
         try {
         int modelsInDB = new Select().from(entry.getKey()).count();
         assertTrue("No " + inserts.length + " " + entry.getKey().getSimpleName() + " found in db", inserts.length == modelsInDB);
         } catch (Exception e) {
         Assert.fail(e.getMessage());
         }
         }

         // custom tables
         dbImporter.createCustomTable();
         int dcInDB = new Select().from(DriverConstructor.class).count();
         assertNotEquals("Table driversConstructors must not be empty!", 0, dcInDB);

         testAllData();
         **/
    }

    private void testAllData() {
        testSeasons();
        testDrivers();
    }

    private void testDrivers() {
        int drivers = new Select().from(Driver.class).count();
        assertNotEquals("No drivers in DB", 0, drivers);

        Driver seb = new Select().from(Driver.class).where("surname = ?", "Vettel").executeSingle();
        assertNotNull("Driver Vettel not found in DB", seb);
        assertEquals("Wrong driver loaded", "Sebastian", seb.forename);
        assertEquals("Wrong driver loaded", "VET", seb.code);

        Driver farina = new Select().from(Driver.class).where("surname = ?", "Farina").executeSingle();
        assertNotNull("Driver Farina not found in DB", farina);
        assertEquals("Wrong driver loaded", "Nino", farina.forename);
    }

    private void testSeasons() {
        int seasons = new Select().from(Season.class).count();
        assertNotEquals("No seasons in DB", 0, seasons);

        Season season1950 = new Select().from(Season.class).where("id = ?", 1950).executeSingle();

        assertNotNull("Season 1950 not found in DB", season1950);
        assertEquals("Wrong season loaded", 1950, season1950.getId().intValue());
    }

    private String[] readInsert(String file) {

        List<String> inserts = new ArrayList<>();

        String insertStart = "";
        try (InputStream is = RuntimeEnvironment.application.getAssets().open("dbimage" + "/" + file + ".sql.zip");
             ZipInputStream zipIs = new ZipInputStream(is);
             BufferedReader in = new BufferedReader(new InputStreamReader(zipIs, "UTF-8"))) {

            zipIs.getNextEntry();

            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith("INSERT INTO")) {
                    insertStart = str;
                    continue;
                }

                if (str.equals(";")) {
                    continue;
                }

                inserts.add(insertStart + (str.startsWith(",") ? StringUtils.substring(str, 1) : str));
            }

        } catch (IOException e) {
            fail(e.getMessage());
        }

        String[] insertArray = inserts.toArray(new String[inserts.size()]);
        inserts.clear();
        return insertArray;
    }

}
