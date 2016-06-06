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
import com.chinamobile.hejiaqin.business.model.more.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/5/28.
 */
public class SysMessageAdapter extends BaseAdapter implements ListAdapter {

    private LayoutInflater inflater;
    private List<SystemMessage> sysMessageList = new ArrayList<SystemMessage>();
    private boolean isShow = false;
    private Context context;
    private SystemMessage msg;

    public SysMessageAdapter(Context context) {
        super();
        this.context = context;
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
        return sysMessageList == null ? 0 : sysMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return sysMessageList.get(position);
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
            convertView = inflater.inflate(R.layout.adapter_sys_message, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.more_sys_msg_title_tv);
            holder.date = (TextView) convertView.findViewById(R.id.more_sys_msg_date_tv);
            holder.delCb = (CheckBox) convertView.findViewById(R.id.more_checkbox_sys_message_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        msg = (SystemMessage) getItem(position);
        holder.title.setText(msg.getTitle());
        holder.date.setText(msg.getDate());

        holder.delCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sysMessageList.get(position).setChecked(isChecked);
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

    public void setData(List<SystemMessage> sysMessageList) {
        if (null != sysMessageList) {
            this.sysMessageList = sysMessageList;
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView title;
        TextView date;
        CheckBox delCb;
    }
}
