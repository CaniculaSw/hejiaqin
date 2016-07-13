package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.more.MissCallMessage;
import com.customer.framework.component.time.DateTimeUtil;
import com.customer.framework.ui.AdapterViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MissCallAdapter extends BaseAdapter implements ListAdapter {

    private LayoutInflater inflater;
    private List<MissCallMessage> missCallmessages = new ArrayList<MissCallMessage>();
    private boolean isShow = false;
    private Context mContext;
    private AdapterViewHolder mViewHolder;
    public MissCallAdapter(Context context) {
        super();
        mContext = context;
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
        mViewHolder =  AdapterViewHolder.get(mContext, convertView, parent, R.layout.adapter_miss_call, position);
        CheckBox checkBox = (CheckBox) mViewHolder.getView(R.id.more_checkbox_miss_call);

        MissCallMessage msg = (MissCallMessage) getItem(position);
        mViewHolder.setText(R.id.more_miss_call_item_text,"(小王) 13776570335 fsadfcsdf");
        mViewHolder.setText(R.id.more_miss_call_date,formatTimeString(msg.getDate()));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                missCallmessages.get(position).setChecked(isChecked);
            }
        });

        if (isShow) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setChecked(msg.isChecked());
        return mViewHolder.getView();
    }


    public void setData(List<MissCallMessage> missCallInfoList) {
        this.missCallmessages.clear();
        if (null != missCallInfoList) {
            this.missCallmessages.addAll(missCallInfoList)  ;
        }
        notifyDataSetChanged();
    }


    public void deleteSelectedData() {
        Iterator<MissCallMessage> it = this.missCallmessages.iterator();
        while (it.hasNext()) {
            MissCallMessage missCallMsg = it.next();
            if (missCallMsg.isChecked()) {
                it.remove();
            }
        }
        notifyDataSetChanged();
    }

    public void unSelectedAllData() {
        for (MissCallMessage missCallMsg : this.missCallmessages) {
            missCallMsg.setChecked(false);
        }
        notifyDataSetChanged();
    }

    private String formatTimeString(String time) {
        Date date = DateTimeUtil.parseDateString(time,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if (! DateTimeUtil.isThisYear(date)){
            return DateTimeUtil.getYYYYMMDDString(date);
        }else if (DateTimeUtil.isYesterday(date)){
            return "昨天";
        }else if (DateTimeUtil.isToday(date)){
            return DateTimeUtil.getHHMMByDate(date);
        }else {
            return DateTimeUtil.getMMddByDate(date);
        }
    }
}
