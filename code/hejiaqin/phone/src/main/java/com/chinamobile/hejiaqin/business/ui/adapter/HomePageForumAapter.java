package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.model.homePage.HomeForumInfo;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class HomePageForumAapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeForumInfo> mData;
    private int defaultMaxItems = 3;

    public HomePageForumAapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<HomeForumInfo>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_page_forum, parent, false);
        holer = new HolderView(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeForumInfo info = mData.get(position);
        if (info != null) {
            HolderView tHolder = (HolderView) holder;
            tHolder.itemView.setTag(position);
            tHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    //TODO detail详情

                }
            });
            if (StringUtil.isNullOrEmpty(info.getCover())) {
                Picasso.with(mContext).load(info.getCover()).placeholder(R.color.homepage_forum_list_bg_color).into(tHolder.homeForumImage);
            }
            tHolder.nameTv.setText(info.getTitle());
            int min = Math.round(info.getTotaltime() / 60);
            tHolder.timesTv.setText(mContext.getString(R.string.times_desc) + String.valueOf(min) + mContext.getString(R.string.times_unit_desc));
            tHolder.createTimeTv.setText(mContext.getString(R.string.create_time_desc) + (info.getCreatetime() != null ? info.getCreatetime() : ""));
            tHolder.viewNumTv.setText(mContext.getString(R.string.view_num_desc) + String.valueOf(info.getTotaltime()) + mContext.getString(R.string.view_num_unit_desc));
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void refreshData(List<HomeForumInfo> data) {
        if (data != null)  {
            mData = data;
            //最多显示3条
            if (mData.size() > defaultMaxItems) {
                for (int i = mData.size()-1; i >= defaultMaxItems; i--) {
                    mData.remove(i);
                }
            }
        }
        notifyDataSetChanged();
    }



    public class HolderView extends RecyclerView.ViewHolder {
        private LinearLayout homeForumLayout;
        private RelativeLayout homeForumContentLayout;
        private ImageView homeForumImage;
        private TextView nameTv;
        private TextView timesTv;
        private TextView createTimeTv;
        private TextView viewNumTv;

        public HolderView(View view) {
            super(view);
            homeForumLayout = (LinearLayout) view.findViewById(R.id.home_forum_layout);
            homeForumContentLayout = (RelativeLayout) view.findViewById(R.id.home_forum_content_layout);
            homeForumImage = (ImageView) view.findViewById(R.id.home_forum_image);
            nameTv = (TextView) view.findViewById(R.id.name_tv);
            timesTv = (TextView) view.findViewById(R.id.times_tv);
            createTimeTv = (TextView) view.findViewById(R.id.create_time_tv);
            viewNumTv = (TextView) view.findViewById(R.id.view_num_tv);
        }
    }

}


