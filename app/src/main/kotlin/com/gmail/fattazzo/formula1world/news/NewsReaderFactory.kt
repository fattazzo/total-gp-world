package com.gmail.fattazzo.formula1world.news

import com.gmail.fattazzo.formula1world.news.reader.AutosportComNewsReader
import com.gmail.fattazzo.formula1world.news.reader.CircusF1NewsReader
import com.gmail.fattazzo.formula1world.news.reader.Formula1ComNewsReader
import com.gmail.fattazzo.formula1world.news.reader.Formula1ItNewsReader
import com.gmail.fattazzo.formula1world.news.reader.NewsReader
import com.gmail.fattazzo.formula1world.news.reader.PlanetF1ComNewsReader

import org.apache.commons.collections4.map.HashedMap

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
object NewsReaderFactory {

    private val newsReaders = HashedMap<RaederType, NewsReader>()

    init {
        newsReaders.put(RaederType.FORMULA1_IT, Formula1ItNewsReader())
        newsReaders.put(RaederType.CIRCUS_F1, CircusF1NewsReader())
        newsReaders.put(RaederType.FORMULA1_COM, Formula1ComNewsReader())
        newsReaders.put(RaederType.AUTOSPORT_COM, AutosportComNewsReader())
        newsReaders.put(RaederType.PLANETF1_COM, PlanetF1ComNewsReader())
    }

    fun getNewsReader(type: RaederType): NewsReader {
        return newsReaders[type]!!
    }
}
