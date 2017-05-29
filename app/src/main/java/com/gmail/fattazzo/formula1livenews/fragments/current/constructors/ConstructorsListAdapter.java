package com.gmail.fattazzo.formula1livenews.fragments.current.constructors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1livenews.ergast.objects.Constructor;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class ConstructorsListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<Constructor> constructors;

    public void setConstructors(List<Constructor> constructors) {
        this.constructors = constructors;
    }

    @AfterInject
    void initAdapter() {
        constructors = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConstructorItemView constructorItemView;
        if (convertView == null) {
            constructorItemView = ConstructorItemView_.build(context);
        } else {
            constructorItemView = (ConstructorItemView) convertView;
        }

        constructorItemView.bind(getItem(position));

        return constructorItemView;
    }

    @Override
    public int getCount() {
        return constructors.size();
    }

    @Override
    public Constructor getItem(int position) {
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