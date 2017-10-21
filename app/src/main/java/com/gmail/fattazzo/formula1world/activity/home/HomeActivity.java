package com.gmail.fattazzo.formula1world.activity.home;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.LocalDBDataService;
import com.gmail.fattazzo.formula1world.fragments.BaseFragment;
import com.gmail.fattazzo.formula1world.fragments.collaborate.CollaborateFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.CurrentConstructorsFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.CurrentDriversFragment_;
import com.gmail.fattazzo.formula1world.fragments.current.races.CurrentRacesFragment_;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment_;
import com.gmail.fattazzo.formula1world.fragments.news.NewsFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.constructors.StatisticsConstructorsFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.drivers.StatisticsDriversFragment_;
import com.gmail.fattazzo.formula1world.fragments.stats.season.StatisticsSeasonFragment_;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager;
import com.gmail.fattazzo.formula1world.utils.FragmentUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PREF_ACTIVITY_RESULT = 1;
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Bean
    Ergast ergast;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @ViewById
    NavigationView nav_view;

    @ViewById
    DrawerLayout drawer_layout;

    @ViewById
    Toolbar toolbar;

    @Bean
    DataService dataService;

    @Bean
    LocalDBDataService localDBDataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(preferenceManager.getAppTheme());

        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Log.d(TAG, "Clear cache");
            dataService.clearCache();
            // Instanzio il fragment se savedInstanceState == null altrimenti (ad es. girando il dispositivo)
            // viene rimesso anche se quello attivo è un altro
            FragmentUtils.replace(this, HomeFragment_.builder().build());
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
    protected void init() {

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
                    dataService.clearCache();

                    FragmentUtils.replace(HomeActivity.this, HomeFragment_.builder().build());
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

        List<Fragment> fragments = fragmentManager.getFragments();
        Fragment fragment = CollectionUtils.isNotEmpty(fragments) ? ObjectUtils.firstNonNull(fragments.toArray(new Fragment[fragments.size()])) : null;

        if (fragment != null && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).backPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_current_season_driver:
                FragmentUtils.replace(this, CurrentDriversFragment_.builder().build());
                break;
            case R.id.nav_current_season_constructor:
                FragmentUtils.replace(this, CurrentConstructorsFragment_.builder().build());
                break;
            case R.id.nav_current_season_race:
                FragmentUtils.replace(this, CurrentRacesFragment_.builder().build());
                break;
            case R.id.nav_news:
                FragmentUtils.replace(this, NewsFragment_.builder().build());
                break;
            case R.id.nav_collaborate:
                FragmentUtils.replace(this, CollaborateFragment_.builder().build());
                break;
            case R.id.nav_stats_drivers:
                FragmentUtils.replace(this, StatisticsDriversFragment_.builder().build());
                break;
            case R.id.nav_stats_constructors:
                FragmentUtils.replace(this, StatisticsConstructorsFragment_.builder().build());
                break;
            case R.id.nav_stats_season:
                FragmentUtils.replace(this, StatisticsSeasonFragment_.builder().build());
                break;
            case R.id.nav_about:
                AboutActivity_.intent(this).start();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
