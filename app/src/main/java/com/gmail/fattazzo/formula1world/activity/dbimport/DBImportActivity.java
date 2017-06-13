package com.gmail.fattazzo.formula1world.activity.dbimport;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.importer.ErgastDBImporter;
import com.gmail.fattazzo.formula1world.ergast.imagedb.importer.ImportEventValue;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.DriverStandings;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.LapTime;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.PitStop;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Qualification;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Race;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Result;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Season;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Status;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.view.progressbar.ArcProgress;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;

import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/06/17
 */
@EActivity(R.layout.dbimage_import_layout)
public class DBImportActivity extends Activity implements PropertyChangeListener {

    public static boolean active = false;

    @Bean
    ErgastDBImporter dbImporter;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @ViewById
    ProgressBar dbimage_progressBar;

    @ViewById
    ArcProgress dbimage_progressBar_arc;

    @ViewById
    TextView dbimage_percentage_tv;

    @ViewById
    CheckedTextView db_seasons_ctv;

    @ViewById
    CheckedTextView db_status_ctv;

    @ViewById
    CheckedTextView db_circuits_ctv;

    @ViewById
    CheckedTextView db_races_ctv;

    @ViewById
    CheckedTextView db_constructors_ctv;

    @ViewById
    CheckedTextView db_drivers_ctv;

    @ViewById
    CheckedTextView db_constructorStandings_ctv;

    @ViewById
    CheckedTextView db_driverStandings_ctv;

    @ViewById
    CheckedTextView db_qualifying_ctv;

    @ViewById
    CheckedTextView db_results_ctv;

    @ViewById
    CheckedTextView db_pitstops_ctv;

    @ViewById
    CheckedTextView db_laptimes_ctv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(preferenceManager.getAppTheme());
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        dbImporter.setImportListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof ImportEventValue) {
            ImportEventValue importEventValue = (ImportEventValue) evt.getNewValue();

            switch (importEventValue.getStep()) {
                case IMPORT_TABLE_FINISHED:
                case IMPORT:
                    updateUi(importEventValue);

                    int percImport = importEventValue.getCurrentOp() * 100 / importEventValue.getTotalOp();

                    if(dbimage_progressBar != null) {
                        dbimage_progressBar.setMax(importEventValue.getTotalOp());
                        dbimage_progressBar.setProgress(importEventValue.getCurrentOp());
                        dbimage_percentage_tv.setText(String.format(Locale.getDefault(), "%d%%", percImport));
                    }
                    if(dbimage_progressBar_arc != null) {
                        dbimage_progressBar_arc.setProgress(percImport);

                        String tableName = importEventValue.getModel().getAnnotation(Table.class).name();
                        String packageName = getPackageName();
                        int resId = getResources().getIdentifier(tableName, "string", packageName);
                        dbimage_progressBar_arc.setBottomText(getResources().getString(resId));
                    }
                    break;

                case CUSTOM_TABLES:
                    db_laptimes_ctv.setChecked(true);
                case DONE:
                    close();

            }
        }
    }

    @UiThread
    void close() {
        this.finish();
        $HomeFragment(HomeFragment.TAG);
    }


    private void updateUi(ImportEventValue importEventValue) {
        Class<? extends Model> model = importEventValue.getModel();

        switch (model.getSimpleName()) {
            case "LapTime":
                db_laptimes_ctv.setChecked(true);
            case "PitStop":
                db_pitstops_ctv.setChecked(true);
            case "Result":
                db_results_ctv.setChecked(true);
            case "Qualification":
                db_qualifying_ctv.setChecked(true);
            case "DriverStandings":
                db_driverStandings_ctv.setChecked(true);
            case "ConstructorStandings":
                db_constructorStandings_ctv.setChecked(true);
            case "Driver":
                db_drivers_ctv.setChecked(true);
            case "Constructor":
                db_constructors_ctv.setChecked(true);
            case "Race":
                db_races_ctv.setChecked(true);
            case "Circuit":
                db_circuits_ctv.setChecked(true);
            case "Status":
                db_status_ctv.setChecked(true);
            case "Season":
                db_seasons_ctv.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
