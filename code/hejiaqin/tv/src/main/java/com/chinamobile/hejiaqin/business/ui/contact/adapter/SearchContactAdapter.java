package com.chinamobile.hejiaqin.business.ui.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactInfoFragment;
import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
            holder.text = (TextView) convertView.findViewById(R.id.contact_name_text);
            holder.image = (CircleImageView) convertView.findViewById(R.id.contact_photo_img);
            holder.layoutView = convertView.findViewById(R.id.contact_info_layout);
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
        holder.text.setText(contactsInfo.getName());


        Picasso.with(mContext.getApplicationContext())
                .load(contactsInfo.getPhotoSm())
                .placeholder(R.drawable.contact_photo_default)
                .error(R.drawable.contact_photo_default).into(holder.image);

        holder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ContactInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);

                ContactInfoFragment fragment = ContactInfoFragment.newInstance(contactsInfo);
                FragmentMgr.getInstance().showContactFragment(fragment);
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

    class ViewHolder {
        TextView text;
        CircleImageView image;
        View layoutView;
        View convertView;
    }
}
