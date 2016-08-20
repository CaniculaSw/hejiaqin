package com.chinamobile.hejiaqin.business.ui.basic.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/22.
 */
public class MyToast {

    private Context mContext;

    public MyToast(Context context) {
        this.mContext = context;
    }

    public void showToast(int resId, int duration, Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        LayoutInflater inflate = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_view, null);
        TextView tv = (TextView)v.findViewById(R.id.message);
        tv.setText(resId);
        toast.setView(v);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    public void showToast(String text, int duration, Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        LayoutInflater inflate = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_view, null);
        TextView tv = (TextView)v.findViewById(R.id.message);
        tv.setText(text);
        toast.setView(v);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    public void showToast(View view, int duration, Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        toast.setView(view);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    public class Position {
        //起点位置(默认底部居中)
        public int gravity = Gravity.BOTTOM | Gravity.CENTER;

        //水平位移像素 默认不偏移 正右负左
        public int xOffset;

        //竖直位移像素 默认不偏移 正上负下
        public int yOffset;

        //以横向和纵向的百分比计算 水平位移正右负左
        public float horizontalMargin;

        //以横向和纵向的百分比计算 竖直位移正上负下
        public float verticalMargin;


    }
}
