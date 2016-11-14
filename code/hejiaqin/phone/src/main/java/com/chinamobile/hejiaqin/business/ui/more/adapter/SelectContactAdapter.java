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
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersAdapter;
import com.customer.framework.ui.AdapterViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by eshaohu on 16/6/7.
 */
public class SelectContactAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private final static String TAG = "SelectContactAdapter";
    private Context mContext;
    private LayoutInflater inflater;
    private Handler handler;
    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
    private AdapterViewHolder mListViewHolder;
    private AdapterViewHolder mHeaderViewHolder;

    private Set<ContactsInfo> selectedSet = new HashSet<ContactsInfo>();

    public SelectContactAdapter(Context context, Handler handler) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return contactsInfoList == null ? 0 : contactsInfoList.size();
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
        mListViewHolder = AdapterViewHolder.get(mContext, convertView, parent, R.layout.adapter_select_contact, position);

        initView(position, mListViewHolder);

        return mListViewHolder.getView();
    }

    private void initView(int position, AdapterViewHolder holder) {
        final ContactsInfo contactsInfo = contactsInfoList.get(position);
        holder.setText(R.id.contact_name_text, contactsInfo.getName());
        CheckBox checkBox = (CheckBox) holder.getView(R.id.more_select_contact_cb);

        if (contactsInfo != null) {
            if (selectedSet.contains(contactsInfo)) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckBox) v).setChecked(((CheckBox) v).isChecked());
                if (((CheckBox) v).isChecked()) {
                    selectedSet.add(contactsInfo);

                } else {
                    selectedSet.remove(contactsInfo);
                }
                notifyCheckedNum();
            }
        });
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        mHeaderViewHolder = AdapterViewHolder.get(mContext, convertView, parent, R.layout.adapter_contact_head_view, position);
        //set header text as first char in name
        ContactsInfo contactsInfo = contactsInfoList.get(position);
        String headerText = contactsInfo.getGroupName();
        mHeaderViewHolder.setText(R.id.contact_head_text, headerText);
        return mHeaderViewHolder.getView();
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        ContactsInfo contactsInfo = contactsInfoList.get(position);
        return contactsInfo.getGroupName().charAt(0);
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        this.contactsInfoList.clear();
        this.selectedSet.clear();
        if (null != contactsInfoList) {
            this.contactsInfoList.addAll(contactsInfoList);
        }
        notifyDataSetChanged();
    }

    public Set<ContactsInfo> getSelectedSet() {
        return selectedSet;
    }

    public void selectAll(boolean isSelectAll) {
        if (isSelectAll) {
            if (!selectedSet.containsAll(this.contactsInfoList)) {
                selectedSet.addAll(this.contactsInfoList);
            }
        } else {
            selectedSet.clear();
        }

        notifyDataSetChanged();
        notifyCheckedNum();
    }

    public Set<ContactsInfo> getSelectedContactSet(){
        return this.selectedSet;
    }

    private void notifyCheckedNum() {
        Message msg = new Message();
        msg.what = BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED;
        msg.arg1 = selectedSet.size();
        msg.arg2 = this.contactsInfoList.size();
        handler.sendMessage(msg);
    }

}
