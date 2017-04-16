package com.gmail.fattazzo.formula1livenews.fragments.home.constructorstandings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1livenews.ergast.objects.ConstructorStandings;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
class ConstructorStandingsListAdapter extends BaseAdapter {

    private List<ConstructorStandings> drivers;

    @RootContext
    Context context;

    public void setDrivers(List<ConstructorStandings> drivers) {
        this.drivers = drivers;
    }

    @AfterInject
    void initAdapter() {
        drivers = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConstructorStandingsItemView constructorStandingsItemView;
        if (convertView == null) {
            constructorStandingsItemView = ConstructorStandingsItemView_.build(context);
        } else {
            constructorStandingsItemView = (ConstructorStandingsItemView) convertView;
        }

        constructorStandingsItemView.bind(getItem(position));

        return constructorStandingsItemView;
    }

    @Override
    public int getCount() {
        return drivers.size();
    }

    @Override
    public ConstructorStandings getItem(int position) {
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