package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchUnit;
import com.customer.framework.ui.AdapterViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/11/14.
 */
public class MoreSearchContactAdapter extends BaseAdapter {

    private Context mContext;
    private ISettingLogic settingLogic;
    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();

    public MoreSearchContactAdapter(Context context) {
        mContext = context;
        settingLogic = (ISettingLogic) LogicBuilder.getInstance(mContext).getLogicByInterfaceClass(
                ISettingLogic.class);
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
        AdapterViewHolder holder = AdapterViewHolder.get(mContext, convertView, parent,
                R.layout.adapter_contact_search_view, position);
        initView(position, holder);
        return holder.getView();
    }

    private void initView(int position, AdapterViewHolder holder) {
        final ContactsInfo contactsInfo = contactsInfoList.get(position);

        SearchUnit searchUnit = contactsInfo.getSearchUnit();

        holder.setText(R.id.contact_name_text, Html.fromHtml(searchUnit.getNameText()).toString());
        holder.setText(R.id.contact_number_text, Html.fromHtml(searchUnit.getNumberText())
                .toString());

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingLogic.sendBindReq(contactsInfo.getPhone(),
                        UserInfoCacheManager.getUserInfo(mContext).getPhone());
                Toast.makeText(mContext, "正在等待对方接受你的请求", Toast.LENGTH_LONG).show();
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
}
