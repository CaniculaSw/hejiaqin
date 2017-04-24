package com.chinamobile.hejiaqin.business.ui.login.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 16/6/27.
 */
public class DisplayErrorDialog extends Dialog {
    //    private Context mContext;
    private TextView errorTextView;
    private TextView returnBtnTv;
    private String errorText;
    private ClickListener mClickListener;

    public DisplayErrorDialog(Context context, String errorText) {
        super(context);
        //        this.mContext = context;
        this.errorText = errorText;
    }

    public DisplayErrorDialog(Context context, int themeResId, String errorText) {
        super(context, themeResId);
        //        this.mContext = context;
        this.errorText = errorText;
    }

    protected DisplayErrorDialog(Context context, boolean cancelable,
                                 OnCancelListener cancelListener, String errorText) {
        super(context, cancelable, cancelListener);
        //        this.mContext = context;
        this.errorText = errorText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_error);
        mClickListener = new ClickListener();
        errorTextView = (TextView) findViewById(R.id.error_text);
        errorTextView.setText(this.errorText);
        returnBtnTv = (TextView) findViewById(R.id.return_btn);
        returnBtnTv.setText(R.string.prompt_return);
        returnBtnTv.setClickable(true);
        returnBtnTv.setOnClickListener(mClickListener);
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.return_btn:
                    //取消键
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
