package com.gmail.fattazzo.formula1world.fragments.news;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.news.objects.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {

    private List<News> newsList;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public NewsListAdapter(Context context, List<News> news) {
        this.newsList = news;
        inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.stat_notify_sync)
                .showImageForEmptyUri(android.R.drawable.star_on)
                .showImageOnFail(android.R.drawable.ic_delete)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 8))
                .build();
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.news_item_list, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.news_title);
            holder.image = (ImageView) view.findViewById(R.id.news_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        News news = newsList.get(position);
        holder.title.setText(news.getTitle());

        ImageLoader.getInstance().displayImage(news.getChannel().getImage(), holder.image, options);

        return view;
    }

    static class ViewHolder {
        TextView title;
        ImageView image;
    }
}