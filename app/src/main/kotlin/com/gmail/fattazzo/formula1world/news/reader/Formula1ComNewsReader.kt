package com.gmail.fattazzo.formula1world.news.reader

import org.jsoup.nodes.Element

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
class Formula1ComNewsReader : RssNewsReader() {

    override fun getChannelImage(channelElement: Element): String {
        return "https://www.formula1.com/etc/designs/fom-website/icon128x128.png"
    }

    override fun getNewsImage(newsElement: Element): String? {
        return null
    }
}
