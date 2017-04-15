package com.gmail.fattazzo.formula1livenews.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1livenews.config.Config;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

/**
 * @author fattazzo
 *         <p/>
 *         date: 13/04/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class ImageUtils {

    @RootContext
    Context context;

    /**
     * Retrieve bitmap of flag by country code.
     *
     * @param countryCode country code
     * @return bitmap, {@code null} if not exist
     */
    @Nullable
    public Bitmap getFlagForCountryCode(@NonNull String countryCode) {
        Bitmap image;
        try {
            InputStream is = context.getAssets().open(Config.PATH_FLAGS + "/" + StringUtils.defaultString(countryCode).toLowerCase() + ".png");
            image = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            image = null;
        }
        return image;
    }

    /**
     * Retrieve bitmap of circuit by his code.
     *
     * @param circuitCode circuit code
     * @return bitmap, {@code null} if not exist
     */
    @Nullable
    public Bitmap getCircuitForCode(@NonNull String circuitCode) {
        Bitmap image;
        try {
            InputStream is = context.getAssets().open(Config.PATH_CIRCUITS + "/" + StringUtils.defaultString(circuitCode).toLowerCase() + ".png");
            image = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            image = null;
        }
        return image;
    }
}
