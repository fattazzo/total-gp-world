package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1LapTime;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EBean
public class LapTimeListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<F1LapTime> lapTimes;

    public void setLapTimes(List<F1LapTime> lapTimes) {
        this.lapTimes = lapTimes;
    }

    @AfterInject
    void initAdapter() {
        lapTimes = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LapTimeItemView lapTimeItemView;
        if (convertView == null) {
            lapTimeItemView = LapTimeItemView_.build(context);
        } else {
            lapTimeItemView = (LapTimeItemView) convertView;
        }

        lapTimeItemView.bind(getItem(position),position);

        return lapTimeItemView;
    }

    @Override
    public int getCount() {
        return lapTimes.size();
    }

    @Override
    public F1LapTime getItem(int position) {
        return lapTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearItems() {
        lapTimes.clear();
    }

    void removeItems(F1Driver driver) {
        for (Iterator<F1LapTime> iterator = lapTimes.iterator(); iterator.hasNext(); ) {
            F1LapTime lapTime = iterator.next();
            if (lapTime.driver.driverRef.equals(driver.driverRef)) {
                iterator.remove();
            }
        }
    }
}