package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.qualifications;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Qualification;
import com.gmail.fattazzo.formula1world.service.DataService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/04/17
 */
@EViewGroup(R.layout.race_qualifications_row)
public class QualificationsRaceItemView extends TableRow {

    @Bean
    DataService dataService;

    @ViewById
    TextView constructor_tv;

    @ViewById
    TextView driver_tv;

    @ViewById
    TextView position_tv;

    @ViewById
    TextView q1_tv;

    @ViewById
    TextView q2_tv;

    @ViewById
    TextView q3_tv;

    @ViewById
    ImageView driver_item_color;

    private F1Qualification qualification;

    private int rowNumber;

    public QualificationsRaceItemView(Context context, F1Qualification qualification, int rowNumber) {
        super(context);
        this.qualification = qualification;
        this.rowNumber = rowNumber;
    }

    @AfterViews
    void bind() {
        if (qualification.constructor != null && constructor_tv != null) {
            constructor_tv.setText(qualification.constructor.name);
        }
        if (qualification.driver != null) {
            driver_tv.setText(qualification.driver.getFullName());
        }
        position_tv.setText(String.valueOf(qualification.position));
        q1_tv.setText(qualification.q1);
        q2_tv.setText(qualification.q2);
        q3_tv.setText(qualification.q3);

        int color = dataService.loadContructorColor(qualification.constructor);
        driver_item_color.setColorFilter(color);

        setBackgroundColor(rowNumber % 2 == 0 ? getThemeEvenRowColor() : getThemeOddRowColor());
    }

    private int getThemeEvenRowColor() {
        final TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.evenRowColor, value, true);
        return value.data;
    }

    private int getThemeOddRowColor() {
        final TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.oddRowColor, value, true);
        return value.data;
    }
}
