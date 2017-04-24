package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersAdapter;
import com.customer.framework.ui.AdapterViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eshaohu on 16/11/14.
 */
public class SelectContactAndBindAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;

    //    private LayoutInflater inflater;

    private List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();

    private SparseArray<String> positionToLetter = new SparseArray<>();

    private Map<String, Integer> letterToPosition = new HashMap<>();
    private ISettingLogic settingLogic;

    //    private Handler handler;

    public SelectContactAndBindAdapter(Context context, Handler handler) {
        mContext = context;
        //        inflater = LayoutInflater.from(context);
        settingLogic = (ISettingLogic) LogicBuilder.getInstance(context).getLogicByInterfaceClass(
                ISettingLogic.class);
        //        this.handler = handler;
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
                R.layout.adapter_contact_app_view, position);

        initView(position, holder);
        return holder.getView();
    }

    private void initView(int position, AdapterViewHolder holder) {
        final ContactsInfo contactsInfo = contactsInfoList.get(position);
        holder.setText(R.id.contact_name_text, contactsInfo.getName());

        Picasso.with(mContext.getApplicationContext()).load(contactsInfo.getPhotoSm())
                .placeholder(R.drawable.contact_photo_default)
                .error(R.drawable.contact_photo_default)
                .into((CircleImageView) holder.getView(R.id.contact_photo_img));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingLogic.sendBindReq(contactsInfo.getPhone(),
                        UserInfoCacheManager.getUserInfo(mContext).getPhone());
                //                handler.sendEmptyMessage(BussinessConstants.SettingMsgID.SENDING_BIND_REQUEST);
            }
        });
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        AdapterViewHolder holder = AdapterViewHolder.get(mContext, convertView, parent,
                R.layout.adapter_contact_head_view, position);
        //set header text as first char in name
        ContactsInfo contactsInfo = contactsInfoList.get(position);
        String headerText = contactsInfo.getGroupName();
        holder.setText(R.id.contact_head_text, headerText);
        return holder.getView();
    }

    @Override
    public long getHeaderId(int position) {
        String groupLetter = this.positionToLetter.get(position);
        return null == groupLetter ? -1 : groupLetter.charAt(0);
    }

    public int getPositionByLetter(String letter) {
        Integer position = this.letterToPosition.get(letter);
        return null == position ? -1 : position;
    }

    public String[] getGroupLetters() {
        return letterToPosition.keySet().toArray(new String[letterToPosition.size()]);
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        this.contactsInfoList.clear();
        this.positionToLetter.clear();
        this.letterToPosition.clear();
        if (null != contactsInfoList) {
            int index = 0;
            for (ContactsInfo contactsInfo : contactsInfoList) {
                String groupLetter = contactsInfo.getGroupName();
                this.positionToLetter.put(index, groupLetter);
                if (!letterToPosition.containsKey(groupLetter)) {
                    this.letterToPosition.put(groupLetter, index + 1);
                }
                this.contactsInfoList.add(contactsInfo);
                index++;
            }
        }

        notifyDataSetChanged();
    }
}
