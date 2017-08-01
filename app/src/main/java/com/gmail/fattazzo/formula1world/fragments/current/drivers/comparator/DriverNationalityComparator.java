package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator;

import com.gmail.fattazzo.formula1world.domain.F1Driver;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/04/17
 */
public class DriverNationalityComparator implements Comparator<F1Driver> {

    @Override
    public int compare(F1Driver d1, F1Driver d2) {
        return StringUtils.defaultString(d1.nationality).compareTo(StringUtils.defaultString(d2.nationality));
    }
}
