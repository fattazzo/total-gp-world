package com.gmail.fattazzo.formula1world.view.progressbar;

import android.content.res.Resources;

public final class Utils {

    private Utils() {
    }

    static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}