package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "status")
public class Status extends Model {

    @Column
    String status;

    @Override
    public String toString() {
        return "Status{" +
                "statusid=" + getId() +
                ", status='" + status + '\'' +
                '}';
    }
}
