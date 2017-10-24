package com.gmail.fattazzo.formula1world.ergast

import com.gmail.fattazzo.formula1world.ergast.json.exceptions.QueryLimitException
import com.gmail.fattazzo.formula1world.ergast.json.exceptions.QueryOffsetException

import org.androidannotations.annotations.EBean

import java.util.Calendar


@EBean(scope = EBean.Scope.Singleton)
open class Ergast internal constructor() {

    val series: String
    var season: Int = 0
        get() {
            if (field == CURRENT_SEASON) {
                this.season = Calendar.getInstance().get(Calendar.YEAR)
            }
            return field
        }
    private var limit: Int = 0
    private var offset: Int = 0

    init {
        this.offset = DEFAULT_OFFSET
        this.limit = DEFAULT_LIMIT
        this.season = CURRENT_SEASON
        this.series = "f1"
    }

    fun getLimit(): Int {
        return limit
    }

    /**
     * @param limit is a number of results that are returned. Up to a maximum value of 1000.
     * Please use the smallest value that your application needs.
     * @throws QueryLimitException if limit is less than 0 or large than 1000
     */
    fun setLimit(limit: Int) {
        if (limit > 1000) {
            throw QueryLimitException("Limit requires to be no large than 1000")
        } else if (limit < 0) {
            throw QueryLimitException("Limit requires to be a positive number")
        }

        this.limit = limit
    }

    fun getOffset(): Int {
        return offset
    }

    /**
     * @param offset specifies an offset into the result set.
     * @throws QueryOffsetException if offset is less than 0.
     */
    fun setOffset(offset: Int) {
        if (offset < 0) {
            throw QueryOffsetException("Offset requires to be a positive number")
        }
        this.offset = offset
    }

    companion object {

        val CURRENT_SEASON = -1
        val DEFAULT_LIMIT = 60
        val DEFAULT_OFFSET = 0
        val NO_ROUND = -1
    }

}
