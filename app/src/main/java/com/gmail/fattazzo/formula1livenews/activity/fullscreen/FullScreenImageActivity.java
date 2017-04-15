package com.gmail.fattazzo.formula1livenews.activity.fullscreen;

import android.app.Activity;

import com.gmail.fattazzo.formula1livenews.R;
import com.gmail.fattazzo.formula1livenews.utils.ImageUtils;
import com.gmail.fattazzo.formula1livenews.view.ZoomableImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_layout_full)
public class FullScreenImageActivity extends Activity {

    public static final String EXTRA_CIRCUIT_ID = "circuit_id";

    @Extra
    String circuit_id;

    @Bean
    ImageUtils imageUtils;

    @ViewById
    ZoomableImageView imgDisplay;

    @AfterViews
    void init() {
        imgDisplay.setImageBitmap(imageUtils.getCircuitForCode(circuit_id));
    }

    @Click
    void btnClose() {
        FullScreenImageActivity.this.finish();
    }
}