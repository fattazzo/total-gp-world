package com.gmail.fattazzo.formula1world.news.reader;

import org.jsoup.nodes.Element;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class Formula1ComNewsReader extends RssNewsReader {

    @Override
    protected String getChannelImage(Element channelElement) {
        return "https://www.formula1.com/etc/designs/fom-website/icon128x128.png";
    }

    @Override
    protected String getNewsImage(Element newsElement) {
        return null;
    }
}
