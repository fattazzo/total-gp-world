package com.gmail.fattazzo.formula1world.ergast.imagedb.objects;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.gmail.fattazzo.formula1world.domain.F1Circuit;
import com.gmail.fattazzo.formula1world.domain.F1Location;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/06/17
 */
@Table(name = "circuits")
public class Circuit extends Model {

    @Column
    public String circuitRef;

    @Column
    String url;

    @Column
    public String name;

    @Column
    public float lat;

    @Column
    public float lng;

    @Column
    int alt;

    @Column
    public String location;

    @Column
    String country;

    public F1Circuit toF1Circuit() {
        F1Circuit f1Circuit = new F1Circuit();
        f1Circuit.circuitRef = this.circuitRef;
        f1Circuit.url = this.url;
        f1Circuit.name = this.name;
        f1Circuit.location = new F1Location(lat, lng, location, country);
        return f1Circuit;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "circuitId=" + getId() +
                ", circuitRef='" + circuitRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", alt=" + alt +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
