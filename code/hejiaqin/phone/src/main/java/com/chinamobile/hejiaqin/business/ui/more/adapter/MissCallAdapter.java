package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

import com.chinamobile.hejiaqin.R;
import com.customer.framework.component.time.DateTimeUtil;
import com.customer.framework.ui.AdapterViewHolder;
import com.huawei.rcs.call.CallLogApi;
import com.huawei.rcs.call.ContactCallLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MissCallAdapter extends BaseAdapter implements ListAdapter {

    //    private LayoutInflater inflater;
    private List<ContactCallLog> missCallmessages = new ArrayList<ContactCallLog>();
    private boolean isShow = false;
    private Context mContext;
    private AdapterViewHolder mViewHolder;
    private Set<ContactCallLog> selected;

    public MissCallAdapter(Context context) {
        super();
        mContext = context;
        //        inflater = LayoutInflater.from(context);
        selected = new HashSet<ContactCallLog>();
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
        mViewHolder = AdapterViewHolder.get(mContext, convertView, parent,
                R.layout.adapter_miss_call, position);
        CheckBox checkBox = (CheckBox) mViewHolder.getView(R.id.more_checkbox_miss_call);

        ContactCallLog msg = (ContactCallLog) getItem(position);

        mViewHolder.setText(
                R.id.more_miss_call_item_text,
                Html.fromHtml("<font color='#44C8FF'> (" + msg.getPeerInfo().getName() + ") "
                        + msg.getPeerInfo().getNumber()
                        + "</font>"));
        //Html.fromHtml("<font color='#44C8FF'>("+msg.getPeerInfo().getName()+") "+msg.getPeerInfo().getNumber() +"</font>"));
        mViewHolder.setText(R.id.more_miss_call_date,
                formatTimeString(DateTimeUtil.getDateString(new Date(msg.getLastCallTimeLong()))));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected.add(missCallmessages.get(position));
                } else {
                    selected.remove(missCallmessages.get(position));
                }
            }
        });

        if (isShow) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setChecked(selected.contains(missCallmessages.get(position)));
        return mViewHolder.getView();
    }

    public void setData(List<ContactCallLog> missCallInfoList) {
        this.missCallmessages.clear();
        if (null != missCallInfoList) {
            this.missCallmessages.addAll(missCallInfoList);
        }
        notifyDataSetChanged();
    }

    /***/
    public void deleteSelectedData() {
        Iterator<ContactCallLog> iterator = selected.iterator();
        while (iterator.hasNext()) {
            ContactCallLog delete = iterator.next();
            CallLogApi.removeCalllogsByNumberAndType(delete.getPeerInfo().getNumber(),
                    CallLogApi.QUERY_FILTER_TYPE_MISSED);
            iterator.remove();
            this.missCallmessages.remove(delete);
        }
        notifyDataSetChanged();
    }

    /***/
    public void unSelectedAllData() {
        selected.clear();
        notifyDataSetChanged();
    }

    private String formatTimeString(String time) {
        Date date = DateTimeUtil.parseDateString(time, new SimpleDateFormat("yyyyMMddHHmmss"));
        if (!DateTimeUtil.isThisYear(date)) {
            return DateTimeUtil.getYYYYMMDDString(date);
        } else if (DateTimeUtil.isYesterday(date)) {
            return "昨天";
        } else if (DateTimeUtil.isToday(date)) {
            return DateTimeUtil.getHHMMByDate(date);
        } else {
            return DateTimeUtil.getMMddByDate(date);
        }
    }
}
