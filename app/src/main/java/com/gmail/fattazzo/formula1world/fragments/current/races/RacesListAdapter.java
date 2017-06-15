package com.gmail.fattazzo.formula1world.fragments.current.races;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Race;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.ConstructorItemView;
import com.gmail.fattazzo.formula1world.fragments.current.constructors.ConstructorItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
class RacesListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<F1Race> races;

    public void setRaces(List<F1Race> races) {
        this.races = races;
    }

    @AfterInject
    void initAdapter() {
        races = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RaceItemView raceItemView;
        if (convertView == null) {
            raceItemView = RaceItemView_.build(context);
        } else {
            raceItemView = (RaceItemView) convertView;
        }

        raceItemView.bind(getItem(position));

        return raceItemView;
    }

    @Override
    public int getCount() {
        return races.size();
    }

    @Override
    public F1Race getItem(int position) {
        return races.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void clearItems() {
        races.clear();
    }
}