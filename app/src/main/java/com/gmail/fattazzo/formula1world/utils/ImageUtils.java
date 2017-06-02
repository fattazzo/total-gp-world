package com.gmail.fattazzo.formula1world.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.config.Config;

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

    /**
     * Retrieve color for constructor.
     *
     * @param constructorId id constructor
     * @return color
     */
    public int getColorForConstructorId(@Nullable String constructorId) {
        int color;

        try {
            color = context.getResources().getIdentifier(constructorId, "color", context.getPackageName());
        } catch (Exception e) {
            color = R.color.background_color;
        }

        return color;
    }

    /**
     * Invert bitmap color.
     *
     * @param src bitmap src
     * @return bitmap inverted
     */
    public Bitmap invertColor(Bitmap src) {
        ColorMatrix colorMatrix_Inverted =
                new ColorMatrix(new float[]{
                        -1, 0, 0, 0, 255,
                        0, -1, 0, 0, 255,
                        0, 0, -1, 0, 255,
                        0, 0, 0, 1, 0});

        ColorFilter ColorFilter_Sepia = new ColorMatrixColorFilter(
                colorMatrix_Inverted);

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();

        paint.setColorFilter(ColorFilter_Sepia);
        canvas.drawBitmap(src, 0, 0, paint);

        return bitmap;
    }
}
