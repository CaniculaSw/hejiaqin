package com.chinamobile.hejiaqin.business.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.ui.contact.ContactInfoActivity;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2016/7/20.
 */
public class DialContactAdapter extends RecyclerView.Adapter  {
    private Context mContext;
    private List<ContactsInfo> mData ;

    public DialContactAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<ContactsInfo>();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dial_contact, parent, false);
        holer = new HolderView(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactsInfo info = mData.get(position);
        if (info != null) {
            HolderView tHolder = (HolderView) holder;
            tHolder.itemView.setTag(position);
            tHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Intent intent = new Intent(mContext, ContactInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, mData.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            tHolder.contactNameTv.setText(Html.fromHtml(info.getSearchUnit().getNameText()));
            tHolder.contactNumberTv.setText(Html.fromHtml(info.getSearchUnit().getNumberText()));
        }
    }

    public void refreshData(List<ContactsInfo> data) {
        if (data != null)  {
            mData = data;
        }else{
            mData.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        private TextView contactNameTv;
        private TextView contactNumberTv;

        public HolderView(View view) {
            super(view);
            contactNameTv = (TextView) view.findViewById(R.id.contact_name_tv);
            contactNumberTv = (TextView) view.findViewById(R.id.contact_number_tv);
        }
    }
}
