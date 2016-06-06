package com.chinamobile.hejiaqin.tv.business.ui.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.model.more.MissCallMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MissCallAdapter extends BaseAdapter implements ListAdapter {

    private LayoutInflater inflater;
    private List<MissCallMessage> missCallmessages = new ArrayList<MissCallMessage>();
    private boolean isShow = false;

    public MissCallAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public int getCount() {
        return missCallmessages == null ? 0 : missCallmessages.size();
    }

    @Override
    public Object getItem(int position) {
        return missCallmessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_miss_call, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.more_miss_call_item_text);
            holder.date = (TextView) convertView.findViewById(R.id.more_miss_call_date);
            holder.delCb = (CheckBox) convertView.findViewById(R.id.more_checkbox_miss_call);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MissCallMessage msg = (MissCallMessage) getItem(position);
        holder.text.setText("(小王) 13776570335 fsadfcsdf");
        holder.date.setText(msg.getDate());

        holder.delCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                missCallmessages.get(position).setChecked(isChecked);
            }
        });

        if (isShow) {
            holder.delCb.setVisibility(View.VISIBLE);
        } else {
            holder.delCb.setVisibility(View.GONE);
        }

        holder.delCb.setChecked(msg.isChecked());
        return convertView;
    }

    class ViewHolder {
        TextView text;
        TextView date;
        CheckBox delCb;
    }

    public void setData(List<MissCallMessage> missCallInfoList) {
//        this.missCallmessages.clear();
        if (null != missCallInfoList) {
            this.missCallmessages = missCallInfoList ;
        }
        notifyDataSetChanged();
    }
}
