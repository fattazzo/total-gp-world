package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator;

import com.gmail.fattazzo.formula1world.domain.F1Driver;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/04/17
 */
public class DriverNumberComparator implements Comparator<F1Driver> {

    @Override
    public int compare(F1Driver d1, F1Driver d2) {
        return d1.number - d2.number;
    }
}
