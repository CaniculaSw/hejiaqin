package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.setting.UserQuestionInfo;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiadong on 2016/5/11.
 */
public class QuestionAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<UserQuestionInfo> mDatas;
    private ClickListener mClickListeter;
    private List<Boolean> mStates;                          //保存收缩状态

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行

    public QuestionAdapter(Context context) {
        this.mContext = context;
        this.mClickListeter = new ClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_question, parent, false);
        RecyclerView.ViewHolder holder = new TextViewHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDatas.get(position) != null) {
            TextViewHoler textViewHolder = (TextViewHoler) holder;
//            textViewHolder.titleTextView.setText(StringUtil.isNullOrEmpty(mDatas.get(position).getReply()) ? "" : mDatas.get(position).getReply());
            textViewHolder.contentTextView.setText(StringUtil.isNullOrEmpty(mDatas.get(position).getAskContent()) ? "" : mDatas.get(position).getAskContent());
            textViewHolder.question_time.setText(StringUtil.isNullOrEmpty(mDatas.get(position).getCreatetime()) ? "" : mDatas.get(position).getCreatetime());
            textViewHolder.rowLayout.setOnClickListener(mClickListeter);
            textViewHolder.rowLayout.setTag(position);

            if (!mStates.get(position)) {
                textViewHolder.contentTextView.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                textViewHolder.contentTextView.requestLayout();
//                mImageShrinkUp.setVisibility(View.GONE);
//                mImageSpread.setVisibility(View.VISIBLE);
            } else{
                textViewHolder.contentTextView.setMaxLines(Integer.MAX_VALUE);
                textViewHolder.contentTextView.requestLayout();
//                mImageShrinkUp.setVisibility(View.VISIBLE);
//                mImageSpread.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class TextViewHoler extends RecyclerView.ViewHolder {
        LinearLayout rowLayout;
//        TextView titleTextView;
        TextView contentTextView;
        TextView question_time;

        public TextViewHoler(View view) {
            super(view);
            rowLayout = (LinearLayout) view.findViewById(R.id.question_row_layout);
//            titleTextView = (TextView) view.findViewById(R.id.question_title_tv);
            contentTextView = (TextView) view.findViewById(R.id.question_content_tv);
            question_time = (TextView) view.findViewById(R.id.question_time);
        }
    }


    public void loadMoreData(List<UserQuestionInfo> data) {
        if (data != null && data.size() > 0) {
            if (mStates == null) {
                mStates = new ArrayList<>();
            }
            for (int i = 0; i < data.size(); i++) {
                mStates.add(false);
            }

            mDatas.addAll(data);
            notifyDataSetChanged();
        }

    }

    public void refreshData(List<UserQuestionInfo> data) {
        mStates = new ArrayList<>();
        if (data == null) {
            mDatas = new ArrayList<UserQuestionInfo>();
        } else {
            mDatas = data;
        }

        for (int i = 0; i < data.size(); i++) {
            mStates.add(false);
        }
        notifyDataSetChanged();
    }


    public String getLastId(){
        String id = "";
        if(mDatas != null && mDatas.size() >0 ){
            id = mDatas.get(mDatas.size()-1).getId() + "";
        }
        return id;
    }

    class ClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            if(mStates != null) {
                int position = (int) v.getTag();
                mStates.set(position, !mStates.get(position));
                notifyItemChanged(position);
            }
        }
    }
}
