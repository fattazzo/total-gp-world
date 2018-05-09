/*
 * Project: total-gp-world
 * File: F1PitStopDriverComparator.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator

import com.gmail.fattazzo.formula1world.domain.F1PitStop
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 03/07/17
 */
class F1PitStopDriverComparator : Comparator<F1PitStop> {

    override fun compare(pit1: F1PitStop, pit2: F1PitStop): Int {
        val nameCompare = StringUtils.defaultString(pit1.driver!!.fullName).compareTo(StringUtils.defaultString(pit2.driver!!.fullName))
        return if (nameCompare != 0) {
            nameCompare
        } else Integer.valueOf(pit1.stop)!!.compareTo(pit2.stop)

    }
}
