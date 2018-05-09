/*
 * Project: total-gp-world
 * File: TimeAxisValueFormatter.kt
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

package com.gmail.fattazzo.formula1world.view.chart.valueformatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class TimeAxisValueFormatter(private val pattern: String, private val entries: List<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        //String result;
        //try {
        val idx = value.toInt() % entries.size
        //int millisec = entries.get(idx);
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(0);
        //calendar.add(Calendar.MILLISECOND,millisec);
        //result = DateFormatUtils.format(calendar,pattern);
        //result = String.valueOf(millisec);
        return entries[idx]
        //} catch (Exception e) {
        //    result = "";
        //}
        //Log.e(TAG,"Value: " + value + " - formatted: " + result);
        //return result;
    }

    companion object {

        private val TAG = TimeAxisValueFormatter::class.java.simpleName
    }
}
