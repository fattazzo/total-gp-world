package com.gmail.fattazzo.formula1world.news.reader;

import org.jsoup.nodes.Element;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/07/17
 */
public class AutosportComNewsReader extends RssNewsReader {

    @Override
    protected String getChannelImage(Element channelElement) {
        return "http://www.autosport.com/img/favicons/120x120.png";
    }
}
