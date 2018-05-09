/*
 * Project: total-gp-world
 * File: StatsDataImageLabelListAdapter.kt
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
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats.StatsData

import java.text.DecimalFormat

abstract class StatsDataImageLabelListAdapter(context: Context, private val data: List<StatsData>, private val valueFormat: DecimalFormat) : BaseAdapter() {
    private val mInflater: LayoutInflater

    init {

        mInflater = (context as Activity).layoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val data = getItem(position)

        val holder: ViewHolderItem

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.stats_row, null)

            holder = ViewHolderItem()
            holder.textLeft = convertView!!.findViewById<TextView>(R.id.textLeft)
            holder.textCenter = convertView.findViewById<TextView>(R.id.textCenter)
            holder.textRight = convertView.findViewById<TextView>(R.id.textRight)
            holder.imageView = convertView.findViewById<ImageView>(R.id.imageView)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolderItem
        }

        holder.textLeft!!.text = (position + 1).toString()
        holder.textCenter!!.text = data.label
        holder.textRight!!.text = valueFormat.format(data.value.toDouble())
        val bitmap = getImage(data)
        if (bitmap == null) {
            holder.imageView!!.visibility = View.GONE
        } else {
            holder.imageView!!.visibility = View.VISIBLE
            holder.imageView!!.setImageBitmap(bitmap)
        }

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

    abstract fun getImage(data: StatsData): Bitmap?

    private class ViewHolderItem {
        internal var textLeft: TextView? = null
        internal var imageView: ImageView? = null
        internal var textCenter: TextView? = null
        internal var textRight: TextView? = null
    }
}