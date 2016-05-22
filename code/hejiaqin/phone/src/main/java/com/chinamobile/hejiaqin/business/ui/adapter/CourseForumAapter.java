package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.courses.LectureInfo;
import com.chinamobile.hejiaqin.business.ui.courses.ForumDetailActivity;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class CourseForumAapter extends RecyclerView.Adapter {
    private Context mContext;
    private ClickListener mClickListener;
    private List<LectureInfo> mData;

    public CourseForumAapter(Context context) {
        this.mContext = context;
        mClickListener = new ClickListener();
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_health_care_course_list, parent, false);
        holer = new HolderView(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LectureInfo lectureInfo = mData.get(position);
        if (lectureInfo != null) {
            HolderView tHolder = (HolderView) holder;
            tHolder.layout_div.setTag(position);
            tHolder.layout_div.setOnClickListener(mClickListener);

            tHolder.tv_course_name.setText(StringUtil.isNullOrEmpty(lectureInfo.getTitle()) ? "" : lectureInfo.getTitle());
            tHolder.tv_course_coach.setText(StringUtil.isNullOrEmpty(lectureInfo.getAuthorIntro()) ? "" : lectureInfo.getAuthorIntro());
            tHolder.tv_course_exercise_time.setText(mContext.getResources().getString(R.string.course_forum_time) + lectureInfo.getTotaltime() + mContext.getResources().getString(R.string.course_forum_min));
            String time = lectureInfo.getCreatetime().substring(11, lectureInfo.getCreatetime().length() - 3);
            tHolder.tv_course_publish_time.setText(mContext.getResources().getString(R.string.course_forum_publish_time) + time);
            tHolder.tv_course_broadcast_num.setText(mContext.getResources().getString(R.string.course_forum_has_brocast) + lectureInfo.getViewcount() + mContext.getResources().getString(R.string.course_forum_brocast_num));

            String img_url = StringUtil.isNullOrEmpty(lectureInfo.getCover()) ? "-" : lectureInfo.getCover();
            Picasso.with(mContext).load(img_url).placeholder(R.mipmap.ic_default2).into(tHolder.img_pic);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        public LinearLayout layout_div;
        private TextView tv_course_name;
        private TextView tv_course_coach;
        private TextView tv_course_exercise_time;
        private TextView tv_course_publish_time;
        private TextView tv_course_broadcast_num;
        private ImageView img_pic;

        public HolderView(View view) {
            super(view);
            layout_div = (LinearLayout) view.findViewById(R.id.layout_div);
            tv_course_name = (TextView) view.findViewById(R.id.textView2);
            tv_course_coach = (TextView) view.findViewById(R.id.textView3);
            tv_course_exercise_time = (TextView) view.findViewById(R.id.textView4);
            tv_course_publish_time = (TextView) view.findViewById(R.id.textView7);
            tv_course_broadcast_num = (TextView) view.findViewById(R.id.textView6);
            img_pic = (ImageView) view.findViewById(R.id.img_pic);
        }
    }


    public void loadMoreData(List<LectureInfo> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void refreshData(List<LectureInfo> data) {
        if (data == null) {
            mData = new ArrayList<LectureInfo>();
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
            Intent intent = new Intent(mContext, ForumDetailActivity.class);
            intent.putExtra("id", mData.get(position).getId());
            intent.putExtra("title", mData.get(position).getTitle());
            mContext.startActivity(intent);
        }
    }

}


