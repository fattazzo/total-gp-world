/*
 * Project: total-gp-world
 * File: FragmentUtils.kt
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

package com.gmail.fattazzo.formula1world.utils

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.gmail.fattazzo.formula1world.R

/**
 * @author fattazzo
 *
 *
 * date: 20/10/17
 */
object FragmentUtils {

    @JvmOverloads fun replace(activity: Activity?, fragment: Fragment, containerResId: Int = R.id.container) {
        val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(containerResId, fragment, fragment.javaClass.simpleName.replace("_", "")).commit()
    }

    @JvmOverloads fun add(activity: Activity?, fragment: Fragment, containerResId: Int = R.id.container) {
        val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.add(containerResId, fragment, fragment.javaClass.simpleName.replace("_", "")).commit()
    }
}
