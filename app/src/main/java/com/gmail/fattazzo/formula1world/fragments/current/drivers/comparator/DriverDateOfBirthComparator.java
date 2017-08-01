package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator;

import com.gmail.fattazzo.formula1world.domain.F1Driver;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/04/17
 */
public class DriverDateOfBirthComparator implements Comparator<F1Driver> {

    @Override
    public int compare(F1Driver d1, F1Driver d2) {
        Long millisec1 = d1.dateOfBirth == null ? 0 : d1.dateOfBirth.getTime();
        Long millisec2 = d2.dateOfBirth == null ? 0 : d2.dateOfBirth.getTime();
        return millisec1.compareTo(millisec2)*-1;
    }
}
