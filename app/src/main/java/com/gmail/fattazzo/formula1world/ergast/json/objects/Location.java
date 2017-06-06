package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Location;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class Location {
    private float lat;
    private float lng;
    private String locality;
    private String country;

    public Location(float lat, float lng, String locality, String country) {
        this.lat = lat;
        this.lng = lng;
        this.locality = locality;
        this.country = country;
    }

    F1Location toF1Location() {
        F1Location f1Location = new F1Location();
        f1Location.lat = this.lat;
        f1Location.lng = this.lng;
        f1Location.locality = this.locality;
        f1Location.country = this.country;
        return f1Location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
