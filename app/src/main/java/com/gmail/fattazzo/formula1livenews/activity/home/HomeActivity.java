package com.gmail.fattazzo.formula1livenews.activity.home;

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
import android.view.MenuItem;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.activity.settings.SettingsActivity;
import com.gmail.fattazzo.formula1livenews.fragments.current.constructors.CurrentConstructorsFragment;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.CurrentDriversFragment;
import com.gmail.fattazzo.formula1livenews.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1livenews.fragments.home.HomeFragmentActionHolder_;
import com.gmail.fattazzo.formula1livenews.settings.ApplicationPreferenceManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$AlertDialog;
import static com.dspot.declex.Action.$BackPressedEvent;
import static com.dspot.declex.Action.$CurrentConstructorsFragment;
import static com.dspot.declex.Action.$CurrentDriversFragment;
import static com.dspot.declex.Action.$FullScreenImageActivity;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PREF_ACTIVITY_RESULT = 1;

    @Bean
    ApplicationPreferenceManager preferenceManager;

    @ViewById
    NavigationView nav_view;

    @ViewById
    DrawerLayout drawer_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(preferenceManager.getAppTheme());

        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            // Instanzio il fragment se savedInstanceState == null altrimenti (ad es. girando il dispositivo)
            // viene rimesso anche se quello attivo è un altro
            final HomeFragmentActionHolder_ $HomeFragment0 = HomeFragmentActionHolder_.getInstance_(this);
            $HomeFragment0 .init((HomeFragment.TAG));
            $HomeFragment0 .build(null);
            $HomeFragment0 .execute();
        }
    }

    @OnActivityResult(PREF_ACTIVITY_RESULT)
    public void onPrefThemeChanged( int resultCode) {
        if(SettingsActivity.RESULT_CODE_THEME_UPDATED == resultCode) {
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
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag(HomeFragment.TAG) != null) {
            $AlertDialog().message(R.string.app_exit_comfirmation)
                    .positiveButton(R.string.yes).negativeButton(R.string.no);

            if ($AlertDialog.PositiveButtonPressed) {
                $BackPressedEvent();
            }
        } else {
            $BackPressedEvent();
        }
    }

    public void openCirtuitFullScreeenImage(String circuitId) {
        if (circuitId != null) {
            $FullScreenImageActivity().circuit_id(circuitId);
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
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
