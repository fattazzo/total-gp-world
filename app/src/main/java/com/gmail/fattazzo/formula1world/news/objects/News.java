package com.gmail.fattazzo.formula1world.news.objects;

import java.util.Date;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class News {

    private Channel channel;
    private String title;
    private String description;
    private String link;
    private Date date;
    private String image;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
