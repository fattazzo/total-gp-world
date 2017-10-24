package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

/**
 * @author fattazzo
 *
 *
 * date: 24/08/17
 */
class StatsData {

    var value: Float = 0.toFloat()
        private set
    val value2: Float
    val value3: Float
    val value4: Float

    var label: String? = null
        private set

    constructor(value: Float, label: String) {
        this.value = value
        this.label = label
        this.value2 = 0.toFloat()
        this.value3 = 0.toFloat()
        this.value4 = 0.toFloat()
    }

    constructor(value: Float, value2: Float, value3: Float, value4: Float, label: String) {
        this.value = value
        this.value2 = value2
        this.value3 = value3
        this.value4 = value4
        this.label = label
    }
}
