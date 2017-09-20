package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@EBean
public class DriversSpinnerAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<DriverSpinnerModel> drivers;

    public void setDrivers(Collection<DriverSpinnerModel> drivers) {
        this.drivers = new ArrayList<>();
        CollectionUtils.addAll(this.drivers, CollectionUtils.emptyIfNull(drivers));

        Collections.sort(this.drivers, new Comparator<DriverSpinnerModel>() {
            @Override
            public int compare(DriverSpinnerModel o1, DriverSpinnerModel o2) {
                int constr = o1.getConstructor().constructorRef.compareTo(o2.getConstructor().constructorRef);
                if (constr != 0) {
                    return constr;
                } else {
                    return o1.getDriver().driverRef.compareTo(o2.getDriver().driverRef);
                }
            }
        });
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
    public DriverSpinnerModel getItem(int position) {
        return drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void clearItems() {
        drivers.clear();
    }

    public List<DriverSpinnerModel> getItems() {
        return drivers;
    }
}