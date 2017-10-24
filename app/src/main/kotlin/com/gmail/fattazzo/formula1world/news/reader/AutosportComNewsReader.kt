package com.gmail.fattazzo.formula1world.news.reader

import org.jsoup.nodes.Element

/**
 * @author fattazzo
 *
 *
 * date: 21/07/17
 */
class AutosportComNewsReader : RssNewsReader() {

    override fun getChannelImage(channelElement: Element): String {
        return "http://www.autosport.com/img/favicons/120x120.png"
    }
}
