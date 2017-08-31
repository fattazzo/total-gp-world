package com.gmail.fattazzo.formula1world.fragments.stats.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData;

import java.text.DecimalFormat;
import java.util.List;

public abstract class StatsDataImageLabelListAdapter extends BaseAdapter {

    private List<StatsData> data;
    private LayoutInflater mInflater;

    private DecimalFormat valueFormat;

    public StatsDataImageLabelListAdapter(@NonNull Context context, @NonNull List<StatsData> data, DecimalFormat valueFormat) {
        this.data = data;
        this.valueFormat = valueFormat;

        mInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatsData data = getItem(position);

        ViewHolderItem holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stats_row, null);

            holder = new ViewHolderItem();
            holder.textLeft = (TextView) convertView.findViewById(R.id.textLeft);
            holder.textCenter = (TextView) convertView.findViewById(R.id.textCenter);
            holder.textRight = (TextView) convertView.findViewById(R.id.textRight);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.textLeft.setText(String.valueOf(position + 1));
        holder.textCenter.setText(data.getLabel());
        holder.textRight.setText(valueFormat.format(data.getValue()));
        Bitmap bitmap = getImage(data);
        if (bitmap == null) {
            holder.imageView.setVisibility(View.GONE);
        } else {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public StatsData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract
    @Nullable
    Bitmap getImage(StatsData data);

    private static class ViewHolderItem {
        TextView textLeft;
        ImageView imageView;
        TextView textCenter;
        TextView textRight;
    }
}