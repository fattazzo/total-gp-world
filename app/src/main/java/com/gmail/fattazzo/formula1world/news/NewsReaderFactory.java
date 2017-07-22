package com.gmail.fattazzo.formula1world.news;

import com.gmail.fattazzo.formula1world.news.reader.AutosportComNewsReader;
import com.gmail.fattazzo.formula1world.news.reader.CircusF1NewsReader;
import com.gmail.fattazzo.formula1world.news.reader.Formula1ComNewsReader;
import com.gmail.fattazzo.formula1world.news.reader.Formula1ItNewsReader;
import com.gmail.fattazzo.formula1world.news.reader.NewsReader;
import com.gmail.fattazzo.formula1world.news.reader.PlanetF1ComNewsReader;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class NewsReaderFactory {

    private static Map<RaederType, NewsReader> newsReaders = new HashedMap<>();

    static {
        newsReaders.put(RaederType.FORMULA1_IT, new Formula1ItNewsReader());
        newsReaders.put(RaederType.CIRCUS_F1, new CircusF1NewsReader());
        newsReaders.put(RaederType.FORMULA1_COM, new Formula1ComNewsReader());
        newsReaders.put(RaederType.AUTOSPORT_COM, new AutosportComNewsReader());
        newsReaders.put(RaederType.PLANETF1_COM, new PlanetF1ComNewsReader());
    }

    public static NewsReader getNewsReader(RaederType type) {
        return newsReaders.get(type);
    }
}
