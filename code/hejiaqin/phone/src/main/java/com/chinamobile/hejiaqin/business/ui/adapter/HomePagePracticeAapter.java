package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.homePage.MyPracticeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class HomePagePracticeAapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyPracticeInfo> mAllData;
    private List<MyPracticeInfo> mData;
    private boolean isAll;
    private int defaultMaxItems = 5;
    private int maxTop = 3;
    private PopupWindow popupWindow;
    private PopClickListen mPopClickListen;

    public HomePagePracticeAapter(Context context,PopClickListen popClickListen) {
        this.mContext = context;
        mAllData = new ArrayList<MyPracticeInfo>();
        mData = new ArrayList<MyPracticeInfo>();
        this.mPopClickListen = popClickListen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holer = null;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_page_practice, parent, false);
        holer = new HolderView(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyPracticeInfo info = mData.get(position);
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
            tHolder.itemView.setOnLongClickListener(new LongClickListener());
            if (BussinessConstants.DictInfo.PLACE_TOP.equals(info.getPlacedTop())) {
                tHolder.homePracticeLayout.setBackgroundResource(R.drawable.home_practice_top_bg);
            } else {
                tHolder.homePracticeLayout.setBackgroundResource(R.drawable.home_small_item_bg);
            }
            tHolder.nameTv.setText(info.getTitle());
            //我的
            if (BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(info.getType())) {
                tHolder.joinNumTv.setVisibility(View.GONE);
                tHolder.recommendedMarkTv.setVisibility(View.GONE);
                long weekNum = info.getWeeknum();
                //控制一下界面显示
                if (weekNum > 9999) {
                    weekNum = 9999;
                }
                tHolder.weekNumTv.setText(String.valueOf(weekNum));
                long score = info.getScore();
                //控制一下界面显示
                if (score > 9999) {
                    score = 9999;
                }
                tHolder.scoreTv.setText(String.valueOf(score));
                tHolder.weekNumDescTv.setVisibility(View.VISIBLE);
                tHolder.weekNumTv.setVisibility(View.VISIBLE);
                tHolder.weekNumUnitTv.setVisibility(View.VISIBLE);
                tHolder.homeRightLayout.setVisibility(View.VISIBLE);
            }
            //推荐
            else {
                tHolder.weekNumDescTv.setVisibility(View.GONE);
                tHolder.weekNumTv.setVisibility(View.GONE);
                tHolder.weekNumUnitTv.setVisibility(View.GONE);
                tHolder.homeRightLayout.setVisibility(View.GONE);

                tHolder.joinNumTv.setText(String.valueOf(info.getParticipants() + mContext.getString(R.string.join_num_desc)));

                tHolder.joinNumTv.setVisibility(View.VISIBLE);
                tHolder.recommendedMarkTv.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void refreshData(List<MyPracticeInfo> data) {
        if (data != null) {
            mAllData = data;
            if (isAll) {
                mData.addAll(mAllData);
            } else {
                if (mAllData.size() > defaultMaxItems) {
                    mData.clear();
                    for (int i = 0; i < defaultMaxItems; i++) {
                        mData.add(mAllData.get(i));
                    }
                } else {
                    mData.addAll(mAllData);
                }
            }
        }
        notifyDataSetChanged();
    }


    public boolean pullDown() {
        if (mAllData.size() > defaultMaxItems) {
            mData.clear();
            mData.addAll(mAllData);
            notifyDataSetChanged();
            isAll = true;
            return true;
        }
        return false;
    }

    public boolean pushUp() {
        if (mData.size() > defaultMaxItems) {
            mData.clear();
            for (int i = 0; i < defaultMaxItems; i++) {
                mData.add(mAllData.get(i));
            }
            notifyDataSetChanged();
            isAll = false;
            return true;
        }
        return false;
    }


    public List<MyPracticeInfo> getData() {
        return mData;
    }

    public List<MyPracticeInfo> getAllData() {
        return mAllData;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        public LinearLayout homePracticeLayout;
        public RelativeLayout homeLeftLayout;
        public RelativeLayout homeRightLayout;
        private TextView nameTv;
        private TextView weekNumDescTv;
        private TextView weekNumTv;
        private TextView weekNumUnitTv;
        private TextView joinNumTv;
        private TextView recommendedMarkTv;
        private TextView scoreTv;

        public HolderView(View view) {
            super(view);
            homePracticeLayout = (LinearLayout) view.findViewById(R.id.home_practice_layout);
            homeLeftLayout = (RelativeLayout) view.findViewById(R.id.left_layout);
            homeRightLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
            nameTv = (TextView) view.findViewById(R.id.name_tv);
            weekNumDescTv = (TextView) view.findViewById(R.id.week_num_desc_tv);
            weekNumTv = (TextView) view.findViewById(R.id.week_num_tv);
            weekNumUnitTv = (TextView) view.findViewById(R.id.week_num_unit_tv);
            joinNumTv = (TextView) view.findViewById(R.id.join_num_tv);
            recommendedMarkTv = (TextView) view.findViewById(R.id.recommended_mark_tv);
            scoreTv = (TextView) view.findViewById(R.id.score_tv);
        }
    }

    private int getTopDataNum() {
        int topDataNum = 0;
        for (MyPracticeInfo info : mData) {
            if (BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(info.getType()) &&
                    BussinessConstants.DictInfo.PLACE_TOP.equals(info.getPlacedTop())) {
                topDataNum = topDataNum + 1;
            }
        }
        return topDataNum;
    }

    class LongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            MyPracticeInfo currentInfo = mData.get(position);
            if (BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(currentInfo.getType()) &&
                    BussinessConstants.DictInfo.PLACE_TOP.equals(currentInfo.getPlacedTop())) {
                //展示取消置顶的popwindow
                showpPopwindow(v, currentInfo, true);
                return true;
            } else if (BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(currentInfo.getType()) &&
                    getTopDataNum() < maxTop) {
                //展示显示置顶的popwindow
                showpPopwindow(v, currentInfo, false);
                return true;
            }
            return false;
        }
    }

    private void showpPopwindow(View v, final MyPracticeInfo info, final boolean cancelTop) {
        //获取view的坐标
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //获取view的宽和高
        int width = v.getWidth();
        int height = v.getHeight();
        int popWidth = (int) mContext.getResources().getDimension(R.dimen.home_page_pop_width);
        int popHeight = (int) mContext.getResources().getDimension(R.dimen.home_page_pop_height);
        int popX = location[0] + width / 2 - popWidth / 2;
        int popY = location[1] + height / 3 - popHeight;
        View popView = LayoutInflater.from(mContext).inflate(
                R.layout.home_page_popup_layout, null);
        int contentId = cancelTop ? R.string.cancel_top_desc : R.string.top_desc;
        ((TextView) popView.findViewById(R.id.pop_tv)).setText(contentId);
        popupWindow = new PopupWindow(popView, popWidth,
                popHeight, true);
        popView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cancelTop) {
                    info.setPlacedTop(BussinessConstants.DictInfo.CANCEL_PLACE_TOP);
                } else {
                    info.setPlacedTop(BussinessConstants.DictInfo.PLACE_TOP);
                }
                Comparator comp = new SortComparator();
                Collections.sort(mAllData, comp);
                Collections.sort(mData, comp);
                HomePagePracticeAapter.this.notifyDataSetChanged();
                HomePagePracticeAapter.this.mPopClickListen.update(info);
                popupWindow.dismiss();
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.TOP,
                popX,
                popY);
    }

    class SortComparator implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            MyPracticeInfo a = (MyPracticeInfo) lhs;
            MyPracticeInfo b = (MyPracticeInfo) rhs;
            //先比较我的和推荐
            int compareType = b.getType().compareTo(a.getType());
            //如果不相等
            if (compareType != 0) {
                //取反 PACTICE_TYPE_MY 1排在前 PRACTICE_TYPE_RECOMMOND 2排在后
                return 0-compareType;
            }
            //都是我的
            if (BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(a.getType())) {
                int compareTop = b.getPlacedTop().compareTo(a.getPlacedTop());
                if(compareTop !=0)
                {
                    return compareTop;
                }
                return b.getPracticeDate().compareTo(a.getPracticeDate());
            }
            //都是推荐的
            else {
                if (a.getParticipants() == b.getParticipants()) {
                    return 0;
                } else if (b.getParticipants() > a.getParticipants()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    public interface PopClickListen
    {
         void update(MyPracticeInfo info);

    }


}


