package com.gmail.fattazzo.formula1world.fragments.news

import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity
import com.gmail.fattazzo.formula1world.activity.settings.SettingsActivity_
import com.gmail.fattazzo.formula1world.fragments.BaseFragment
import com.gmail.fattazzo.formula1world.news.objects.News
import com.gmail.fattazzo.formula1world.service.NewsService
import com.gmail.fattazzo.formula1world.settings.ApplicationPreferenceManager
import com.gmail.fattazzo.formula1world.utils.IssueReporter
import com.gmail.fattazzo.formula1world.utils.Utils
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import org.androidannotations.annotations.*
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
@OptionsMenu(R.menu.news)
@EFragment(R.layout.fragment_news)
open class NewsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Bean
    lateinit protected var preferenceManager: ApplicationPreferenceManager
    @Bean
    lateinit internal var newsService: NewsService
    @Bean
    lateinit internal var utils: Utils

    @ViewById
    lateinit internal var swipe_refresh_layout: SwipeRefreshLayout

    @ViewById
    lateinit internal var recycler_view: RecyclerView

    @ViewById
    lateinit internal var list_view: ListView
    @ViewById
    lateinit internal var news_date: TextView
    @ViewById
    lateinit internal var news_description: TextView
    @ViewById
    lateinit internal var news_title: TextView
    @ViewById
    lateinit internal var news_open_button: Button

    @AfterViews
    internal fun init() {
        swipe_refresh_layout!!.setOnRefreshListener(this)

        onRefresh()
    }

    // ----------- Recycler View ----------------------------------------------------------------
    @Background
    open fun loadNewsRecyclerView() {
        var result: List<News>? = null
        try {
            startLoad()
            val language = preferenceManager!!.newsLanguage()
            result = newsService!!.loadNews(language)
        } finally {
            updateUI(result)
        }
    }

    // ------------ List View -------------------------------------------------------------------
    @Background
    open fun loadNewsListView() {
        var result: List<News>? = null
        try {
            startLoad()
            val language = preferenceManager!!.newsLanguage()
            result = newsService!!.loadNews(language)
        } finally {
            updateUI(result)
        }
    }

    @UiThread
    internal open fun startLoad() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout!!.isRefreshing = true
        }
    }

    @UiThread
    internal open fun updateUI(result: List<News>?) {
        try {
            if (recycler_view != null) {
                recycler_view!!.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(activity)
                recycler_view!!.layoutManager = layoutManager
                recycler_view!!.adapter = NewsExpandableRecyclerAdapter(result!!)
            } else if (list_view != null) {
                list_view!!.adapter = NewsListAdapter(activity, result!!)
            }
        } finally {
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout!!.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        if (recycler_view != null) {
            loadNewsRecyclerView()
        } else {
            loadNewsListView()
        }
    }

    @ItemClick
    fun list_view(position: Int) {
        val news = list_view!!.adapter.getItem(position) as News

        var localeDateString: String
        try {
            localeDateString = android.text.format.DateUtils.formatDateTime(activity.applicationContext,
                    news.date!!.time,
                    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        } catch (e: Exception) {
            localeDateString = ""
        }

        news_date!!.text = localeDateString
        news_description!!.text = Html.fromHtml(news.description).toString()
        news_title!!.text = Html.fromHtml(news.title).toString()
        news_open_button!!.tag = news.link
    }

    @Click
    fun news_open_button(view: View?) {
        if (view != null && view.tag != null) {
            utils!!.openLink(view.tag.toString())
        }
    }

    @Click
    fun info_fab() {
        LovelyStandardDialog(activity)
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.news_language_info_title)
                .setMessage(Html.fromHtml(getString(R.string.news_language_info_message, Locale.getDefault().language.toUpperCase(), Locale.getDefault().displayLanguage)))
                .setPositiveButton(android.R.string.ok) {
                    val issueTitle = getString(R.string.news_report_title, Locale.getDefault().language.toUpperCase(), Locale.getDefault().displayLanguage)
                    IssueReporter.openReportIssue(activity, issueTitle, "", false)
                }
                .setNegativeButton(android.R.string.no, null)
                .show()
    }

    @OptionsItem
    protected fun action_settings() {
        SettingsActivity_.intent(context).startForResult(HomeActivity.PREF_ACTIVITY_RESULT)
    }

    @OnActivityResult(HomeActivity.PREF_ACTIVITY_RESULT)
    internal fun onPrefActivityResult() {
        onRefresh()
    }

    companion object {

        val TAG = NewsFragment::class.java.simpleName
    }
}
