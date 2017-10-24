package com.gmail.fattazzo.formula1world.activity.about

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.*

/**
 * @author fattazzo
 *
 *
 * date: 26/06/17
 */
@EActivity(R.layout.activity_about)
open class AboutActivity : Activity() {

    @Bean
    lateinit internal var utils: Utils

    var expand_space_indicator: View? = null

    // ---------- Project ----------------------------
    @ViewById
    lateinit internal var project_container: LinearLayout

    // ---------- Attribution ------------------------
    @ViewById
    lateinit internal var attribution_container: LinearLayout

    // ---------- Terms & Conditions -----------------
    @ViewById
    lateinit internal var terms_conditions_container: LinearLayout
    @ViewById
    lateinit internal var terms_of_use_text: TextView
    @ViewById
    lateinit internal var disclaimer_text: TextView

    // ---------- FAQ --------------------------------
    @ViewById
    lateinit internal var faq_container: LinearLayout
    @ViewById
    lateinit internal var faq1_text: TextView
    @ViewById
    lateinit internal var faq2_text: TextView

    @AfterViews
    internal fun init() {
        expand_space_indicator = findViewById(R.id.expand_space_indicator);

        terms_of_use_text!!.text = Html.fromHtml(getString(R.string.about_terms_of_use_text))
        disclaimer_text!!.text = Html.fromHtml(getString(R.string.about_disclaimer_text))

        faq1_text!!.text = Html.fromHtml(getString(R.string.about_faq1_text))
        faq2_text!!.text = Html.fromHtml(getString(R.string.about_faq2_text))

        attribution_container!!.visibility = if (expand_space_indicator != null) View.VISIBLE else View.GONE
        terms_conditions_container!!.visibility = if (expand_space_indicator != null) View.VISIBLE else View.GONE
        faq_container!!.visibility = if (expand_space_indicator != null) View.VISIBLE else View.GONE
    }

    // ---------- Project ----------------------------
    @Click(R.id.project_title)
    internal fun toggleProjectPanel() {
        project_container!!.visibility = if (project_container!!.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    @Click(R.id.project_author_email)
    internal fun openEmailAction(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        val data = Uri.parse("mailto:" + (view as TextView).text)
        intent.data = data
        startActivity(intent)
    }

    @Click(R.id.project_source)
    internal fun openProjectSourceAction() {
        utils!!.openLink("https://github.com/fattazzo/total-gp-world")
    }

    @Click(R.id.project_wiki)
    internal fun openProjectWikiAction() {
        utils!!.openLink("https://github.com/fattazzo/total-gp-world/wiki")
    }

    // ---------- Attribution ------------------------
    @Click(R.id.ergast_button)
    internal fun openErgastWebPage() {
        utils!!.openLink("http://ergast.com/mrd/")
    }

    @Click(R.id.attribution_title)
    internal fun toggleAttributionPanel() {
        attribution_container!!.visibility = if (attribution_container!!.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    // ---------- Terms & Conditions -----------------
    @Click(R.id.terms_conditions_title)
    internal fun toggleTermsConditionsPanel() {
        terms_conditions_container!!.visibility = if (terms_conditions_container!!.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    // ---------- FAQ --------------------------------
    @Click(R.id.faq_title)
    internal fun toggleFaqPanel() {
        faq_container!!.visibility = if (faq_container!!.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}
