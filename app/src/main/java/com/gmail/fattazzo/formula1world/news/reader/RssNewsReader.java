package com.gmail.fattazzo.formula1world.news.reader;

import android.util.Log;

import com.gmail.fattazzo.formula1world.news.NewsSource;
import com.gmail.fattazzo.formula1world.news.objects.Channel;
import com.gmail.fattazzo.formula1world.news.objects.News;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class RssNewsReader implements NewsReader {

    private static final String TAG = RssNewsReader.class.getSimpleName();

    @Override
    public List<News> loadNews(NewsSource source) {

        List<News> news = new ArrayList<>();

        try {
            Document document = Jsoup.connect(source.getUrl()).parser(Parser.xmlParser()).get();

            // channel
            Channel channel = null;
            Element channelElement = document.select(getChannelElementTag()).first();
            if (channelElement != null) {
                channel = parseChannelElement(channelElement);
            }

            for (Element item : document.select(getNewsElementTag())) {
                News newsEntry = parseNewsElement(item, channel);
                news.add(newsEntry);
            }


        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }

        return ListUtils.emptyIfNull(news);
    }

    protected String getChannelElementTag() {
        return "channel";
    }

    protected String getNewsElementTag() {
        return "item";
    }

    protected Channel parseChannelElement(Element element) {
        String title = element.getElementsByTag("title").first().text();
        String link = element.getElementsByTag("link").first().text();
        String description = element.getElementsByTag("description").first().text();
        String image = getChannelImage(element);

        Channel channel = new Channel();
        channel.setTitle(title);
        channel.setDescription(description);
        channel.setLink(link);
        channel.setImage(image);
        return channel;
    }

    protected String getChannelImage(Element channelElement) {
        return channelElement.getElementsByTag("image").first().select("url").text();
    }

    protected News parseNewsElement(Element element, Channel channel) {
        String title = element.getElementsByTag("title").first().text();
        String description = getNewsDescription(element);
        String link = element.getElementsByTag("link").first().text();
        String image = getNewsImage(element);
        String pubDateStr = element.getElementsByTag("pubDate").first().text();
        Date pubDate = null;
        if(StringUtils.isNotBlank(pubDateStr)) {
            try {
                pubDate = new SimpleDateFormat(getDatePattern(), Locale.ENGLISH).parse(pubDateStr);
            } catch (ParseException e) {
                pubDate = null;
            }
        }

        News news = new News();
        news.setChannel(channel);
        news.setTitle(title);
        news.setDescription(description);
        news.setLink(link);
        news.setDate(pubDate);
        news.setImage(image);
        return news;
    }

    protected String getNewsImage(Element newsElement) {
        Elements imgElement = newsElement.getElementsByTag("enclosure");
        if (imgElement != null && imgElement.size() > 0) {
            return imgElement.first().text();
        }
        return null;
    }

    protected String getNewsDescription(Element newsElement) {
        return newsElement.getElementsByTag("description").first().text();
    }

    protected String getDatePattern() {
        return "EEE, dd MMM yyyy HH:mm:ss Z";
    }
}
