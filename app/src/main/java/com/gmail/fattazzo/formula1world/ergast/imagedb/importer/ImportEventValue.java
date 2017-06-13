package com.gmail.fattazzo.formula1world.ergast.imagedb.importer;

import android.support.annotation.NonNull;

import com.activeandroid.Model;

/**
 * @author fattazzo
 *         <p/>
 *         date: 07/06/17
 */
public class ImportEventValue {

    private ImportStep step;

    private Class<? extends Model> model;

    private int currentOp;

    private int totalOp;

    public ImportEventValue(@NonNull ImportStep step, Class<? extends Model> model, int currentOp, int totalOp) {
        this.step = step;
        this.model = model;
        this.currentOp = currentOp;
        this.totalOp = totalOp;
    }

    public ImportStep getStep() {
        return step;
    }

    public Class<? extends Model> getModel() {
        return model;
    }

    public int getCurrentOp() {
        return currentOp;
    }

    public int getTotalOp() {
        return totalOp;
    }

    @Override
    public String toString() {
        return "ImportEventValue{" +
                "step=" + step +
                ", model=" + model +
                ", currentOp=" + currentOp +
                ", totalOp=" + totalOp +
                '}';
    }
}
