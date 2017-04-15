package com.gmail.fattazzo.formula1livenews.activity.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dspot.declex.Action.$CurrentDriversFragment;
import com.dspot.declex.Action.$HomeFragment;
import com.dspot.declex.api.action.Action;
import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1livenews.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$BackPressedEvent;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @ViewById
    NavigationView nav_view;

    @ViewById
    DrawerLayout drawer_layout;

    @Action
    $CurrentDriversFragment currentDriversFragment;

    @Action
    $HomeFragment onCreate;

    @AfterViews
    protected void init(Toolbar toolbar) {

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
    }

    @Action
    @Override
    public void onBackPressed() {
        $BackPressedEvent();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_current_season_driver:
                currentDriversFragment.fire();
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Event
    void  onBackPressedEvent() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            onCreate.fire();
        }
    }
}
