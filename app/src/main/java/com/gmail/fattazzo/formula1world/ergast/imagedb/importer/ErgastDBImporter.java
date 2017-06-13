package com.gmail.fattazzo.formula1world.ergast.imagedb.importer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
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
import org.androidannotations.annotations.UiThread;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private PropertyChangeListener importListener;

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

    @UiThread
    void fireImportEvent(@NonNull ImportStep step, Class<? extends Model> model, int currentOp, int totalOp) {
        if (importListener != null) {
            PropertyChangeEvent event = new PropertyChangeEvent(this, "importEvent", null, new ImportEventValue(step, model, currentOp, totalOp));
            importListener.propertyChange(event);
        }
    }

    @Background
    void sync() {

        Cache.clear();

        Log.d(TAG, "Drop existing tables");
        fireImportEvent(ImportStep.DROP_TABLES, null, 0, 0);
        dropTables();

        Log.d(TAG, "Recreate tables structures");
        fireImportEvent(ImportStep.RECREATE_DB, null, 0, 0);
        recreateDatabase();

        Log.d(TAG, "Import data");
        importData();

        Log.d(TAG, "Create utilities tables");
        fireImportEvent(ImportStep.CUSTOM_TABLES, null, 0, 0);
        createCustomTable();

        Log.d(TAG, "Done");
        fireImportEvent(ImportStep.DONE, null, 0, 0);

        removeListener();
    }

    @UiThread
    void removeListener() {
        this.importListener = null;
    }

    @UiThread
    public void importDBImage() {
        sync();
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
        // total insert calculation
        // TODO change total insert calculation
        int totalInserts = 0;
        for (Map.Entry<Class<? extends Model>, String> entry : objectImportMap.entrySet()) {
            try (InputStream is = context.getAssets().open(DB_IMAGE_PATH + "/" + entry.getValue() + ".sql.zip");
                 ZipInputStream zipIs = new ZipInputStream(is);
                 BufferedReader in = new BufferedReader(new InputStreamReader(zipIs, "UTF-8"))) {

                zipIs.getNextEntry();

                String insert = "";
                String str;
                while ((str = in.readLine()) != null) {
                    if (insert.length() != 0 && str.startsWith("INSERT INTO")) {
                        totalInserts = totalInserts + 10;
                        insert = str;
                    } else {
                        insert = insert + str;
                    }
                }
                if (StringUtils.isNotBlank(insert)) {
                    totalInserts = totalInserts + 10;
                }

            } catch (IOException e) {
            }
        }
        Log.d(TAG, "Total imports: " + totalInserts);

        String[] inserts;
        int currentInsert = 0;
        for (Map.Entry<Class<? extends Model>, String> entry : objectImportMap.entrySet()) {

            inserts = readInsert(entry.getValue());

            Log.d(TAG, "Import " + inserts.length * 10 + " " + entry.getKey().getSimpleName());
            fireImportEvent(ImportStep.IMPORT, entry.getKey(), currentInsert, totalInserts);

            SQLiteDatabase db = Cache.openDatabase();
            db.beginTransaction();
            for (String string : inserts) {
                db.execSQL(string);
                currentInsert = currentInsert + 10;
                if (currentInsert % 200 == 0) {
                    fireImportEvent(ImportStep.IMPORT, entry.getKey(), currentInsert, totalInserts);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            fireImportEvent(ImportStep.IMPORT_TABLE_FINISHED, entry.getKey(), currentInsert, totalInserts);
        }
    }

    private String[] readInsert(String file) {

        List<String> inserts = new ArrayList<>();
        Log.d(TAG, "Lettura " + file);

        try (InputStream is = context.getAssets().open(DB_IMAGE_PATH + "/" + file + ".sql.zip");
             ZipInputStream zipIs = new ZipInputStream(is);
             BufferedReader in = new BufferedReader(new InputStreamReader(zipIs, "UTF-8"))) {

            zipIs.getNextEntry();

            String insert = "";
            String str;
            while ((str = in.readLine()) != null) {
                if (insert.length() != 0 && str.startsWith("INSERT INTO")) {
                    inserts.add(insert);
                    insert = str;
                } else {
                    insert = insert + str;
                }
            }
            if (StringUtils.isNotBlank(insert)) {
                inserts.add(insert);
            }

        } catch (IOException e) {
        }

        String[] insertArray = inserts.toArray(new String[inserts.size()]);
        inserts.clear();
        return insertArray;
    }

    public void setImportListener(PropertyChangeListener importListener) {
        this.importListener = importListener;
    }
}
