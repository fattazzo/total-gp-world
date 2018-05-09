/*
 * Project: total-gp-world
 * File: NewsListAdapter.kt
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

package com.gmail.fattazzo.formula1world.fragments.news

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.news.objects.News
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer

class NewsListAdapter(context: Context, private val newsList: List<News>) : BaseAdapter() {
    private val inflater: LayoutInflater
    private val options: DisplayImageOptions

    init {
        inflater = LayoutInflater.from(context)

        options = DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.stat_notify_sync)
                .showImageForEmptyUri(android.R.drawable.star_on)
                .showImageOnFail(android.R.drawable.ic_delete)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(CircleBitmapDisplayer(Color.WHITE, 8f))
                .build()
    }

    override fun getCount(): Int {
        return newsList.size
    }

    override fun getItem(position: Int): Any {
        return newsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.news_item_list, parent, false)
            holder = ViewHolder()
            holder.title = view!!.findViewById<TextView>(R.id.news_title)
            holder.image = view.findViewById<ImageView>(R.id.news_image)
            view.tag = holder
        } else {
            holder = view!!.tag as ViewHolder
        }

        val news = newsList[position]
        holder.title!!.text = news.title

        ImageLoader.getInstance().displayImage(news.channel!!.image, holder.image!!, options)

        return view
    }

    internal class ViewHolder {
        var title: TextView? = null
        var image: ImageView? = null
    }
}