package com.chinamobile.hejiaqin.business.ui.setting.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 16/8/26.
 */
public class AutoAnswerNumberSettingDialog extends Dialog{
    private Context mContext;
    private ClickListener mClickListener;

    public AutoAnswerNumberSettingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AutoAnswerNumberSettingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected AutoAnswerNumberSettingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_auto_answer_number_setting);
        mClickListener = new ClickListener();
//        mByScanTv = (TextView) findViewById(R.id.more_bind_tv_dialog_scan);
//        mByInputTv = (TextView) findViewById(R.id.more_bind_tv_dialog_input);
//        mByContactTv = (TextView) findViewById(R.id.more_bind_tv_dialog_contact);
//        mByCancleTv = (TextView) findViewById(R.id.more_bind_tv_dialog_cancle);
//
//        mByCancleTv.setOnClickListener(mClickListener);
//        mByContactTv.setOnClickListener(mClickListener);
//        mByInputTv.setOnClickListener(mClickListener);
//        mByScanTv.setOnClickListener(mClickListener);

    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    break;
            }
        }
    }
}
