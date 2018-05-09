/*
 * Project: total-gp-world
 * File: ChartColorThemePickerPreference
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.preference.chartColorThemePicker;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;

import java.util.ArrayList;
import java.util.List;

public class ChartColorThemePickerPreference extends ListPreference {
    private int currentIndex = 0;
    private Context context;
    private ImageView image1, image2, image3, image4, image5;
    private ChartColorTheme[] chartColorThemes;
    private List<IconItem> icons;

    private ChartColorTheme selectedTheme;
    private TextView summary;

    public ChartColorThemePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        //view.setPadding(context.getResources().getDimension(android.R.attr.listPreferredItemPaddingLeft),0,0,0);

        ChartColorTheme[] themes = ChartColorTheme.values();
        selectedTheme = themes[currentIndex];
        image1 = (ImageView) view.findViewById(R.id.color1);
        image2 = (ImageView) view.findViewById(R.id.color2);
        image3 = (ImageView) view.findViewById(R.id.color3);
        image4 = (ImageView) view.findViewById(R.id.color4);
        image5 = (ImageView) view.findViewById(R.id.color5);
        updateIcons();
        summary = (TextView) view.findViewById(R.id.summary);
        summary.setText(themes[currentIndex].name());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (icons != null) {
            for (int i = 0; i < chartColorThemes.length; i++) {
                IconItem item = icons.get(i);
                if (item.isChecked) {
                    persistString("" + i);
                    currentIndex = i;
                    selectedTheme = item.theme;
                    updateIcons();
                    summary.setText(item.theme.name());
                    break;
                }
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        String number = "3";
        if (restorePersistedValue) {
            number = this.getPersistedString("3");
        } else {
            persistString(number);
        }
        try {
            currentIndex = Integer.parseInt(number);
        } catch (Exception e) {
            currentIndex = 3;
        }
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton(null, null);

        icons = new ArrayList<>();
        chartColorThemes = ChartColorTheme.values();
        for (int i = 0; i < chartColorThemes.length; i++) {
            IconItem item = new IconItem(chartColorThemes[i], (i == currentIndex));
            icons.add(item);
        }
        ChartColorThemePickerPreferenceAdapter customListPreferenceAdapter = new ChartColorThemePickerPreferenceAdapter(
                context, R.layout.preference_list_chart_theme_picker, icons);
        builder.setAdapter(customListPreferenceAdapter, null);
    }

    private void updateIcons() {
        image1.setBackgroundColor(selectedTheme.getColors()[0]);
        image1.setTag(selectedTheme);
        image2.setBackgroundColor(selectedTheme.getColors()[1]);
        image2.setTag(selectedTheme);
        image3.setBackgroundColor(selectedTheme.getColors()[2]);
        image3.setTag(selectedTheme);
        image4.setBackgroundColor(selectedTheme.getColors()[3]);
        image4.setTag(selectedTheme);
        image5.setBackgroundColor(selectedTheme.getColors()[4]);
        image5.setTag(selectedTheme);
    }

    private class ChartColorThemePickerPreferenceAdapter extends ArrayAdapter<IconItem> {
        private Context context;
        private List<IconItem> icons;
        private int resource;

        ChartColorThemePickerPreferenceAdapter(Context context, int resource, List<IconItem> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.icons = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resource, parent, false);

                holder = new ViewHolder();
                holder.iconName = (TextView) convertView.findViewById(R.id.iconName);
                holder.image1 = (ImageView) convertView.findViewById(R.id.color1);
                holder.image2 = (ImageView) convertView.findViewById(R.id.color2);
                holder.image3 = (ImageView) convertView.findViewById(R.id.color3);
                holder.image4 = (ImageView) convertView.findViewById(R.id.color4);
                holder.image5 = (ImageView) convertView.findViewById(R.id.color5);
                holder.radioButton = (RadioButton) convertView.findViewById(R.id.iconRadio);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.iconName.setText(icons.get(position).theme.name());

            ChartColorTheme theme = icons.get(position).theme;
            holder.image1.setBackgroundColor(theme.getColors()[0]);
            holder.image2.setBackgroundColor(theme.getColors()[1]);
            holder.image3.setBackgroundColor(theme.getColors()[2]);
            holder.image4.setBackgroundColor(theme.getColors()[3]);
            holder.image5.setBackgroundColor(theme.getColors()[4]);
            holder.radioButton.setChecked(icons.get(position).isChecked);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < icons.size(); i++) {
                        icons.get(i).isChecked = i == position;
                    }
                    getDialog().dismiss();
                }
            });
            return convertView;
        }
    }

    private class IconItem {
        private ChartColorTheme theme;
        private boolean isChecked;

        IconItem(ChartColorTheme theme, boolean isChecked) {
            this.theme = theme;
            this.isChecked = isChecked;
        }

    }

    private class ViewHolder {
        ImageView image1, image2, image3, image4, image5;
        TextView iconName;
        RadioButton radioButton;
    }
}