package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersAdapter;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MoreMissCallAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater inflater;

    public MoreMissCallAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_miss_call, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.more_miss_call_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText("fdsgfdsgdge");

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView text;
    }
}
