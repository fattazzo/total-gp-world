package com.gmail.fattazzo.formula1world.fragments.home.circuit

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.constraint.ConstraintLayout
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.fullscreen.FullScreenImageActivity_
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.domain.F1Season
import com.gmail.fattazzo.formula1world.service.DataService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.ImageUtils
import com.gmail.fattazzo.formula1world.utils.LocaleUtils
import com.gmail.fattazzo.formula1world.utils.Utils
import org.androidannotations.annotations.*
import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup

@EBean
open class CurrentCircuitTask {

    @Bean
    lateinit internal var preferenceManager: ApplicationPreferenceManager

    @Bean
    lateinit internal var utils: Utils

    @Bean
    lateinit internal var localeUtils: LocaleUtils

    @Bean
    lateinit internal var imageUtils: ImageUtils

    @Bean
    lateinit internal var dataService: DataService

    @RootContext
    lateinit internal var activity: HomeActivity

    @ViewById(R.id.home_current_circuit_progressBar)
    lateinit internal var progressBar: ProgressBar

    @ViewById(R.id.current_circuit_name)
    lateinit internal var circuitNameView: TextView

    @ViewById(R.id.current_circuit_location)
    lateinit internal var circuitLocationView: TextView

    @ViewById(R.id.current_circuit_country_flag)
    lateinit internal var flagView: ImageView

    @ViewById(R.id.current_circuit_image)
    lateinit internal var circuitImageView: ImageView

    @ViewById(R.id.current_circuit_round_number_label)
    lateinit internal var roundNumberView: TextView

    @ViewById(R.id.current_circuit_date)
    lateinit internal var roundDateView: TextView

    @ViewById(R.id.current_circuit_time)
    lateinit internal var roundTimeView: TextView

    @ViewById
    lateinit internal var home_current_circuit_layout: View

    @ViewById
    lateinit internal var season_wv: WebView

    private var raceLoaded: F1Race? = null
    private var season: F1Season? = null

    @ViewById(R.id.current_circuit_info_button)
    internal fun setupInfoButton(button: ImageButton) {
        button.setOnClickListener {
            val linkInfo = circuitLocationView!!.tag as String
            utils!!.openLink(linkInfo)
        }
    }

    @ViewById(R.id.current_circuit_coord_location_button)
    internal fun setupCoordButton(button: ImageButton) {
        button.setOnClickListener {
            if (raceLoaded != null && raceLoaded!!.circuit != null && raceLoaded!!.circuit.location != null) {
                utils!!.openCoordinates(raceLoaded!!.circuit.location!!.lat, raceLoaded!!.circuit.location!!.lng)
            }
        }
    }

    @ViewById(R.id.home_current_circuit_layout)
    internal fun setOneView(layout: ConstraintLayout) {
        circuitImageView!!.setOnClickListener {
            if (circuitImageView!!.tag != null) {
                FullScreenImageActivity_.intent(activity).circuit_id(circuitImageView!!.tag as String).start()
            }
        }
    }


    @UiThread
    internal open fun start() {
        progressBar!!.visibility = View.VISIBLE
    }


    fun loadCurrentSchedule(reloadData: Boolean) {
        if (reloadData) {
            dataService!!.clearRacesCache()
        }
        loadCurrentSchedule()
    }

    @Background
    open fun loadCurrentSchedule() {
        try {
            start()
            raceLoaded = dataService.loadCurrentSchedule()

            season = dataService.loadSeason(dataService.selectedSeasons)

            if (StringUtils.isBlank(season!!.description)) {
                val doc = Jsoup.connect(season!!.url!!.replace("en.wikipedia", "en.m.wikipedia")).get()
                season!!.description = doc.outerHtml()
                dataService.updateSeason(season!!)
            }
        } catch (e: Exception) {
            season!!.description = ""
        } finally {
            if (season!!.current) {
                updateRaceUI()
            } else {
                updateSeasonUI()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @UiThread
    internal open fun updateSeasonUI() {
        home_current_circuit_layout!!.visibility = View.GONE
        season_wv!!.visibility = View.VISIBLE

        season_wv!!.settings.javaScriptEnabled = true
        season_wv!!.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (progressBar != null) {
                    progressBar!!.visibility = View.INVISIBLE
                }
            }
        })
        if (StringUtils.isNotBlank(season!!.description)) {
            season_wv!!.loadDataWithBaseURL("https://en.m.wikipedia.org", season!!.description, "text/html; charset=UTF-8", null, null)
        } else {
            season_wv!!.loadData("<html>No data</html>", "text/html; charset=UTF-8", null)
        }
    }

    @UiThread
    internal open fun updateRaceUI() {
        season_wv!!.visibility = View.GONE
        home_current_circuit_layout!!.visibility = View.VISIBLE

        try {
            var circuitName = activity!!.getString(R.string.no_race_scheduled)
            var circuitLocation = ""
            var circuitInfoLink: String? = ""
            var flagImage: Bitmap? = null
            var circuitImage: Bitmap? = null
            var roundNumber = ""
            var roundDate = ""
            var roundTime = ""
            var circuitId: String? = null
            if (raceLoaded != null) {
                circuitName = raceLoaded!!.name

                val countryEn = raceLoaded!!.circuit.location!!.country
                val countryLocale = localeUtils!!.getLocaleCountryName(countryEn!!)
                circuitLocation = countryLocale + ", " + raceLoaded!!.circuit.location!!.locality + "\n" + raceLoaded!!.circuit.name
                circuitInfoLink = raceLoaded!!.circuit.url
                flagImage = imageUtils!!.getFlagForCountryCode(localeUtils!!.getCountryCode(countryEn))
                circuitId = raceLoaded!!.circuit.circuitRef
                circuitImage = imageUtils!!.getCircuitForCode(circuitId!!)
                if (preferenceManager!!.isBitmapInvertedForCurrentTheme) {
                    circuitImage = imageUtils!!.invertColor(circuitImage!!)
                }
                roundNumber = raceLoaded!!.round.toString()

                val dateUTCString = raceLoaded!!.date + "T" + raceLoaded!!.time
                roundDate = utils!!.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss", "EEEE dd MMMM yyyy")
                roundTime = utils!!.convertUTCDateToLocal(dateUTCString, "yyyy-MM-dd'T'HH:mm:ss", "HH:mm")
            }

            circuitNameView!!.text = circuitName
            circuitLocationView!!.text = circuitLocation
            circuitLocationView!!.tag = circuitInfoLink
            flagView!!.setImageBitmap(flagImage)
            circuitImageView!!.setImageBitmap(circuitImage)
            circuitImageView!!.tag = circuitId
            roundNumberView!!.text = roundNumber
            roundDateView!!.text = roundDate
            roundTimeView!!.text = roundTime
        } finally {
            progressBar!!.visibility = View.INVISIBLE
        }
    }
}