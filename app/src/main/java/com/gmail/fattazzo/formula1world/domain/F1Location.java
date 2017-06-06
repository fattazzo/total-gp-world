package com.gmail.fattazzo.formula1world.domain;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class F1Location {

    public float lat;
    public float lng;
    public String locality;
    public String country;

    public F1Location() {
    }

    public F1Location(float lat, float lng, String locality, String country) {
        this.lat = lat;
        this.lng = lng;
        this.locality = locality;
        this.country = country;
    }

    @Override
    public String toString() {
        return "F1Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
