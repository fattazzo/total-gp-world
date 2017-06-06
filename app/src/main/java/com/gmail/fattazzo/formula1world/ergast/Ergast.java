package com.gmail.fattazzo.formula1world.ergast;

import com.gmail.fattazzo.formula1world.ergast.json.exceptions.QueryLimitException;
import com.gmail.fattazzo.formula1world.ergast.json.exceptions.QueryOffsetException;

import org.androidannotations.annotations.EBean;


@EBean(scope = EBean.Scope.Singleton)
public class Ergast {

    public final static int CURRENT_SEASON = -1;
    public final static int DEFAULT_LIMIT = 60;
    public final static int DEFAULT_OFFSET = 0;
    public final static int NO_ROUND = -1;

    private String series;
    private int season;
    private int limit;
    private int offset;

    Ergast() {
        this.offset = DEFAULT_OFFSET;
        this.limit = DEFAULT_LIMIT;
        this.season = CURRENT_SEASON;
        this.series = "f1";
    }

    public String getSeries() {
        return series;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * @param limit is a number of results that are returned. Up to a maximum value of 1000.
     *              Please use the smallest value that your application needs.
     * @throws QueryLimitException if limit is less than 0 or large than 1000
     */
    public void setLimit(int limit) {
        if (limit > 1000) {
            throw new QueryLimitException("Limit requires to be no large than 1000");
        } else if (limit < 0) {
            throw new QueryLimitException("Limit requires to be a positive number");
        }

        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    /**
     * @param offset specifies an offset into the result set.
     * @throws QueryOffsetException if offset is less than 0.
     */
    public void setOffset(int offset) {
        if (offset < 0) {
            throw new QueryOffsetException("Offset requires to be a positive number");
        }
        this.offset = offset;
    }

}
