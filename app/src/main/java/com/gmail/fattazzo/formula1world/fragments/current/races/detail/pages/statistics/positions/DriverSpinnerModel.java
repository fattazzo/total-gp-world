package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions;

import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Driver;

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/07/17
 */
public class DriverSpinnerModel {

    private F1Driver driver;
    private F1Constructor constructor;

    public DriverSpinnerModel(F1Driver driver, F1Constructor constructor) {
        this.driver = driver;
        this.constructor = constructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverSpinnerModel that = (DriverSpinnerModel) o;

        return driver.equals(that.driver);

    }

    @Override
    public int hashCode() {
        return driver.hashCode();
    }

    public F1Driver getDriver() {
        return driver;
    }

    public void setDriver(F1Driver driver) {
        this.driver = driver;
    }

    public F1Constructor getConstructor() {
        return constructor;
    }

    public void setConstructor(F1Constructor constructor) {
        this.constructor = constructor;
    }
}
