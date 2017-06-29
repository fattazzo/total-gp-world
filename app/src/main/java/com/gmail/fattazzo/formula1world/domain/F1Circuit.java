package com.gmail.fattazzo.formula1world.domain;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1Circuit implements Serializable {

    public String circuitRef;

    public String url;

    public String name;

    public F1Location location;

    @Override
    public String toString() {
        return "F1Circuit{" +
                "circuitRef='" + circuitRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
