package com.gmail.fattazzo.formula1world.news;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/07/17
 */
public class NewsSource {

    String website;

    String url;

    RaederType type;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RaederType getType() {
        return type;
    }

    public void setType(RaederType type) {
        this.type = type;
    }
}
