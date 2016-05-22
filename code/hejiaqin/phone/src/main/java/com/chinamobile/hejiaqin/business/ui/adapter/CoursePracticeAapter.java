package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.courses.PracticeInfo;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class CoursePracticeAapter extends RecyclerView.Adapter {
    private Context mContext;
    private ClickListener mClickListener;
    private List<PracticeInfo> mData;

    public CoursePracticeAapter(Context context) {
        this.mContext = context;
        mClickListener = new ClickListener();
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_exercise_course_list, parent, false);
        holer = new TextViewHolder(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData.get(position) != null) {
            TextViewHolder textholer = (TextViewHolder) holder;
            PracticeInfo practiceInfo = mData.get(position);
            textholer.tv_title2.setText(StringUtil.isNullOrEmpty(practiceInfo.getTitle()) ? "" : practiceInfo.getTitle());
            if (practiceInfo.getRange() != null && practiceInfo.getRange().get(position) != null) {
                textholer.tv_position.setText(StringUtil.isNullOrEmpty(practiceInfo.getRange().get(position).getCaption()) ? mContext.getResources().getString(R.string.course_exercise_body_position) :
                        mContext.getResources().getString(R.string.course_exercise_body_position) + practiceInfo.getRange().get(position).getCaption());
            }
            if (practiceInfo.getApparatus() != null && practiceInfo.getApparatus().get(position) != null) {
                textholer.tv_equipment.setText(StringUtil.isNullOrEmpty(practiceInfo.getApparatus().get(position).getCaption()) ? mContext.getResources().
                        getString(R.string.course_exercise_equipment) + mContext.getResources().getString(R.string.course_exercise_equipment_non) : mContext.getResources().
                        getString(R.string.course_exercise_equipment) + practiceInfo.getApparatus().get(position).getCaption());
            }
            textholer.tv_num.setText(practiceInfo.getParticipants() + "人已参加");
            boolean jioned = practiceInfo.getJoined() == 1;
            if (!jioned) {
                textholer.imag_tag.setVisibility(View.INVISIBLE);
            }
            String img_url = StringUtil.isNullOrEmpty(practiceInfo.getCoverImage()) ? "-" : practiceInfo.getCoverImage();
            Picasso.with(mContext).load(img_url).placeholder(R.mipmap.ic_default1).into(textholer.imag_pic);
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
        private TextView tv_title2;
        private TextView tv_position;
        private TextView tv_equipment;
        private ImageView imag_tag;
        private TextView tv_num;
        private ImageView imag_pic;

        public TextViewHolder(View view) {
            super(view);
            layout_div = (LinearLayout) view.findViewById(R.id.layout_div);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title2 = (TextView) view.findViewById(R.id.textView2);
            tv_position = (TextView) view.findViewById(R.id.textView3);
            tv_equipment = (TextView) view.findViewById(R.id.textView4);
            imag_tag = (ImageView) view.findViewById(R.id.textView5);
            tv_num = (TextView) view.findViewById(R.id.textView6);
            imag_pic = (ImageView) view.findViewById(R.id.imageView2);
        }
    }


    public void loadMoreData(List<PracticeInfo> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void refreshData(List<PracticeInfo> data) {
        if (data == null) {
            mData = new ArrayList<PracticeInfo>();
        } else {
            mData = data;
        }
        notifyDataSetChanged();
    }

    public void deleteData(String id) {
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (StringUtil.equals(mData.get(i).getId() + "", id)) {
                    mData.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public String getLastId() {
        String id = "";
        if (mData != null && mData.size() > 0) {
            id = mData.get(mData.size() - 1).getId() + "";
        }
        return id;
    }


    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int position = (int) v.getTag();
        }
    }

}


