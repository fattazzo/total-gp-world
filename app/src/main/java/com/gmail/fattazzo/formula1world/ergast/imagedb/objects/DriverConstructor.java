package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "driversConstructors")
public class DriverConstructor extends Model {

    @Column
    long driverId;

    @Column
    long constructorId;

    @Column
    int year;
}
