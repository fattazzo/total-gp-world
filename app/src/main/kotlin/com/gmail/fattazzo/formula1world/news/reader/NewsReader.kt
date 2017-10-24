package com.gmail.fattazzo.formula1world.news.reader

import com.gmail.fattazzo.formula1world.news.NewsSource
import com.gmail.fattazzo.formula1world.news.objects.News

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
interface NewsReader {

    /**
     * Load all news for the source.
     *
     * @param source news source
     * @return news
     */
    fun loadNews(source: NewsSource): List<News>
}
