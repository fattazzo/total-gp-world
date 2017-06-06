package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
public class F1ConstructorStandings {

    public int position;

    public String positionText;

    public float points;

    public int wins;

    public F1Constructor constructor;

    @Override
    public String toString() {
        return "F1ConstructorStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}';
    }
}
