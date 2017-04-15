package com.gmail.fattazzo.formula1livenews.fragments.current;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.ergast.objects.Driver;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.drivers_item_list)
public class DriverItemView extends LinearLayout {

    @ViewById(R.id.driver_item_number)
    TextView numberView;

    @ViewById(R.id.driver_item_name)
    TextView nameView;

    public DriverItemView(Context context) {
        super(context);
    }

    public void bind(Driver driver) {
        numberView.setText(String.valueOf(driver.getPermanentNumber()));
        nameView.setText(driver.getGivenName() + " " + driver.getFamilyName());
    }
}