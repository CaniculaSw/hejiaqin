package com.chinamobile.hejiaqin.business.ui.adapter;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.event.MessageEvent;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.CustomDialog;
import com.chinamobile.hejiaqin.business.ui.myMesage.MyMessageDetialActivity;
import com.chinamobile.hejiaqin.business.utils.BusProvider;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wubengui on 2016/4/26.
 */
public class MessageAapter extends RecyclerView.Adapter {
    private Context mContext;
    private ClickListener mClickListener;
    private LongClickListener mLongClickListener;
    private List<AppMessageInfo> mData;

    public MessageAapter(Context context) {
        this.mContext = context;
        mClickListener = new ClickListener();
        mLongClickListener = new LongClickListener();
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_message, parent, false);
        holer = new TextViewHolder(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData.get(position) != null) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.layout_div.setOnClickListener(mClickListener);
            textViewHolder.layout_div.setOnLongClickListener(mLongClickListener);
            textViewHolder.layout_div.setTag(position);
            textViewHolder.tv_title.setText((StringUtil.isNullOrEmpty(mData.get(position).getTitle()) ? "" : mData.get(position).getTitle()));
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_div;
        private TextView tv_title;

        public TextViewHolder(View view) {
            super(view);
            layout_div = (LinearLayout) view.findViewById(R.id.layout_div);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }


    public void loadMoreData(List<AppMessageInfo> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void refreshData(List<AppMessageInfo> data) {
        if (data == null) {
            mData = new ArrayList<AppMessageInfo>();
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }

    public void deleteData(String id){
        if(mData != null){
            for (int i = 0;i<mData.size();i++) {
                if(StringUtil.equals(mData.get(i).getId() + "", id)){
                    mData.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public String getLastId(){
        String id = "";
        if(mData != null && mData.size() >0 ){
          id = mData.get(mData.size()-1).getId() + "";
        }
        return id;
    }


    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();
            String id = mData.get(position).getId()+"";
            Intent intent = new Intent(mContext, MyMessageDetialActivity.class);
            intent.putExtra("id",id);
            mContext.startActivity(intent);

        }
    }


    class LongClickListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            final int position = (int)v.getTag();
            CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setCancelable(true);
            builder.setMessage("选择复制或删除，点击确认");
            builder.setPositiveButton("复制", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //文本复制功能
                    ClipboardManager cmb = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(mData.get(position).getTitle().trim());
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MessageEvent event = new MessageEvent();
                    event.setType(2);
                    event.setMessageInfo(mData.get(position));
                    BusProvider.getUIBusInstance().post(event);
                    dialog.dismiss();
                }
            });

            Dialog dialog = builder.create();
            dialog.show();
            return false;
        }
    }


}


