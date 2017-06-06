package com.gmail.fattazzo.formula1world.domain;

import java.io.Serializable;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
public class F1Constructor implements Serializable {

    public String constructorRef;

    public String url;

    public String name;

    public String nationality;

    @Override
    public String toString() {
        return "Constructor{" +
                "constructorRef='" + constructorRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
