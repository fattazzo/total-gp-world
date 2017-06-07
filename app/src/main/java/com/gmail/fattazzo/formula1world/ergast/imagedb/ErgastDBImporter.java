package com.gmail.fattazzo.formula1world.ergast.imagedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.util.SQLiteUtils;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverConstructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.LapTime;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.PitStop;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Status;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @author fattazzo
 *         <p/>
 *         date: 02/06/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class ErgastDBImporter {
    private static final String TAG = ErgastDBImporter.class.getSimpleName();

    private final static String DB_IMAGE_PATH = "dbimage";

    @RootContext
    Context context;

    private Map<Class<? extends Model>, String> objectImportMap = new LinkedHashMap<>();

    {
        objectImportMap.put(Season.class, "seasons");
        objectImportMap.put(Status.class, "status");
        objectImportMap.put(Circuit.class, "circuits");
        objectImportMap.put(Race.class, "races");
        objectImportMap.put(Constructor.class, "constructors");
        objectImportMap.put(Driver.class, "drivers");
        objectImportMap.put(ConstructorStandings.class, "constructorStandings");
        objectImportMap.put(DriverStandings.class, "driverStandings");
        objectImportMap.put(Qualification.class, "qualifying");
        objectImportMap.put(Result.class, "results");
        objectImportMap.put(PitStop.class, "pitStops");
        objectImportMap.put(LapTime.class, "lapTimes");
    }

    @Background
    public void importDBImage() {

        Cache.clear();

        Log.d(TAG, "Drop existing tables");
        dropTables();

        Log.d(TAG, "Recreate tables structures");
        recreateDatabase();

        Log.d(TAG, "Import data");
        importData();

        Log.d(TAG, "Create utilities tables");
        createCustomTable();

        Log.d(TAG, "Done");
    }

    private void createCustomTable() {
        SQLiteUtils.execSql("DROP TABLE IF EXISTS driversConstructors");
        SQLiteUtils.execSql(SQLiteUtils.createTableDefinition(Cache.getTableInfo(DriverConstructor.class)));
        SQLiteUtils.execSql("INSERT INTO driversConstructors (driverId,constructorId,year) " +
                "SELECT distinct results.driverId,results.constructorId,races.year " +
                "FROM results inner join races on results.raceId = races.id");
    }

    private void recreateDatabase() {
        for (Map.Entry<Class<? extends Model>, String> entry : objectImportMap.entrySet()) {
            SQLiteUtils.execSql(SQLiteUtils.createTableDefinition(Cache.getTableInfo(entry.getKey())));
        }
    }

    private void dropTables() {
        List<Class<? extends Model>> reverseOrderedKeys = new ArrayList<>(objectImportMap.keySet());
        Collections.reverse(reverseOrderedKeys);
        for (Class<? extends Model> key : reverseOrderedKeys) {
            SQLiteUtils.execSql("DROP TABLE IF EXISTS " + objectImportMap.get(key));
        }
    }

    private void importData() {
        for (Map.Entry<Class<? extends Model>, String> entry : objectImportMap.entrySet()) {

            List<String> inserts = readInsert(entry.getValue());
            Log.d(TAG, "Import " + (inserts.size() * 10) + " " + entry.getValue());

            SQLiteDatabase db = Cache.openDatabase();
            db.beginTransaction();
            for (String string : inserts) {
                db.execSQL(string);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    private List<String> readInsert(String file) {

        List<String> inserts = new ArrayList<>();

        StringBuilder buf = new StringBuilder();
        try (InputStream is = context.getAssets().open(DB_IMAGE_PATH + "/" + file + ".sql.zip");
             ZipInputStream zipIs = new ZipInputStream(is);
             BufferedReader in = new BufferedReader(new InputStreamReader(zipIs, "UTF-8"))) {

            zipIs.getNextEntry();

            String str;
            while ((str = in.readLine()) != null) {
                if (buf.length() != 0 && str.startsWith("INSERT INTO")) {
                    inserts.add(buf.toString());
                    buf = new StringBuilder(str);
                } else {
                    buf.append(str);
                }
            }
            if (StringUtils.isNotBlank(buf.toString())) {
                inserts.add(buf.toString());
            }
        } catch (IOException e) {
        }

        return inserts;
    }

}
