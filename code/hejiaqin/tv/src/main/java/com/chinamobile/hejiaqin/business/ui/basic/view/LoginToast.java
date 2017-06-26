package com.chinamobile.hejiaqin.business.ui.basic.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 2017/6/25.
 */
public class LoginToast  {

    private Context mContext;

    public LoginToast(Context context) {
        this.mContext = context;
    }

    /***/
    public void showToast(int resId, int duration, LoginToast.Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        LayoutInflater inflate = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_xml, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(resId);
        toast.setView(v);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    /***/
    public void showToast(String text, int duration, LoginToast.Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        LayoutInflater inflate = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_xml, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(text);
        toast.setView(v);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    /***/
    public void showToast(View view, int duration, LoginToast.Position pos) {
        android.widget.Toast toast = new android.widget.Toast(this.mContext);
        toast.setView(view);
        toast.setDuration(duration);
        if (pos != null) {
            toast.setGravity(pos.gravity, pos.xOffset, pos.yOffset);
            toast.setMargin(pos.horizontalMargin, pos.verticalMargin);
        }
        toast.show();
    }

    /***/
    public static class Position {
        //起点位置(默认底部居中)
        public int gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

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
