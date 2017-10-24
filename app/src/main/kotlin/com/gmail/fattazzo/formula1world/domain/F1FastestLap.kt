package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1FastestLap {

    var rank: Int = 0

    var lap: Int = 0

    var time: F1Time? = null

    var averageSpeed: F1AverageSpeed? = null

    constructor() {}

    constructor(rank: Int, lap: Int, time: F1Time, averageSpeed: F1AverageSpeed) {
        this.rank = rank
        this.lap = lap
        this.time = time
        this.averageSpeed = averageSpeed
    }

    override fun toString(): String {
        return "FastestLap{" +
                "rank=" + rank +
                ", lap=" + lap +
                ", time=" + time +
                ", averageSpeed=" + averageSpeed +
                '}'
    }
}
