package com.gmail.fattazzo.formula1world.utils;

import android.content.Context;
import android.util.TypedValue;

import com.gmail.fattazzo.formula1world.R;

import org.androidannotations.annotations.EBean;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class ThemeUtils {

    public int getThemeTextColor(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColor, value, true);
        return value.data;
    }

    public int getThemeBGColor(Context context) {
        try {
            final TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.bgBackground, value, true);
            return value.data;
        } catch (Exception e) {
            return android.R.color.transparent;
        }
    }

    public float getThemeTextSize(final Context context, int dimansionResId) {
        float scaleRatio = ObjectUtils.defaultIfNull(context.getResources().getDisplayMetrics().density, 1f);
        float dimenPix = ObjectUtils.defaultIfNull(context.getResources().getDimension(dimansionResId), 16f);
        return dimenPix / scaleRatio;
    }
}
