package com.gmail.fattazzo.formula1world.news.reader;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class CircusF1NewsReader extends RssNewsReader {

    @Override
    protected String getChannelImage(Element channelElement) {
        return "assets://news/circusf1com.png";
    }

    @Override
    protected String getNewsDescription(Element newsElement) {
        String fullDescription = newsElement.getElementsByTag("content:encoded").first().text();
        fullDescription = StringUtils.substringBefore(fullDescription, " The post");
        return fullDescription;
    }

    @Override
    protected String getNewsImage(Element newsElement) {
        return null;
    }
}
