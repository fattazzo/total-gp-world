package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1Season;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "seasons")
public class Season extends Model {

    @Column
    public String url;

    @Column
    public String description;

    public F1Season toF1Season() {
        F1Season f1Season = new F1Season();
        f1Season.year = getId().intValue();
        f1Season.url = url;
        f1Season.description = description;
        f1Season.current = false;
        return f1Season;
    }

    @Override
    public String toString() {
        return "Season{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
