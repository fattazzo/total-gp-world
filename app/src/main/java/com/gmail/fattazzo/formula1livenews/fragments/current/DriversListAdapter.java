package com.gmail.fattazzo.formula1livenews.fragments.current;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;
import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;
import com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings.DriverStandingsItemView;
import com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings.DriverStandingsItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class DriversListAdapter extends BaseAdapter {

    List<Driver> drivers;

    @RootContext
    Context context;

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @AfterInject
    void initAdapter() {
        drivers = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DriverItemView driverItemView;
        if (convertView == null) {
            driverItemView = DriverItemView_.build(context);
        } else {
            driverItemView = (DriverItemView) convertView;
        }

        driverItemView.bind(getItem(position));

        return driverItemView;
    }

    @Override
    public int getCount() {
        return drivers.size();
    }

    @Override
    public Driver getItem(int position) {
        return drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearItems() {
        drivers.clear();
    }
}