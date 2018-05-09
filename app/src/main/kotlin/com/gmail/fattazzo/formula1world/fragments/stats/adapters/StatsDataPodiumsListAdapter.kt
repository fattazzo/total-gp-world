/*
 * Project: total-gp-world
 * File: StatsDataPodiumsListAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.stats.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData

import java.text.DecimalFormat

class StatsDataPodiumsListAdapter(context: Context, private val data: List<StatsData>, private val valueFormat: DecimalFormat) : BaseAdapter() {
    private val mInflater: LayoutInflater

    init {

        mInflater = (context as Activity).layoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val data = getItem(position)

        val holder: ViewHolderItem

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stats_four_values_row, null)

            holder = ViewHolderItem()
            holder.textLeft = convertView!!.findViewById<TextView>(R.id.textLeft)
            holder.textLabel = convertView.findViewById<TextView>(R.id.textLabel)
            holder.textValueOne = convertView.findViewById<TextView>(R.id.textValueOne)
            holder.textValueTwo = convertView.findViewById<TextView>(R.id.textValueTwo)
            holder.textValueThree = convertView.findViewById<TextView>(R.id.textValueThree)
            holder.textValueFour = convertView.findViewById<TextView>(R.id.textValueFour)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderItem
        }

        holder.textLeft!!.text = (position + 1).toString()
        holder.textLabel!!.text = data.label
        holder.textValueOne!!.text = valueFormat.format(data.value.toDouble())
        holder.textValueTwo!!.text = valueFormat.format(data.value2.toDouble())
        holder.textValueThree!!.text = valueFormat.format(data.value3.toDouble())
        holder.textValueFour!!.text = valueFormat.format(data.value4.toDouble())

        return convertView
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): StatsData {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolderItem {
        internal var textLeft: TextView? = null
        internal var textLabel: TextView? = null
        internal var textValueOne: TextView? = null
        internal var textValueTwo: TextView? = null
        internal var textValueThree: TextView? = null
        internal var textValueFour: TextView? = null
    }
}