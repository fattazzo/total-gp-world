package com.gmail.fattazzo.formula1livenews.activity.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dspot.declex.api.action.Action;
import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.CurrentDriversFragment;
import com.gmail.fattazzo.formula1livenews.fragments.current.drivers.CurrentDriversFragment_;
import com.gmail.fattazzo.formula1livenews.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1livenews.fragments.home.HomeFragment_;
import com.gmail.fattazzo.formula1livenews.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bean
    Utils utils;

    @ViewById
    NavigationView nav_view;

    @ViewById
    DrawerLayout drawer_layout;

    //@Action
    //$CurrentDriversFragment currentDriversFragment;

    CurrentDriversFragment currentDriversFragment;
    HomeFragment homeFragment;

    //@Action
    //$HomeFragment onCreate;

    @AfterViews
    protected void init(Toolbar toolbar) {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
    }

    @Action
    void onCreate() {
        if (homeFragment == null) {
            homeFragment = HomeFragment_.builder().build();
        }
        utils.showFragment(this, homeFragment, HomeFragment.TAG,false);
    }

    @Action
    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_current_season_driver:
                if (currentDriversFragment == null) {
                    currentDriversFragment = CurrentDriversFragment_.builder().build();
                }
                utils.showFragment(this, currentDriversFragment, CurrentDriversFragment.TAG,true);
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
