package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
public class StatsData {

    private float value;
    private String label;

    public StatsData(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
