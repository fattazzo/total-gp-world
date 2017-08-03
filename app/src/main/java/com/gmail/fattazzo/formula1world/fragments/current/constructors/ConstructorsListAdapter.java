package com.gmail.fattazzo.formula1world.fragments.current.constructors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@EBean
class ConstructorsListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<F1Constructor> constructors;

    public void setConstructors(List<F1Constructor> constructors) {
        this.constructors = new ArrayList<>();
        CollectionUtils.addAll(this.constructors, CollectionUtils.emptyIfNull(constructors));
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
    public F1Constructor getItem(int position) {
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