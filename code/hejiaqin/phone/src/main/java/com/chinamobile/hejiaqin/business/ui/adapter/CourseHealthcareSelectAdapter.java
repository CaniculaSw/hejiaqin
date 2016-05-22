package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.CourseCacheManager;
import com.customer.framework.component.storage.StorageMgr;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/4.
 */
public class CourseHealthcareSelectAdapter extends RecyclerView.Adapter {

    private String[] mData;
    private Context mContext;
    private ClickListener mClickListener;
    private OnCoursePracticeChangeListener mChangeListener;

    public CourseHealthcareSelectAdapter(Context context, String[] data, OnCoursePracticeChangeListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mClickListener = new ClickListener();
        this.mChangeListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_course_select_item, parent, false);
        RecyclerView.ViewHolder holder = new TextHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextHoler mHolder = (TextHoler) holder;
        mHolder.layout_div.setTag(position);
        mHolder.layout_div.setOnClickListener(mClickListener);
        mHolder.tv_text.setText(mData[position]);
        int index = StorageMgr.getInstance().getSharedPStorage(mContext.getApplicationContext()).getInt(BussinessConstants.Courses.COURSE_HEALTHCARE_SELECT_INDEX);
        if(index < 0){
            index = 0;
        }
        if(index == position){
            mHolder.iv_img.setVisibility(View.VISIBLE);
        }else{
            mHolder.iv_img.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.length;
    }


    /**
     * 内部TextHoler
     * @author edsheng
     */
    public class TextHoler extends RecyclerView.ViewHolder {

        public TextView tv_text;
        public ImageView iv_img;
        public LinearLayout layout_div;

        public TextHoler(View v) {
            super(v);
            layout_div = (LinearLayout) v.findViewById(R.id.layout_div);
            tv_text = (TextView) v.findViewById(R.id.tv_select_item_text);
            iv_img = (ImageView) v.findViewById(R.id.iv_selected);
        }
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if(mData != null) {
                CourseCacheManager.saveCourseHealthcareToLoacl(mContext.getApplicationContext(), position, mData[position]);
                notifyDataSetChanged();
                if(mChangeListener != null){
                    mChangeListener.onSelectChange(position,mData[position]);
                }
            }
        }
    }

    public interface OnCoursePracticeChangeListener{
        void onSelectChange(int position, String text);
    }
}
