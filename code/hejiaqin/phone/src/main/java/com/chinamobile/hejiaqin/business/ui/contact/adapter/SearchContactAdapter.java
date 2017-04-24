package com.chinamobile.hejiaqin.business.ui.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchUnit;
import com.chinamobile.hejiaqin.business.ui.contact.ContactInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/28 0028.
 */
public class SearchContactAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();

    public SearchContactAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_contact_search_view, parent, false);
            holder.nameText = (TextView) convertView.findViewById(R.id.contact_name_text);
            holder.numberText = (TextView) convertView.findViewById(R.id.contact_number_text);
            holder.convertView = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        initView(position, holder);
        return convertView;
    }

    private void initView(int position, ViewHolder holder) {
        final ContactsInfo contactsInfo = contactsInfoList.get(position);

        SearchUnit searchUnit = contactsInfo.getSearchUnit();

        holder.nameText.setText(Html.fromHtml(searchUnit.getNameText()));
        holder.numberText.setText(Html.fromHtml(searchUnit.getNumberText()));

        holder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContactInfoActivity.class);
                intent.putExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        this.contactsInfoList.clear();
        if (null != contactsInfoList) {
            this.contactsInfoList.addAll(contactsInfoList);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView nameText;
        TextView numberText;
        View convertView;
    }
}
