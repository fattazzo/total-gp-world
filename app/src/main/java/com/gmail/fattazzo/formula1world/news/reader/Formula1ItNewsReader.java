package com.gmail.fattazzo.formula1world.news.reader;

import org.jsoup.nodes.Element;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/07/17
 */
public class Formula1ItNewsReader extends RssNewsReader {

    @Override
    protected String getChannelImage(Element channelElement) {
        return "assets://news/formula1itlogo.png";
    }
}
