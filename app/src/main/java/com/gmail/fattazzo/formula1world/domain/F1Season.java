package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/07/17
 */
public class F1Season {

    public Integer year;

    public String url;

    public boolean current;

    public String description;

    @Override
    public String toString() {
        return "F1Season{" +
                "year=" + year +
                ", url='" + url + '\'' +
                ", current=" + current +
                ", description='" + description + '\'' +
                '}';
    }
}
