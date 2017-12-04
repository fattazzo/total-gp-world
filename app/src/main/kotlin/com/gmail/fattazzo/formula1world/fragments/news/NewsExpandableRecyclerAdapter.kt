package com.gmail.fattazzo.formula1world.fragments.news

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.format.DateUtils
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.gmail.fattazzo.formula1world.R
import com.gmail.fattazzo.formula1world.news.objects.News
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer


internal class NewsExpandableRecyclerAdapter(private val newsList: List<News>) : RecyclerView.Adapter<NewsExpandableRecyclerAdapter.ViewHolder>() {

    private val expandState = SparseBooleanArray()
    private var context: Context? = null

    private val options: DisplayImageOptions

    init {
        for (i in newsList.indices) {
            expandState.append(i, false)
        }

        options = DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.stat_notify_sync)
                .showImageForEmptyUri(android.R.drawable.star_on)
                .showImageOnFail(android.R.drawable.ic_delete)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(CircleBitmapDisplayer(Color.WHITE, 5f))
                .build()
    }

    private fun createRotateAnimator(target: View, duration: Int): ObjectAnimator {
        val animation = ObjectAnimator.ofFloat(target, "rotationY", 0.0f, 360f)
        animation.duration = duration.toLong()
        animation.interpolator = AccelerateDecelerateInterpolator()
        return animation
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsExpandableRecyclerAdapter.ViewHolder {
        this.context = viewGroup.context
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.news_item_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NewsExpandableRecyclerAdapter.ViewHolder, i: Int) {

        val news = newsList[i]

        var localeDateString: String
        try {
            localeDateString = android.text.format.DateUtils.formatDateTime(context!!.applicationContext,
                    news.date!!.time,
                    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)
        } catch (e: Exception) {
            localeDateString = ""
        }

        viewHolder.setIsRecyclable(false)
        viewHolder.news_title.text = Html.fromHtml(news.title).toString()
        viewHolder.news_description.text = Html.fromHtml(news.description).toString()
        viewHolder.news_date.text = localeDateString
        viewHolder.news_open_button.tag = news.link
        viewHolder.news_open_button.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.data = Uri.parse(viewHolder.news_open_button.tag.toString())
            context!!.startActivity(i)
        }

        ImageLoader.getInstance().displayImage(news.channel!!.image, viewHolder.news_image, options)

        val isExpanded = expandState.get(i)
        viewHolder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

        viewHolder.news_image.rotation = if (expandState.get(i)) 360f else 0f
        viewHolder.baseLayout.setOnClickListener { onClickButton(viewHolder.expandableLayout, viewHolder.news_image, i) }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    private fun onClickButton(expandableLayout: RelativeLayout, rotationView: View, i: Int) {

        if (expandableLayout.visibility == View.VISIBLE) {
            createRotateAnimator(rotationView, 1800).start()
            expandableLayout.visibility = View.GONE
            expandState.put(i, false)
        } else {
            createRotateAnimator(rotationView, 1800).start()
            expandableLayout.visibility = View.VISIBLE
            expandState.put(i, true)
        }
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var baseLayout: RelativeLayout
        var expandableLayout: RelativeLayout
        val news_title: TextView
        val news_description: TextView
        val news_date: TextView

        val news_image: ImageView

        val news_open_button: Button

        init {

            news_title = view.findViewById<TextView>(R.id.news_title)
            news_description = view.findViewById<TextView>(R.id.news_description)
            news_date = view.findViewById<TextView>(R.id.news_date)
            news_image = view.findViewById<ImageView>(R.id.news_image)
            news_open_button = view.findViewById<Button>(R.id.news_open_button)

            expandableLayout = view.findViewById<RelativeLayout>(R.id.expandableLayout)
            baseLayout = view.findViewById<RelativeLayout>(R.id.baseLayout)
        }
    }
}