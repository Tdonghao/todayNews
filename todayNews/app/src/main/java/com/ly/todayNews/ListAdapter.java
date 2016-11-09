package com.ly.todayNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ListAdapter extends ArrayAdapter<Map<String,Object>> {

    public static final String KEY_NAME = "name";
    public static final String KEY_VAL = "val";
    private final LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public ListAdapter(Context context, int layoutResourceId, List<Map<String,Object>> data) {
        super(context,layoutResourceId,data);
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mListItemName = (TextView) convertView.findViewById(R.id.text_view_name);
            viewHolder.mListItemVal = (TextView) convertView.findViewById(R.id.text_view_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mListItemName.setText(mData.get(position).get(KEY_NAME).toString().toUpperCase());
        viewHolder.mListItemVal.setText(mData.get(position).get(KEY_VAL).toString().toUpperCase());

        return convertView;
    }

    static class ViewHolder {
        TextView mListItemName;
        TextView mListItemVal;
    }
}
