package com.gmail.fattazzo.formula1world.news.reader

import org.jsoup.nodes.Element

/**
 * @author fattazzo
 *
 *
 * date: 21/07/17
 */
class Formula1ItNewsReader : RssNewsReader() {

    override fun getChannelImage(channelElement: Element): String {
        return "assets://news/formula1itlogo.png"
    }
}
