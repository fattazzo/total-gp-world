package com.gmail.fattazzo.formula1world.ergast.json.objects;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class Timing {
    private String driverId;
    private int position;
    private String time;

    public Timing(String driverId, int position, String time) {
        this.driverId = driverId;
        this.position = position;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Timing{" +
                "driverId='" + driverId + '\'' +
                ", position=" + position +
                ", time='" + time + '\'' +
                '}';
    }
}
