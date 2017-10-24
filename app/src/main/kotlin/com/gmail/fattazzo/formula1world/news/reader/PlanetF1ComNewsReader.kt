package com.gmail.fattazzo.formula1world.news.reader

import org.jsoup.nodes.Element

/**
 * @author fattazzo
 *
 *
 * date: 21/07/17
 */
class PlanetF1ComNewsReader : RssNewsReader() {

    override fun getChannelImage(channelElement: Element): String {
        return "http://cdn.planetf1.com.s3.amazonaws.com/content/themes/planet/img/icons/favicon-96x96.png"
    }
}
