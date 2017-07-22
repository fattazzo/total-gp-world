package com.gmail.fattazzo.formula1world.news;

import android.graphics.Bitmap;

import com.gmail.fattazzo.formula1world.CustomRobolectricRunner;
import com.gmail.fattazzo.formula1world.news.objects.Channel;
import com.gmail.fattazzo.formula1world.news.objects.News;
import com.gmail.fattazzo.formula1world.service.NewsService;
import com.gmail.fattazzo.formula1world.service.NewsService_;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 21/07/17
 */
@RunWith(CustomRobolectricRunner.class)
public class NewsTest {

    private NewsService newsService;

    @Before
    public void init() {
        newsService = NewsService_.getInstance_(RuntimeEnvironment.application);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(RuntimeEnvironment.application)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Test
    public void testNewsSourcesCount() {
        Map<String, List<NewsSource>> newsSources = newsService.loadAllNewsSources();
        assertNotNull("No news sources loaded", newsSources);
        assertNotEquals("No news sources loaded", 0, newsSources.size());
        assertTrue("No news for language IT", newsSources.containsKey("IT"));
        assertTrue("No news for language EN", newsSources.containsKey("EN"));
        System.out.println("News sources found: " + newsSources.size());
    }

    @Test
    public void testNewsContent() {
        Map<String, List<NewsSource>> newsSources = newsService.loadAllNewsSources();

        for (Map.Entry<String, List<NewsSource>> entry : newsSources.entrySet()) {
            System.out.println("News for language: " + entry.getKey());

            for (NewsSource source : entry.getValue()) {
                assertNotNull("Website value not valid for source", source.getWebsite());
                assertNotNull("Url value not valid for source " + source.getWebsite(), source.getUrl());
                assertNotNull("Type value not valid for source " + source.getWebsite(), source.getType());

                System.out.println("Source: " + source.getWebsite());
                List<News> news = newsService.loadNews(source);
                assertNotNull("No news found for source " + source.getWebsite(), news);
                assertNotEquals("No news found for source " + source.getWebsite(), 0, news.size());

                for (News newsEntry : news) {
                    assertNotNull("No title for news", newsEntry.getTitle());
                    assertNotNull("No description for news " + newsEntry.getTitle(), newsEntry.getDescription());
                    assertNotNull("No link for news " + newsEntry.getTitle(), newsEntry.getLink());
                    assertNotNull("No date for news " + newsEntry.getTitle(), newsEntry.getDate());
                    if (StringUtils.isNotBlank(newsEntry.getImage())) {
                        Bitmap bmp = ImageLoader.getInstance().loadImageSync(newsEntry.getImage());
                        assertNotNull("Channel image download error", bmp);
                    }
                }
                System.out.println(news.size() + " news found for " + source.getWebsite());

            }

        }
    }

    @Test
    public void testChannelsContent() {
        Map<String, List<NewsSource>> newsSources = newsService.loadAllNewsSources();

        for (Map.Entry<String, List<NewsSource>> entry : newsSources.entrySet()) {
            System.out.println("News for language: " + entry.getKey());

            for (NewsSource source : entry.getValue()) {
                assertNotNull("Website value not valid for source", source.getWebsite());
                assertNotNull("Url value not valid for source " + source.getWebsite(), source.getUrl());
                assertNotNull("Type value not valid for source " + source.getWebsite(), source.getType());

                System.out.println("Source: " + source.getWebsite());
                List<News> news = newsService.loadNews(source);
                assertNotNull("No news found for source " + source.getWebsite(), news);
                assertNotEquals("No news found for source " + source.getWebsite(), 0, news.size());
                // channel
                Channel channel = news.get(0).getChannel();
                assertNotNull("No channel for news", channel);
                assertNotNull("No channel title for news", channel.getTitle());
                assertNotNull("No channel description for news", channel.getDescription());
                assertNotNull("No channel link for news", channel.getLink());
                assertNotNull("No channel image for news", channel.getImage());

                Bitmap bmp = ImageLoader.getInstance().loadImageSync(channel.getImage());
                assertNotNull("Channel image download error", bmp);
            }
        }
    }
}
