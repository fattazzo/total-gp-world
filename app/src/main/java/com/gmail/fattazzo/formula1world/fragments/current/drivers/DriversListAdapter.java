package com.gmail.fattazzo.formula1world.fragments.current.drivers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNameComparator;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNumberComparator;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@EBean
public class DriversListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<F1Driver> drivers;

    private SortType sortType = SortType.NAME;

    private final DriverNameComparator driverNameComparator = new DriverNameComparator();
    private final DriverNumberComparator driverNumberComparator = new DriverNumberComparator();

    void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public void setDrivers(List<F1Driver> drivers) {
        this.drivers = drivers;
        sortDrivers();
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
    public F1Driver getItem(int position) {
        return drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void clearItems() {
        drivers.clear();
    }

    void sortDrivers() {
        Comparator<F1Driver> comparator;

        switch (sortType) {
            case NAME:
                comparator = driverNameComparator;
                break;
            case NUMBER:
                comparator = driverNumberComparator;
                break;
            default:
                comparator = driverNameComparator;
        }
        Collections.sort(drivers,comparator);
    }

    enum SortType {NUMBER, NAME}
}