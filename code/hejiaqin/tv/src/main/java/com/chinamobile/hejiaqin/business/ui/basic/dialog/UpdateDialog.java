package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.ui.basic.MyActivityManager;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 17/1/7.
 */
public class UpdateDialog extends Dialog implements View.OnClickListener {
    public static final String TAG = UpdateDialog.class.getSimpleName();
    private Button logout;
    private MyToast myToast;
    private Context mContext;
    private Handler handler = new Handler();
    private String tips = "";
    private TextView tipsText;
    private static boolean canBack = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mHandler != null) {
                UpdateDialog.this.handleStateMessage(msg);
            }
        }
    };

    private UpdateDialog(Context context, int theme) {
        this(context, theme, "");
    }

    private UpdateDialog(Context context, int theme, String text) {
        super(context, theme);
        this.mContext = context;
        tips = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_tip);
        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(this);
        tipsText = (TextView) findViewById(R.id.tips_text);
        if (!StringUtil.isNullOrEmpty(tips)) {
            tipsText.setText(tips);
        }
    }

    private void handleStateMessage(Message msg) {
        switch (msg.what) {
            default:
                break;
        }
    }

    protected void showToast(int resId, int duration, MyToast.Position pos) {
        myToast.showToast(resId, duration, pos);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
    /***/
    public static void show(Activity activity) {
        UpdateDialog videoInComingDialog = new UpdateDialog(activity, R.style.CalendarDialog);
        Window window = videoInComingDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        videoInComingDialog.setCancelable(false);
        videoInComingDialog.show();
    }
    /***/
    public static void show(Activity activity, String text) {
        UpdateDialog videoInComingDialog = new UpdateDialog(activity, R.style.CalendarDialog, text);
        Window window = videoInComingDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        videoInComingDialog.setCancelable(false);
        videoInComingDialog.show();
    }
    /***/
    public static void show(Activity activity, String text,boolean isCanBack){
        canBack = isCanBack;
        show(activity,text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                if (canBack){
                    canBack = false;
                    dismiss();
                }
                else {
                    canBack = false;
                    dismiss();
                    MyActivityManager.getInstance().finishAllActivity(null);
                }
                break;
            default:
                break;
        }
    }
}
