package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator;

import com.gmail.fattazzo.formula1world.ergast.objects.Driver;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/04/17
 */
public class DriverNameComparator implements Comparator<Driver> {

    @Override
    public int compare(Driver d1, Driver d2) {
        String d1FullName = d1.getGivenName() + " " + d1.getFamilyName();
        String d2FullName = d2.getGivenName() + " " + d2.getFamilyName();

        return d1FullName.compareTo(d2FullName);
    }
}
