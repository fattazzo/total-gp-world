package com.gmail.fattazzo.formula1world.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gmail.fattazzo.formula1world.news.NewsReaderFactory;
import com.gmail.fattazzo.formula1world.news.NewsSource;
import com.gmail.fattazzo.formula1world.news.objects.News;
import com.gmail.fattazzo.formula1world.news.reader.NewsReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.commons.collections4.CollectionUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
@EBean(scope = EBean.Scope.Singleton)
public class NewsService {

    @RootContext
    Context context;

    /**
     * Load all {@link NewsSource} for language.
     *
     * @param language language for news
     * @return NewsSource loaded
     */
    private
    @NonNull
    List<NewsSource> getNewsSource(String language) {

        Map<String, List<NewsSource>> jsonToMap = loadAllNewsSources();

        return new ArrayList<>(CollectionUtils.emptyIfNull(jsonToMap.get(language.toUpperCase())));
    }

    public Map<String, List<NewsSource>> loadAllNewsSources() {
        try (InputStream is = context.getAssets().open("news.json"); InputStreamReader isr = new InputStreamReader(is)) {

            Type listType = new TypeToken<Map<String, List<NewsSource>>>() {
            }.getType();

            Gson objGson = new GsonBuilder().create();
            JsonReader jsonReader = new JsonReader(isr);

            return objGson.fromJson(jsonReader, listType);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Load all news for configured {@link NewsSource}
     *
     * @param language news language
     * @return news
     */
    public
    @NonNull
    List<News> loadNews(String language) {

        List<News> news = new ArrayList<>();

        List<NewsSource> newsSources = getNewsSource(language);
        for (NewsSource source : newsSources) {
            news.addAll(loadNews(source));
        }

        Collections.sort(news, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                return n1.getDate().compareTo(n2.getDate()) * -1;
            }
        });

        return news;
    }

    /**
     * Load all news for source
     *
     * @param newsSource source
     * @return news
     */
    public
    @NonNull
    List<News> loadNews(NewsSource newsSource) {
        NewsReader newsReader = NewsReaderFactory.getNewsReader(newsSource.getType());
        return newsReader.loadNews(newsSource);
    }
}
