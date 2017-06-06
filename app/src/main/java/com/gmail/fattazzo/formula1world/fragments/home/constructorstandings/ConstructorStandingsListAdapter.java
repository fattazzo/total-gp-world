package com.gmail.fattazzo.formula1world.fragments.home.constructorstandings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings;
import com.gmail.fattazzo.formula1world.ergast.json.objects.ConstructorStandings;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
class ConstructorStandingsListAdapter extends BaseAdapter {

    private List<F1ConstructorStandings> constructors;

    @RootContext
    Context context;

    public void setConstructors(List<F1ConstructorStandings> constructors) {
        this.constructors = constructors;
    }

    @AfterInject
    void initAdapter() {
        constructors = new ArrayList<>();
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
        return constructors.size();
    }

    @Override
    public F1ConstructorStandings getItem(int position) {
        return constructors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void clearItems() {
        constructors.clear();
    }
}