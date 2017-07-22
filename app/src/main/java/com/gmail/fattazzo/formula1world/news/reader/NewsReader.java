package com.gmail.fattazzo.formula1world.news.reader;

import com.gmail.fattazzo.formula1world.news.NewsSource;
import com.gmail.fattazzo.formula1world.news.objects.News;

import java.util.List;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public interface NewsReader {

    /**
     * Load all news for the source.
     *
     * @param source news source
     * @return news
     */
    List<News> loadNews(NewsSource source);
}
