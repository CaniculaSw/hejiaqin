package com.chinamobile.hejiaqin.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/4.
 */
public class ExerciseAdapter extends  RecyclerView.Adapter {

    private final int VIEW_TYPE_TOP = 0;
    private final int VIEW_TYPE_CONTENT = 1;
    private final int VIEW_TYPE_BUTTOM = 2;

    private List<HashMap<String,String>> mData;
    private Context mContext;

    public ExerciseAdapter(Context context){
        this.mContext = context;
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            mData.add(null);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == VIEW_TYPE_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exercise_item1, parent, false);
            holder = new TextHoler(view);
        }else if(viewType == VIEW_TYPE_CONTENT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exercise_item2, parent, false);
            holder = new TextHoler2(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exercise_item3, parent, false);
            holder = new TextHoler3(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position) == VIEW_TYPE_BUTTOM){
            TextHoler3 mHolder = (TextHoler3) holder;

            Picasso.with(mContext).load(R.mipmap.bg_lecture).into(mHolder.iv_exercise);
        }
    }

    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(position < 4){
            return VIEW_TYPE_TOP;
        }else if(position == 4){
            return VIEW_TYPE_CONTENT;
        }else {
            return VIEW_TYPE_BUTTOM;
        }
    }

    /**
     * 内部TextHoler
     * @author edsheng
     */
    public class TextHoler extends RecyclerView.ViewHolder {

        public TextHoler(View v) {
            super(v);
        }
    }

    /**
     * 内部TextHoler
     * @author edsheng
     */
    public class TextHoler2 extends RecyclerView.ViewHolder {

        public TextHoler2(View v) {
            super(v);
        }
    }
    /**
     * 内部TextHoler
     * @author edsheng
     */
    public class TextHoler3 extends RecyclerView.ViewHolder {

        public ImageView iv_exercise;

        public TextHoler3(View v) {
            super(v);
            iv_exercise = (ImageView)v.findViewById(R.id.iv_exercise);
        }
    }

}
