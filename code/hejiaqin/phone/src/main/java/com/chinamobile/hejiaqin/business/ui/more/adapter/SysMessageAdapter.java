package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.dbapdater.SystemMessageDbAdapter;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.SystemMessage;
import com.customer.framework.component.time.DateTimeUtil;
import com.customer.framework.ui.AdapterViewHolder;
import com.customer.framework.utils.LogUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eshaohu on 16/5/28.
 */
public class SysMessageAdapter extends BaseAdapter implements ListAdapter {

//    private LayoutInflater inflater;
    private List<SystemMessage> sysMessageList = new ArrayList<SystemMessage>();
    private boolean isShow = false;
    private Context context;
    private static final String TAG = "SysMessageAdapter";

    private AdapterViewHolder mViewHolder;

    private Set<SystemMessage> selectedSet = new HashSet<SystemMessage>();

    public SysMessageAdapter(Context context) {
        super();
        this.context = context;
//        inflater = LayoutInflater.from(context);
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

        SystemMessage msg = (SystemMessage) getItem(position);


        mViewHolder =  AdapterViewHolder.get(context, convertView, parent, R.layout.adapter_sys_message, position);
        if (msg != null) {
            mViewHolder.setText(R.id.more_sys_msg_title_tv, msg.getTitle());
            try {
                mViewHolder.setText(R.id.more_sys_msg_date_tv, DateTimeUtil.getFormatStrByDate(DateTimeUtil.parseSTANDARDFormatToDate(msg.getTime()), "yyyy-MM-dd"));
            } catch (ParseException e) {
                LogUtil.e(TAG, e);
            }
        }
        CheckBox delCb = (CheckBox) mViewHolder.getView(R.id.more_checkbox_sys_message_cb);

        if (msg != null) {
            if (selectedSet.contains(msg)) {
                delCb.setChecked(true);
            } else {
                delCb.setChecked(false);
            }
        }

        delCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckBox) v).setChecked(((CheckBox) v).isChecked());
                if (((CheckBox) v).isChecked()) {
                    selectedSet.add((SystemMessage) getItem(position));

                } else {
                    selectedSet.remove((SystemMessage) getItem(position));
                }
            }
        });

        if (isShow) {
            delCb.setVisibility(View.VISIBLE);
        } else {
            delCb.setVisibility(View.GONE);
        }

        return mViewHolder.getView();
    }

    public void setData(List<SystemMessage> sysMessageList) {
        if (null != sysMessageList) {
            this.sysMessageList.addAll(sysMessageList);
        }
        notifyDataSetChanged();
    }
    /***/
    public void delSelectedSystemMessage(){
        LogUtil.d(TAG,"The lentg of the selected system messages: "+selectedSet.size());
        for (SystemMessage systemMessage : selectedSet){
            this.sysMessageList.remove(systemMessage);
            LogUtil.d(TAG,"Will delete system message with id: "+ systemMessage.getId());
            SystemMessageDbAdapter.getInstance(context, UserInfoCacheManager.getUserId(context))
                    .deleteSystemMessageByID(systemMessage.getId());
        }
        this.selectedSet.clear();
        notifyDataSetChanged();
    }
    /***/
    public void unSelectAll() {
        selectedSet.clear();
        notifyDataSetChanged();
    }
}
