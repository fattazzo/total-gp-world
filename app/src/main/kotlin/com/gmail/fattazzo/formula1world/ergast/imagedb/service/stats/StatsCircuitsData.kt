/*
 * Project: total-gp-world
 * File: StatsCircuitsData.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats

/**
 * @author fattazzo
 *
 *
 * date: 09/10/17
 */
class StatsCircuitsData {

    var id: Int = 0
        private set
    var name: String? = null
        private set

    var count: Int = 0

    var driverId: Int = 0
    var driverName: String? = null

    var constructorId: Int = 0
    var constructorName: String? = null
    var constructorRef: String? = null

    var type: Type? = null
        private set

    val winnerName: String
        get() {
            when (type) {
                StatsCircuitsData.Type.CONSTRUCTORS_WINNER -> return constructorName.orEmpty()
                StatsCircuitsData.Type.DRIVERS_WINNER -> return driverName.orEmpty()
                else -> return ""
            }
        }

    constructor(id: Int, name: String, driverId: Int, driverName: String?, constructorId: Int, constructorName: String?, constructorRef: String?, type: Type) {
        this.id = id
        this.name = name

        this.driverId = driverId
        this.driverName = driverName

        this.constructorId = constructorId
        this.constructorName = constructorName
        this.constructorRef = constructorRef

        this.type = type
    }

    constructor(id: Int, name: String, count: Int) {
        this.id = id
        this.name = name

        this.type = Type.COUNT

        this.count = count
    }

    enum class Type {
        COUNT, DRIVERS_WINNER, CONSTRUCTORS_WINNER
    }
}
