package com.chinamobile.hejiaqin.business.ui.more.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.more.SelectableContactActivity;
import com.customer.framework.ui.AdapterViewHolder;
import com.customer.framework.utils.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eshaohu on 16/9/18.
 */
public class BindedTVListAdapter extends BaseAdapter implements ListAdapter {
    private static final String TAG = "BindedTVListAdapter";
    private List<UserInfo> mBindedTV;
    private Context mContext;
    private AdapterViewHolder mViewHolder;

    public BindedTVListAdapter(Context context) {
        super();
        mContext = context;
        mBindedTV = new ArrayList<UserInfo>();
    }

    @Override
    public int getCount() {
        return mBindedTV == null ? 0 : mBindedTV.size();
    }

    @Override
    public Object getItem(int position) {
        return mBindedTV == null ? null : mBindedTV.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mViewHolder = AdapterViewHolder.get(mContext, convertView, parent, R.layout.adapter_binded_tv, position);
        mViewHolder.setText(R.id.tv_name, mBindedTV.get(position).getUserName());
        Picasso.with(mContext).load(BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + mBindedTV.get(position).getPhotoSm())
                .placeholder(R.mipmap.pic80)
                .error(R.mipmap.pic80).into((CircleImageView) mViewHolder.getView(R.id.tv_avatar_ci));

        mViewHolder.getView(R.id.send_contact_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SelectableContactActivity.class);
                intent.putExtra("tvAccount",mBindedTV.get(position).getTvAccount());
                mContext.startActivity(intent);
            }
        });

        mViewHolder.getView(R.id.remote_monitor_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d(TAG,"Will start remote monitor.");
            }
        });

        return mViewHolder.getView();
    }

    public void setData(List<UserInfo> bindedTVList) {
        if (bindedTVList != null) {
            mBindedTV.clear();
            mBindedTV.addAll(bindedTVList);
        }
        notifyDataSetChanged();
    }
}
