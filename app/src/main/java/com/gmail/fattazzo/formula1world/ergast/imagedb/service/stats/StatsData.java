package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

/**
 * @author fattazzo
 *         <p/>
 *         date: 24/08/17
 */
public class StatsData {

    private float value;
    private float value2;
    private float value3;
    private float value4;

    private String label;

    public StatsData(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public StatsData(float value, float value2, float value3, float value4, String label) {
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.label = label;
    }

    public float getValue2() {
        return value2;
    }

    public float getValue3() {
        return value3;
    }

    public float getValue4() {
        return value4;
    }

    public float getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
