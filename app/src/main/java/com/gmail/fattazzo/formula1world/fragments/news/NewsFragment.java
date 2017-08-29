package com.gmail.fattazzo.formula1world.fragments.news;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dspot.declex.api.eventbus.Event;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.fragments.home.HomeFragment;
import com.gmail.fattazzo.formula1world.news.objects.News;
import com.gmail.fattazzo.formula1world.service.NewsService;
import com.gmail.fattazzo.formula1world.utils.IssueReporter;
import com.gmail.fattazzo.formula1world.utils.Utils;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Locale;

import static com.dspot.declex.Action.$HomeFragment;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
@EFragment(R.layout.fragment_news)
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = NewsFragment.class.getSimpleName();

    @Bean
    NewsService newsService;

    @Bean
    Utils utils;

    @ViewById
    FloatingActionButton info_fab;

    @ViewById
    SwipeRefreshLayout swipe_refresh_layout;

    @ViewById
    RecyclerView recycler_view;

    @ViewById
    ListView list_view;
    @ViewById
    TextView news_date;
    @ViewById
    TextView news_description;
    @ViewById
    TextView news_title;
    @ViewById
    Button news_open_button;

    @AfterViews
    void init() {
        swipe_refresh_layout.setOnRefreshListener(this);

        if (recycler_view != null) {
            loadNewsRecyclerView();
        } else {
            loadNewsListView();
        }
    }

    // ----------- Recycler View ----------------------------------------------------------------
    @Background
    public void loadNewsRecyclerView() {
        List<News> result = null;
        try {
            startLoad();
            String language = Locale.getDefault().getLanguage();
            result = newsService.loadNews(language);
        } finally {
            updateUI(result);
        }
    }

    // ------------ List View -------------------------------------------------------------------
    @Background
    public void loadNewsListView() {
        List<News> result = null;
        try {
            startLoad();
            String language = Locale.getDefault().getLanguage();
            result = newsService.loadNews(language);
        } finally {
            updateUI(result);
        }
    }

    @UiThread
    void startLoad() {
        if (swipe_refresh_layout != null) {
            swipe_refresh_layout.setRefreshing(true);
        }
    }

    @UiThread
    void updateUI(List<News> result) {
        try {
            if (recycler_view != null) {
                recycler_view.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recycler_view.setLayoutManager(layoutManager);
                recycler_view.setAdapter(new NewsExpandableRecyclerAdapter(result));
            } else if (list_view != null) {
                list_view.setAdapter(new NewsListAdapter(getActivity(), result));
            }
        } finally {
            if (swipe_refresh_layout != null) {
                swipe_refresh_layout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        if (recycler_view != null) {
            loadNewsRecyclerView();
        } else {
            loadNewsListView();
        }
    }

    @ItemClick
    public void list_view(int position) {
        News news = (News) (list_view.getAdapter()).getItem(position);

        String localeDateString;
        try {
            localeDateString = android.text.format.DateUtils.formatDateTime(getActivity().getApplicationContext(),
                    news.getDate().getTime(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR);
        } catch (Exception e) {
            localeDateString = "";
        }
        news_date.setText(localeDateString);
        news_description.setText(Html.fromHtml(news.getDescription()).toString());
        news_title.setText(Html.fromHtml(news.getTitle()).toString());
        news_open_button.setTag(news.getLink());
    }

    @Click
    public void news_open_button(View view) {
        if (view != null && view.getTag() != null) {
            utils.openLink(view.getTag().toString());
        }
    }

    @Click
    public void info_fab() {
        new LovelyStandardDialog(getActivity())
                .setTopColorRes(R.color.colorPrimaryDark)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.news_language_info_title)
                .setMessage(Html.fromHtml(getString(R.string.news_language_info_message, Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault().getDisplayLanguage())))
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String issueTitle = getString(R.string.news_report_title, Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault().getDisplayLanguage());
                        IssueReporter.openReportIssue(getActivity(), issueTitle, "", false);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Event
    void onBackPressedEvent() {
        $HomeFragment(HomeFragment.TAG);
    }
}
