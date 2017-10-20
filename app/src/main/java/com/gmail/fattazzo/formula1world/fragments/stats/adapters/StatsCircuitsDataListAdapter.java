package com.gmail.fattazzo.formula1world.fragments.stats.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Circuit;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Constructor;
import com.gmail.fattazzo.formula1world.ergast.imagedb.objects.Driver;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsCircuitsData;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.utils.CountryNationality;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.LocaleUtils;
import com.gmail.fattazzo.formula1world.utils.Utils;

import java.util.List;

public class StatsCircuitsDataListAdapter extends BaseExpandableListAdapter {

    private DataService dataService;
    private LocaleUtils localeUtils;
    private ImageUtils imageUtils;
    private Utils utils;

    private Context context;

    private List<StatsCircuitsData> data;
    private LayoutInflater mInflater;

    private SparseIntArray constructorsColorCache;

    public StatsCircuitsDataListAdapter(@NonNull Context context, @NonNull List<StatsCircuitsData> data, DataService dataService, LocaleUtils localeUtils, ImageUtils imageUtils, Utils utils) {
        this.context = context;
        this.data = data;
        this.dataService = dataService;
        this.localeUtils = localeUtils;
        this.imageUtils = imageUtils;
        this.utils = utils;

        constructorsColorCache = new SparseIntArray();

        mInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        StatsCircuitsData data = (StatsCircuitsData) getGroup(groupPosition);

        ViewHolderItem holder;

        if (convertView == null) {
            holder = new ViewHolderItem();
            switch (data.getType()) {
                case CONSTRUCTORS_WINNER:
                case DRIVERS_WINNER:
                    convertView = mInflater.inflate(R.layout.stats_circuits_winner_row, null);
                    holder.winnerTV = (TextView) convertView.findViewById(R.id.winnerTV);
                    break;
                case COUNT:
                    convertView = mInflater.inflate(R.layout.stats_circuits_count_row, null);
                    holder.countTV = (TextView) convertView.findViewById(R.id.countTV);
                    break;
            }
            holder.nameTV = (TextView) convertView.findViewById(R.id.nameTV);
            holder.teamIconImage = (ImageView) convertView.findViewById(R.id.teamIconImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.nameTV.setText(data.getName());
        if (holder.winnerTV != null) {
            holder.winnerTV.setText(data.getWinnerName());
        } else {
            holder.countTV.setText(String.valueOf(data.getCount()));
        }

        int color = getConstructorColor(data.getConstructorId(), data.getConstructorRef());
        holder.teamIconImage.setColorFilter(color);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        StatsCircuitsData data = (StatsCircuitsData) getGroup(groupPosition);

        ViewHolderChildItem holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stats_circuits_detail, null);

            holder = new ViewHolderChildItem();
            holder.circuitNameTV = (TextView) convertView.findViewById(R.id.circuitNameTV);
            holder.circuitCountryTv = (TextView) convertView.findViewById(R.id.circuitCountryTv);
            holder.circuitLocationTV = (TextView) convertView.findViewById(R.id.circuitLocationTV);
            holder.circuitFlagImage = (ImageView) convertView.findViewById(R.id.circuitFlagImage);

            holder.driverLayout = (ConstraintLayout) convertView.findViewById(R.id.driverLayout);
            holder.driverSurnameTV = (TextView) convertView.findViewById(R.id.driverSurnameTV);
            holder.driverFornameTV = (TextView) convertView.findViewById(R.id.driverFornameTV);
            holder.driverDobTV = (TextView) convertView.findViewById(R.id.driverDobTV);
            holder.driverFlagImage = (ImageView) convertView.findViewById(R.id.driverFlagImage);

            holder.constructorLayout = (ConstraintLayout) convertView.findViewById(R.id.constructorLayout);
            holder.constructorNameTV = (TextView) convertView.findViewById(R.id.constructorNameTV);
            holder.constructorFlagImage = (ImageView) convertView.findViewById(R.id.constructorFlagImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChildItem) convertView.getTag();
        }

        Circuit circuit = new Select().from(Circuit.class).where("Id = ?", data.getId()).executeSingle();
        holder.circuitNameTV.setText(circuit.name);
        holder.circuitCountryTv.setText(circuit.country);
        holder.circuitLocationTV.setText(circuit.location);
        holder.circuitFlagImage.setImageBitmap(imageUtils.getFlagForCountryCode(localeUtils.getCountryCode(circuit.country)));

        holder.driverLayout.setVisibility(data.getDriverId() > 0 ? View.VISIBLE : View.GONE);
        if (data.getDriverId() > 0) {
            Driver driver = Driver.load(Driver.class, data.getDriverId());
            holder.driverSurnameTV.setText(driver.surname);
            holder.driverFornameTV.setText(driver.forename);
            holder.driverDobTV.setText(driver.dob);

            CountryNationality countryNationality = utils.getCountryNationality(driver.nationality);
            if (countryNationality != null) {
                holder.driverFlagImage.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code()));
            }
        }

        holder.constructorLayout.setVisibility(data.getConstructorId() > 0 ? View.VISIBLE : View.GONE);
        if (data.getConstructorId() > 0) {
            Constructor constructor = Constructor.load(Constructor.class, data.getConstructorId());
            holder.constructorNameTV.setText(constructor.name);

            CountryNationality countryNationality = utils.getCountryNationality(constructor.nationality);
            if (countryNationality != null) {
                holder.constructorFlagImage.setImageBitmap(imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code()));
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private int getConstructorColor(int constructorId, String constructorRef) {
        if (constructorRef == null) {
            return android.R.color.transparent;
        }

        int color = constructorsColorCache.get(constructorId, -1);
        if (color == -1) {
            F1Constructor constructor = new F1Constructor();
            constructor.constructorRef = constructorRef;
            color = dataService.loadContructorColor(constructor);
            constructorsColorCache.put(constructorId, color);
        }
        return color;
    }

    private static class ViewHolderItem {
        TextView nameTV;
        TextView countTV;
        TextView winnerTV;
        ImageView teamIconImage;
    }

    private static class ViewHolderChildItem {
        TextView circuitNameTV, circuitCountryTv, circuitLocationTV;
        ImageView circuitFlagImage;

        ConstraintLayout driverLayout, constructorLayout;

        TextView driverSurnameTV, driverFornameTV, driverDobTV, constructorNameTV;
        ImageView driverFlagImage, constructorFlagImage;
    }
}