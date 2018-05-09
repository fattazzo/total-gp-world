/*
 * Project: total-gp-world
 * File: ConstructorStandingsListAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.home.constructorstandings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.util.*

@EBean
internal open class ConstructorStandingsListAdapter : BaseAdapter() {

    private var constructors: MutableList<F1ConstructorStandings>? = null

    @RootContext
    lateinit var context: Context

    fun setConstructors(constructors: MutableList<F1ConstructorStandings>) {
        this.constructors = constructors
    }

    @AfterInject
    fun initAdapter() {
        constructors = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val constructorStandingsItemView: ConstructorStandingsItemView
        if (convertView == null) {
            constructorStandingsItemView = ConstructorStandingsItemView_.build(context)
        } else {
            constructorStandingsItemView = convertView as ConstructorStandingsItemView
        }

        constructorStandingsItemView.bind(getItem(position))

        return constructorStandingsItemView
    }

    override fun getCount(): Int {
        return constructors!!.size
    }

    override fun getItem(position: Int): F1ConstructorStandings {
        return constructors!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun clearItems() {
        constructors!!.clear()
    }
}