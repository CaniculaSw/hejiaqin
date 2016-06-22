package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.customer.framework.ui.AdapterViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eshaohu on 16/6/8.
 */
public class SelectableSearchContactAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private AdapterViewHolder holder;
    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
    private CheckBox mCheckBox;
    private Set<ContactsInfo> selectedSet = new HashSet<ContactsInfo>();
    private Handler handler;

    public SelectableSearchContactAdapter(Context context, Handler handler) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return contactsInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = AdapterViewHolder.get(mContext, convertView, parent, R.layout.adapter_select_contact, position);
        initView(position, holder);
        return holder.getView();
    }

    private void initView(int position, AdapterViewHolder holder) {
        final ContactsInfo contactsInfo = contactsInfoList.get(position);
        holder.setText(R.id.contact_name_text, contactsInfo.getName());
        mCheckBox = (CheckBox) holder.getView(R.id.more_select_contact_cb);

        if (contactsInfo != null) {
            if (selectedSet.contains(contactsInfo)) {
                mCheckBox.setChecked(true);
            } else {
                mCheckBox.setChecked(false);
            }
        }

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    selectedSet.add(contactsInfo);
                } else {
                    selectedSet.remove(contactsInfo);
                }
                notifyCheckedNum();
            }
        });
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        this.contactsInfoList.clear();
        this.selectedSet.clear();
        if (null != contactsInfoList) {
            this.contactsInfoList.addAll(contactsInfoList);
        }

        notifyDataSetChanged();
        notifyCheckedNum();
    }

    private void notifyCheckedNum() {
        Message msg = new Message();
        msg.what = BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED;
        msg.arg1 = selectedSet.size();
        msg.arg2 = this.contactsInfoList.size();
        handler.sendMessage(msg);
    }

}
