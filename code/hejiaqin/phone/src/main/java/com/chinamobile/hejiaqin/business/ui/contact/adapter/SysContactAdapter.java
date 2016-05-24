package com.chinamobile.hejiaqin.business.ui.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yupeng on 5/23/16.
 */
public class SysContactAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    private LayoutInflater inflater;

    public SysContactAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();

    private String[] headerArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

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
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_contact_app_view, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.contact_name_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ContactsInfo contactsInfo = contactsInfoList.get(position);
        holder.text.setText(contactsInfo.getName());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.adapter_contact_head_view, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.contact_head_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = position >= headerArray.length ? headerArray[0] : headerArray[position];
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return position >= headerArray.length ? headerArray[0].charAt(0) : headerArray[position].charAt(0) ;
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        this.contactsInfoList.clear();
        if (null != contactsInfoList) {
            this.contactsInfoList.addAll(contactsInfoList);
        }
        notifyDataSetChanged();
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

}