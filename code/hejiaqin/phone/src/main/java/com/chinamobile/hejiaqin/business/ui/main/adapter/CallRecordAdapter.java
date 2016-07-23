package com.chinamobile.hejiaqin.business.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanggj on 2016/7/20.
 */
public class CallRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CallRecord> mData ;

    public CallRecordAdapter(Context context) {
        this.mContext = context;
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
            tHolder.itemView.setTag(position);
            tHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
//                    Intent intent = new Intent(mContext, ForumDetailActivity.class);
//                    intent.putExtra("id", mData.get(position).getId());
//                    mContext.startActivity(intent);
                }
            });
            if(info.getType() == CallRecord.TYPE_VIDEO_INCOMING)
            {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_incoming);
            }else if(info.getType() == CallRecord.TYPE_VIDEO_MISSING)
            {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_missed_call);
            }
            else if(info.getType() == CallRecord.TYPE_VIDEO_REJECT)
            {
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_reject_call);
            }else{
                tHolder.callRecordTypeIv.setImageResource(R.mipmap.icon_outbound_call);
            }
            tHolder.callRecordNameTv.setText(StringUtil.isNullOrEmpty(info.getPeerName())?"":info.getPeerName());
            tHolder.callRecordNumberTv.setText(info.getPeerNumber());
            tHolder.callRecordTimeTv.setText(info.getBeginTime());
        }
    }

    public void refreshData(List<CallRecord> data) {
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

        private ImageView callRecordTypeIv;
        private TextView callRecordNameTv;
        private TextView callRecordNumberTv;
        private TextView callRecordTimeTv;

        public HolderView(View view) {
            super(view);
            callRecordTypeIv = (ImageView) view.findViewById(R.id.call_record_type_iv);
            callRecordNameTv = (TextView) view.findViewById(R.id.call_record_name_tv);
            callRecordNumberTv = (TextView) view.findViewById(R.id.call_record_number_tv);
            callRecordTimeTv = (TextView) view.findViewById(R.id.call_record_time_tv);
        }
    }
}
