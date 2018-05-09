/*
 * Project: total-gp-world
 * File: StatsData.kt
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
