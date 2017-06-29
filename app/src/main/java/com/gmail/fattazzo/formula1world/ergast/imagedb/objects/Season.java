package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "seasons")
public class Season extends Model {

    @Column
    String url;

    @Override
    public String toString() {
        return "Season{" +
                "year=" + getId() +
                ", url='" + url + '\'' +
                '}';
    }
}
