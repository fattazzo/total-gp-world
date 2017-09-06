package com.gmail.fattazzo.formula1world.activity.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 26/06/17
 */
@EActivity(R.layout.activity_about)
public class AboutActivity extends Activity {

    @Bean
    Utils utils;

    @ViewById
    View expand_space_indicator;

    // ---------- Project ----------------------------
    @ViewById
    LinearLayout project_container;

    // ---------- Attribution ------------------------
    @ViewById
    LinearLayout attribution_container;

    // ---------- Terms & Conditions -----------------
    @ViewById
    LinearLayout terms_conditions_container;
    @ViewById
    TextView terms_of_use_text;
    @ViewById
    TextView disclaimer_text;

    // ---------- FAQ --------------------------------
    @ViewById
    LinearLayout faq_container;
    @ViewById
    TextView faq1_text;
    @ViewById
    TextView faq2_text;

    @SuppressWarnings("deprecation")
    @AfterViews
    void init() {
        terms_of_use_text.setText(Html.fromHtml(getString(R.string.about_terms_of_use_text)));
        disclaimer_text.setText(Html.fromHtml(getString(R.string.about_disclaimer_text)));

        faq1_text.setText(Html.fromHtml(getString(R.string.about_faq1_text)));
        faq2_text.setText(Html.fromHtml(getString(R.string.about_faq2_text)));

        attribution_container.setVisibility(expand_space_indicator != null ? View.VISIBLE : View.GONE);
        terms_conditions_container.setVisibility(expand_space_indicator != null ? View.VISIBLE : View.GONE);
        faq_container.setVisibility(expand_space_indicator != null ? View.VISIBLE : View.GONE);
    }

    // ---------- Project ----------------------------
    @Click(R.id.project_title)
    void toggleProjectPanel() {
        project_container.setVisibility(project_container.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @Click(R.id.project_author_email)
    void openEmailAction(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + ((TextView) view).getText());
        intent.setData(data);
        startActivity(intent);
    }

    @Click(R.id.project_source)
    void openProjectSourceAction() {
        utils.openLink("https://github.com/fattazzo/total-gp-world");
    }

    @Click(R.id.project_wiki)
    void openProjectWikiAction() {
        utils.openLink("https://github.com/fattazzo/total-gp-world/wiki");
    }

    // ---------- Attribution ------------------------
    @Click(R.id.ergast_button)
    void openErgastWebPage() {
        utils.openLink("http://ergast.com/mrd/");
    }

    @Click(R.id.attribution_title)
    void toggleAttributionPanel() {
        attribution_container.setVisibility(attribution_container.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    // ---------- Terms & Conditions -----------------
    @Click(R.id.terms_conditions_title)
    void toggleTermsConditionsPanel() {
        terms_conditions_container.setVisibility(terms_conditions_container.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    // ---------- FAQ --------------------------------
    @Click(R.id.faq_title)
    void toggleFaqPanel() {
        faq_container.setVisibility(faq_container.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
