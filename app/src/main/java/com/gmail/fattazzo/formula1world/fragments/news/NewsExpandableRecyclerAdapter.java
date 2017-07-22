package com.gmail.fattazzo.formula1world.fragments.news;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.news.objects.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.List;


class NewsExpandableRecyclerAdapter extends RecyclerView.Adapter<NewsExpandableRecyclerAdapter.ViewHolder> {

    private List<News> newsList;

    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;

    private DisplayImageOptions options;

    NewsExpandableRecyclerAdapter(List<News> newsList) {
        this.newsList = newsList;
        for (int i = 0; i < newsList.size(); i++) {
            expandState.append(i, false);
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.stat_notify_sync)
                .showImageForEmptyUri(android.R.drawable.star_on)
                .showImageOnFail(android.R.drawable.ic_delete)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    private ObjectAnimator createRotateAnimator(final View target, int duration) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(target, "rotationY", 0.0f, 360f);
        animation.setDuration(duration);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        return animation;
    }

    @Override
    public NewsExpandableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsExpandableRecyclerAdapter.ViewHolder viewHolder, final int i) {

        News news = newsList.get(i);

        String localeDateString;
        try {
            localeDateString = android.text.format.DateUtils.formatDateTime(context.getApplicationContext(),
                    news.getDate().getTime(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR);
        } catch (Exception e) {
            localeDateString = "";
        }

        viewHolder.setIsRecyclable(false);
        viewHolder.news_title.setText(Html.fromHtml(news.getTitle()).toString());
        viewHolder.news_description.setText(Html.fromHtml(news.getDescription()).toString());
        viewHolder.news_date.setText(localeDateString);
        viewHolder.news_open_button.setTag(news.getLink());
        viewHolder.news_open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(viewHolder.news_open_button.getTag().toString()));
                context.startActivity(i);
            }
        });

        ImageLoader.getInstance().displayImage(news.getChannel().getImage(), viewHolder.news_image, options);

        final boolean isExpanded = expandState.get(i);
        viewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        viewHolder.news_image.setRotation(expandState.get(i) ? 360f : 0f);
        viewHolder.baseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(viewHolder.expandableLayout, viewHolder.news_image, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private void onClickButton(final RelativeLayout expandableLayout, final View rotationView, final int i) {

        if (expandableLayout.getVisibility() == View.VISIBLE) {
            createRotateAnimator(rotationView, 1800).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        } else {
            createRotateAnimator(rotationView, 1800).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout baseLayout;
        RelativeLayout expandableLayout;
        private TextView news_title, news_description, news_date;

        private ImageView news_image;

        private Button news_open_button;

        ViewHolder(View view) {
            super(view);

            news_title = (TextView) view.findViewById(R.id.news_title);
            news_description = (TextView) view.findViewById(R.id.news_description);
            news_date = (TextView) view.findViewById(R.id.news_date);
            news_image = (ImageView) view.findViewById(R.id.news_image);
            news_open_button = (Button) view.findViewById(R.id.news_open_button);

            expandableLayout = (RelativeLayout) view.findViewById(R.id.expandableLayout);
            baseLayout = (RelativeLayout) view.findViewById(R.id.baseLayout);
        }
    }
}