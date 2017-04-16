package com.gmail.fattazzo.formula1livenews.fragments.home.driverstandings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1livenews.ergast.objects.DriverStandings;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
class DriverStandingsListAdapter extends BaseAdapter {

    private List<DriverStandings> drivers;

    @RootContext
    Context context;

    public void setDrivers(List<DriverStandings> drivers) {
        this.drivers = drivers;
    }

    @AfterInject
    void initAdapter() {
        drivers = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DriverStandingsItemView driverStandingsItemView;
        if (convertView == null) {
            driverStandingsItemView = DriverStandingsItemView_.build(context);
        } else {
            driverStandingsItemView = (DriverStandingsItemView) convertView;
        }

        driverStandingsItemView.bind(getItem(position));

        return driverStandingsItemView;
    }

    @Override
    public int getCount() {
        return drivers.size();
    }

    @Override
    public DriverStandings getItem(int position) {
        return drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void clearItems() {
        drivers.clear();
    }
}