package com.gmail.fattazzo.formula1world.ergast.json.objects;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
class PitStop {
    private String driverId;
    private int stop;
    private int lap;
    private String time;
    private String duration;

    public PitStop(String driverId, int stop, int lap, String time, String duration) {
        this.driverId = driverId;
        this.stop = stop;
        this.lap = lap;
        this.time = time;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "PitStop{" +
                "driverId='" + driverId + '\'' +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
