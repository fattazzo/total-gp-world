/*
 * Project: total-gp-world
 * File: ConstructorsListAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.current.constructors

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1Constructor
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.apache.commons.collections4.CollectionUtils
import java.util.*

@EBean
internal open class ConstructorsListAdapter : BaseAdapter() {

    @RootContext
    lateinit var context: Context

    private var constructors: MutableList<F1Constructor>? = null

    fun setConstructors(constructors: List<F1Constructor>) {
        this.constructors = ArrayList()
        CollectionUtils.addAll(this.constructors, CollectionUtils.emptyIfNull(constructors))
    }

    @AfterInject
    fun initAdapter() {
        constructors = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val constructorItemView: ConstructorItemView
        if (convertView == null) {
            constructorItemView = ConstructorItemView_.build(context)
        } else {
            constructorItemView = convertView as ConstructorItemView
        }

        constructorItemView.bind(getItem(position))

        return constructorItemView
    }

    override fun getCount(): Int {
        return constructors!!.size
    }

    override fun getItem(position: Int): F1Constructor {
        return constructors!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        constructors!!.clear()
    }
}