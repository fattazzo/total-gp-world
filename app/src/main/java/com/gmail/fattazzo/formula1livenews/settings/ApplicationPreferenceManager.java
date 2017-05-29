package com.gmail.fattazzo.formula1livenews.settings;

import android.support.annotation.NonNull;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * @author fattazzo
 *         <p/>
 *         date: 26/05/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class ApplicationPreferenceManager {

    @Pref
    ApplicationPreference_ prefs;

    /**
     * Fragment transaction enter animation
     *
     * @return -1 for none animation
     */
    public int getFragmentTransactionEnterAnimation() {
        int anim;
        switch (prefs.fragmentTransitionAnimation().get()) {
            case "fade":
                anim = android.R.anim.fade_in;
                break;
            case "slide":
                anim = android.R.anim.slide_in_left;
                break;
            default:
                anim = -1;
                break;
        }

        return anim;
    }

    /**
     * Fragment transaction exit animation
     *
     * @return -1 for none animation
     */
    public int getFragmentTransactionExitAnimation() {
        int anim;
        switch (prefs.fragmentTransitionAnimation().get()) {
            case "fade":
                anim = android.R.anim.fade_out;
                break;
            case "slide":
                anim = android.R.anim.slide_out_right;
                break;
            default:
                anim = -1;
                break;
        }

        return anim;
    }

    /**
     * Pager transaction animation
     *
     * @return configured transformer for animation
     */
    public @NonNull ABaseTransformer getPagerTansactionAnimation() {
        ABaseTransformer transformer;

        switch (prefs.pagerTransitionAnimation().get()) {
            case "zoomOutSlide":
                transformer = new ZoomOutSlideTransformer();
                break;
            case "cubeOut":
                transformer = new CubeOutTransformer();
                break;
            case "cubeIn":
                transformer = new CubeInTransformer();
                break;
            case "accordion":
                transformer = new AccordionTransformer();
                break;
            case "flipHorizontal":
                transformer = new FlipHorizontalTransformer();
                break;
            case "flipVertical":
                transformer = new FlipVerticalTransformer();
                break;
            default:
                transformer = new ZoomOutSlideTransformer();
                break;
        }

        return transformer;
    }
}
