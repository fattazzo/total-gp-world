package com.gmail.fattazzo.formula1world.ergast.json.objects;

/**
 * @author fattazzo
 *         <p/>
 *         date: 06/06/17
 */
public class FinishingStatus {
    private int statusId;
    private int count;
    private String status;

    public FinishingStatus(int statusId, int count, String status) {
        this.statusId = statusId;
        this.count = count;
        this.status = status;
    }

    @Override
    public String toString() {
        return "FinishingStatus{" +
                "statusId=" + statusId +
                ", count=" + count +
                ", status='" + status + '\'' +
                '}';
    }
}
