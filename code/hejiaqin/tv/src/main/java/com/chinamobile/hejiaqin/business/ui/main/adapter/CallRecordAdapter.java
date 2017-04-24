package com.chinamobile.hejiaqin.business.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2016/7/20.
 */
public class CallRecordAdapter extends BaseAdapter {
    private Context mContext;
    private IContactsLogic mContactsLogic;
    private List<CallRecord> mData;
    private OnClickListen mListen;

    public CallRecordAdapter(Context context, IContactsLogic contactsLogic, OnClickListen listen) {
        this.mContext = context;
        this.mContactsLogic = contactsLogic;
        mData = new ArrayList<CallRecord>();
        this.mListen = listen;
    }

    /***/
    public void refreshData(List<CallRecord> data) {
        if (data != null) {
            mData = data;
        } else {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public CallRecord getData(int position) {
        if (mData == null) {
            return null;
        }
        return mData.get(position);
    }

    /***/
    public void delData(String[] ids) {
        if (mData == null) {
            return;
        }
        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; i < mData.size(); j++) {
                if (ids[i].equals(mData.get(j).getId())) {
                    mData.remove(j);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mData == null) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CallRecord info = mData.get(position);
        if (info != null) {
            HolderView tHolder = null;
            if (convertView == null) {

                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_call_record,
                        parent, false);
                tHolder = new HolderView(convertView);
                convertView.setTag(tHolder);

            } else {
                tHolder = (HolderView) convertView.getTag();
            }

            tHolder.itemView.setTag(position);
            tHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    mListen.onClick(mData.get(position), position);
                }
            });
            tHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    int position = (int) v.getTag();
                    mListen.onLongClick(position);
                    return true;
                }
            });

            if (info.getType() == CallRecord.TYPE_VIDEO_INCOMING) {
                tHolder.callRecordTypeIv.setImageResource(R.drawable.icon_incoming);
            } else if (info.getType() == CallRecord.TYPE_VIDEO_MISSING) {
                tHolder.callRecordTypeIv.setImageResource(R.drawable.icon_missed_call);
            } else if (info.getType() == CallRecord.TYPE_VIDEO_REJECT) {
                tHolder.callRecordTypeIv.setImageResource(R.drawable.icon_reject_call);
            } else {
                tHolder.callRecordTypeIv.setImageResource(R.drawable.icon_outbound_call);
            }
            if (info.getContactsInfo() == null) {
                //遍历本地联系人
                boolean isMatch = false;
                List<ContactsInfo> localcontactsInfos = mContactsLogic.getCacheAppContactLst();
                for (ContactsInfo contactsInfo : localcontactsInfos) {
                    if (isMatch) {
                        break;
                    }
                    if (contactsInfo.getNumberLst() != null) {
                        for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
                            if (info.getNoCountryNumber().equals(
                                    numberInfo.getNumberNoCountryCode())
                                    || (info.getNoCountryNumber()).equals("92"
                                            + numberInfo.getNumberNoCountryCode())) {
                                info.setPeerName(contactsInfo.getName());
                                info.setContactsInfo(contactsInfo);
                                isMatch = true;
                            }
                        }
                    }
                }
            }
            tHolder.callRecordNameTv.setText(StringUtil.isNullOrEmpty(info.getPeerName()) ? info
                    .getPeerNumber() : info.getPeerName());
            tHolder.callRecordNumberTv.setText(StringUtil.isNullOrEmpty(info.getPeerName()) ? ""
                    : info.getPeerNumber());
            tHolder.callRecordTimeTv.setText(info.getBeginTimeformatter());
        }
        return convertView;
    }

    /***/
    static public class HolderView {

        private ImageView callRecordTypeIv;
        private TextView callRecordNameTv;
        private TextView callRecordNumberTv;
        private TextView callRecordTimeTv;
        private View itemView;

        public HolderView(View view) {
            callRecordTypeIv = (ImageView) view.findViewById(R.id.call_record_type_iv);
            callRecordNameTv = (TextView) view.findViewById(R.id.call_record_name_tv);
            callRecordNumberTv = (TextView) view.findViewById(R.id.call_record_number_tv);
            callRecordTimeTv = (TextView) view.findViewById(R.id.call_record_time_tv);
            itemView = view.findViewById(R.id.call_record_item);

        }
    }

    /***/
    public static abstract class OnClickListen {
        /***/
        public abstract void onClick(CallRecord info, int position);

        /***/
        public abstract void onLongClick(int position);
    }

}
