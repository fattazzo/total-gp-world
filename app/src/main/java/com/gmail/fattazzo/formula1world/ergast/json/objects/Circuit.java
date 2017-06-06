package com.gmail.fattazzo.formula1world.ergast.json.objects;

import com.gmail.fattazzo.formula1world.domain.F1Circuit;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class Circuit {
    private String circuitId;
    private String url;
    private String circuitName;
    private Location location;

    public Circuit(String circuitId, String url, String circuitName, Location location) {
        this.circuitId = circuitId;
        this.url = url;
        this.circuitName = circuitName;
        this.location = location;
    }

    F1Circuit toF1Circuit() {
        F1Circuit f1Circuit = new F1Circuit();
        f1Circuit.circuitRef = this.circuitId;
        f1Circuit.name = this.circuitName;
        f1Circuit.url = this.url;
        if (this.location != null) {
            f1Circuit.location = this.location.toF1Location();
        }
        return f1Circuit;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "circuitId='" + circuitId + '\'' +
                ", url='" + url + '\'' +
                ", circuitName='" + circuitName + '\'' +
                ", location=" + location +
                '}';
    }
}
