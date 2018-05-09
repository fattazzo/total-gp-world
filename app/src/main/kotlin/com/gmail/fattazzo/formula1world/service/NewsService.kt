/*
 * Project: total-gp-world
 * File: NewsService.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
