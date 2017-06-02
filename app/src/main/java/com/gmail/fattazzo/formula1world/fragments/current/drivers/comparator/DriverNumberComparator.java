package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator;

import com.gmail.fattazzo.formula1world.ergast.objects.Driver;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/04/17
 */
public class DriverNumberComparator implements Comparator<Driver> {

    @Override
    public int compare(Driver d1, Driver d2) {
        return d1.getPermanentNumber() - d2.getPermanentNumber();
    }
}
