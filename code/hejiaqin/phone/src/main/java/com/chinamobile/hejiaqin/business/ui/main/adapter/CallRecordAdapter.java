package com.chinamobile.hejiaqin.business.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.ui.contact.ContactInfoActivity;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2016/7/20.
 */
public class CallRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private IContactsLogic mContactsLogic;
    private List<CallRecord> mData;

    public CallRecordAdapter(Context context, IContactsLogic contactsLogic) {
        this.mContext = context;
        this.mContactsLogic = contactsLogic;
        mData = new ArrayList<CallRecord>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_call_record, parent, false);
        holer = new HolderView(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CallRecord info = mData.get(position);
        if (info != null) {
            HolderView tHolder = (HolderView) holder;
            tHolder.call_record_type_layout.setTag(position);
            tHolder.call_record_content_layout.setTag(position);
            tHolder.call_record_time_layout.setTag(position);
            tHolder.call_record_arrow_layout.setTag(position);
            tHolder.call_record_type_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callVideo(v);
                }
            });
            tHolder.call_record_content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callVideo(v);
                }
            });
            tHolder.call_record_time_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetail(v);
                }
            });
            tHolder.call_record_arrow_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetail(v);
                }
            });
            if (info.getType() == CallRecord.TYPE_VIDEO_INCOMING) {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_incoming);
            } else if (info.getType() == CallRecord.TYPE_VIDEO_MISSING) {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_missed_call);
            } else if (info.getType() == CallRecord.TYPE_VIDEO_REJECT) {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_reject_call);
            } else {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_outbound_call);
            }
            if (info.getContactsInfo() == null) {
                //遍历本地联系人
                boolean isMatch = false;
                List<ContactsInfo> localcontactsInfos = mContactsLogic.getCacheLocalContactLst();
                for (ContactsInfo contactsInfo : localcontactsInfos) {
                    if(isMatch)
                    {
                        break;
                    }
                    if (contactsInfo.getNumberLst() != null) {
                        for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
                            if (info.getNoCountryNumber().equals(numberInfo.getNumberNoCountryCode())) {
                                info.setPeerName(contactsInfo.getName());
                                info.setContactsInfo(contactsInfo);
                                isMatch =true;
                            }
                        }
                    }
                }
            }
            tHolder.callRecordNameTv.setText(StringUtil.isNullOrEmpty(info.getPeerName()) ? info.getPeerNumber() : info.getPeerName());
            tHolder.callRecordNumberTv.setText(StringUtil.isNullOrEmpty(info.getPeerName()) ? "" : info.getPeerNumber());
            tHolder.callRecordTimeTv.setText(info.getBeginTimeformatter());
        }
    }

    private void callVideo(View v) {
        int position = (int) v.getTag();
        if (mData.get(position).getContactsInfo() != null) {
            Intent outingIntent = new Intent(mContext, VideoCallActivity.class);
            outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, mData.get(position).getPeerNumber());
            outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NAME, mData.get(position).getContactsInfo().getName());
            mContext.startActivity(outingIntent);
        } else {
            Intent outingIntent = new Intent(mContext, VideoCallActivity.class);
            outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, mData.get(position).getPeerNumber());
            mContext.startActivity(outingIntent);
        }
    }

    private void showDetail(View v) {
        int position = (int) v.getTag();
        if (mData.get(position).getContactsInfo() != null) {
            Intent intent = new Intent(mContext, ContactInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, mData.get(position).getContactsInfo());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, ContactInfoActivity.class);
            intent.putExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY, mData.get(position).getPeerNumber());
            mContext.startActivity(intent);
        }
    }

    public void refreshData(List<CallRecord> data) {
        if (data != null) {
            mData = data;
        } else {
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

        private ImageView callRecordTypeIv;
        private TextView callRecordNameTv;
        private TextView callRecordNumberTv;
        private TextView callRecordTimeTv;
        private LinearLayout call_record_type_layout;
        private LinearLayout call_record_content_layout;
        private LinearLayout call_record_time_layout;
        private LinearLayout call_record_arrow_layout;

        public HolderView(View view) {
            super(view);
            callRecordTypeIv = (ImageView) view.findViewById(R.id.call_record_type_iv);
            callRecordNameTv = (TextView) view.findViewById(R.id.call_record_name_tv);
            callRecordNumberTv = (TextView) view.findViewById(R.id.call_record_number_tv);
            callRecordTimeTv = (TextView) view.findViewById(R.id.call_record_time_tv);
            call_record_type_layout = (LinearLayout)view.findViewById(R.id.call_record_type_layout);;
            call_record_content_layout= (LinearLayout)view.findViewById(R.id.call_record_content_layout);;
            call_record_time_layout= (LinearLayout)view.findViewById(R.id.call_record_time_layout);
            call_record_arrow_layout = (LinearLayout)view.findViewById(R.id.call_record_arrow_layout);
        }
    }
}
