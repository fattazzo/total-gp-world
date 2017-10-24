package com.gmail.fattazzo.formula1world.news.reader

import org.apache.commons.lang3.StringUtils
import org.jsoup.nodes.Element

/**
 * @author fattazzo
 *
 *
 * date: 20/07/17
 */
class CircusF1NewsReader : RssNewsReader() {

    override fun getChannelImage(channelElement: Element): String {
        return "assets://news/circusf1com.png"
    }

    override fun getNewsDescription(newsElement: Element): String {
        var fullDescription = newsElement.getElementsByTag("content:encoded").first().text()
        fullDescription = StringUtils.substringBefore(fullDescription, " The post")
        return fullDescription
    }

    override fun getNewsImage(newsElement: Element): String? {
        return null
    }
}
