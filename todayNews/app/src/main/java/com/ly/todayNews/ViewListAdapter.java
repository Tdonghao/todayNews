package com.ly.todayNews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ViewListAdapter extends ArrayAdapter<Map<String,Object>> {

    public static final String KEY_CONTENT = "title";
    public static final String KEY_URL = "key_url";
    public static final String KEY_TAG = "tag";
    public static final String KEY_IMG = "img";
    private final LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public ViewListAdapter(Context context, int layoutResourceId, List<Map<String,Object>> data) {
        super(context,layoutResourceId,data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ItemTitle = (TextView) convertView.findViewById(R.id.tag_title);
            viewHolder.imgview = (ImageView)convertView.findViewById(R.id.tag_img);
            viewHolder.ItemText = (TextView)convertView.findViewById(R.id.tag_text);
            viewHolder.ItemUrl = (TextView)convertView.findViewById(R.id.tag_url);
            viewHolder.ItemTag = (TextView)convertView.findViewById(R.id.tag_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ItemText.setText(mData.get(position).get(KEY_CONTENT).toString().toUpperCase());
        viewHolder.ItemTitle.setText(mData.get(position).get(KEY_TAG).toString().toUpperCase());
        viewHolder.ItemUrl.setText(mData.get(position).get(KEY_URL).toString());
        viewHolder.ItemTag.setText(ViewActivity.name);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();

        MyApplication.imageLoader.displayImage(String.valueOf(mData.get(position).get(KEY_IMG)),
                viewHolder.imgview,options);

        return convertView;
    }

    static class ViewHolder {
        TextView ItemTitle;
        TextView ItemText;
        TextView ItemUrl;
        ImageView imgview;
        TextView ItemTag;
    }
}
