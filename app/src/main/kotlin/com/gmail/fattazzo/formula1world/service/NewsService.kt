package com.gmail.fattazzo.formula1world.service

import android.content.Context
import com.gmail.fattazzo.formula1world.news.NewsReaderFactory
import com.gmail.fattazzo.formula1world.news.NewsSource
import com.gmail.fattazzo.formula1world.news.objects.News
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.io.InputStreamReader
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
@EBean(scope = EBean.Scope.Singleton)
open class NewsService {

    @RootContext
    lateinit internal var context: Context

    /**
     * Load all [NewsSource] for language.
     *
     * @param language language for news
     * @return NewsSource loaded
     */
    private fun getNewsSource(language: String): List<NewsSource> {

        val jsonToMap = loadAllNewsSources()

        return jsonToMap.orEmpty()[language.toUpperCase()].orEmpty()
    }

    fun loadAllNewsSources(): Map<String, List<NewsSource>>? {
        try {
            context!!.assets.open("news.json").use { `is` ->
                InputStreamReader(`is`).use { isr ->

                    val objGson = GsonBuilder().create()
                    val jsonReader = JsonReader(isr)

                    return objGson.fromJson(jsonReader, object : TypeToken<Map<String, @JvmSuppressWildcards List<NewsSource>>>() {}.type)
                }
            }
        } catch (e: Exception) {
            return null
        }

    }

    /**
     * Load all news for configured [NewsSource]
     *
     * @param language news language
     * @return news
     */
    fun loadNews(language: String): List<News> {

        val news = ArrayList<News>()

        val newsSources = getNewsSource(language)

        for (source in newsSources) news.addAll(loadNews(source))

        Collections.sort(news) { n1, n2 -> n1.date!!.compareTo(n2.date) * -1 }

        return news
    }

    /**
     * Load all news for source
     *
     * @param newsSource source
     * @return news
     */
    fun loadNews(newsSource: NewsSource): List<News> {
        val newsReader = NewsReaderFactory.getNewsReader(newsSource.type!!)
        return newsReader.loadNews(newsSource)
    }
}
