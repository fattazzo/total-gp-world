package com.gmail.fattazzo.formula1world.news.reader;

import org.jsoup.nodes.Element;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/07/17
 */
public class PlanetF1ComNewsReader extends RssNewsReader {

    @Override
    protected String getChannelImage(Element channelElement) {
        return "http://cdn.planetf1.com.s3.amazonaws.com/content/themes/planet/img/icons/favicon-96x96.png";
    }
}
