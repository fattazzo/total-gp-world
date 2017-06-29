package com.gmail.fattazzo.formula1world.activity.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.activity.about.AboutActivity_;
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.CurrentConstructorsFragment;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment;
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragmentActionHolder_;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import static com.dspot.declex.Action.$AboutActivity;
import static com.dspot.declex.Action.$AlertDialog;
import static com.dspot.declex.Action.$BackPressedEvent;
import static com.dspot.declex.Action.$CurrentConstructorsFragment;
import static com.dspot.declex.Action.$CurrentDriversFragment;
import static com.dspot.declex.Action.$CurrentRacesFragment;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PREF_ACTIVITY_RESULT = 1;

    @Bean
    Ergast ergast;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @ViewById
    NavigationView nav_view;

    @ViewById
    DrawerLayout drawer_layout;

    @Bean
    DataService dataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(preferenceManager.getAppTheme());

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Instanzio il fragment se savedInstanceState == null altrimenti (ad es. girando il dispositivo)
            // viene rimesso anche se quello attivo è un altro
            final HomeFragmentActionHolder_ $HomeFragment0 = HomeFragmentActionHolder_.getInstance_(this);
            $HomeFragment0.init((HomeFragment.TAG));
            $HomeFragment0.build(null);
            $HomeFragment0.execute();
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int) (metrics.density * 160f);
        Log.d("DENSITY", String.valueOf(metrics.density));
        Log.d("DPI", String.valueOf(densityDpi));
    }

    @OnActivityResult(PREF_ACTIVITY_RESULT)
    public void onPrefThemeChanged(int resultCode) {
        if (SettingsActivity.RESULT_CODE_THEME_UPDATED == resultCode) {
            setTheme(preferenceManager.getAppTheme());
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            });
        }
    }

    @AfterViews
    protected void init(Toolbar toolbar) {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        nav_view.setItemIconTintList(null);

        final List<Integer> seasons = dataService.getAvailableSeasons();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.home_seasons_spinner_item, seasons.toArray(new Integer[seasons.size()]));
        adapter.setDropDownViewResource(R.layout.home_seasons_spinner_dropdown_item);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name) + " " + ergast.getSeason() + "▼");
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSeasonsSelectionDialog();
            }
        });
    }

    public void showSeasonsSelectionDialog() {

        List<Integer> seasons = dataService.getAvailableSeasons();

        final Dialog d = new Dialog(this);
        d.setTitle(getString(R.string.seasons));

        d.setContentView(R.layout.seasons_selection_dialog_layout);
        Button b1 = (Button) d.findViewById(R.id.buttonSelect);
        Button b2 = (Button) d.findViewById(R.id.buttonCancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        np.setMaxValue(seasons.get(0));
        np.setMinValue(seasons.get(seasons.size() - 1));
        np.setValue(ergast.getSeason());
        np.setWrapSelectorWheel(true);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer oldSeason = ergast.getSeason();

                Integer selectedSeason = np.getValue();
                ergast.setSeason(selectedSeason);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.app_name) + " " + np.getValue() + "▼");
                }
                drawer_layout.closeDrawer(GravityCompat.START);

                if (oldSeason != ergast.getSeason()) {
                    dataService.importDBIfNecessary();

                    final HomeFragmentActionHolder_ $HomeFragment0 = HomeFragmentActionHolder_.getInstance_(HomeActivity.this);
                    $HomeFragment0.init((HomeFragment.TAG));
                    $HomeFragment0.build(null);
                    $HomeFragment0.execute();
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        TextView textView = (TextView) d.findViewById(android.R.id.title);
        if (textView != null) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        d.show();


    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(HomeFragment.TAG) != null) {
            $AlertDialog().message(R.string.app_exit_comfirmation)
                    .positiveButton(R.string.yes).negativeButton(R.string.no);

            if ($AlertDialog.PositiveButtonPressed) {
                $BackPressedEvent();
            }
        } else {
            $BackPressedEvent();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_current_season_driver:
                $CurrentDriversFragment(CurrentDriversFragment.TAG);
                break;
            case R.id.nav_current_season_constructor:
                $CurrentConstructorsFragment(CurrentConstructorsFragment.TAG);
                break;
            case R.id.nav_current_season_race:
                $CurrentRacesFragment(CurrentRacesFragment.TAG);
                break;
            case R.id.nav_about:
                $AboutActivity();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
