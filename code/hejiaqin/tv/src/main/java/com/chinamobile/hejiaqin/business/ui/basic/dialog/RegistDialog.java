package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.LoginLogic;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 2017/6/23.
 */
public class RegistDialog extends Dialog {
    public static final String TAG = RegistDialog.class.getSimpleName();
    private TextView login;
    private Context mContext;
    private String tips = "";
    private TextView tipsText;
    private static boolean canBack = false;
    private static boolean needToJump;


    private RegistDialog(Context context, int theme) {
        this(context, theme, "");
    }

    private RegistDialog(Context context, int theme, String text) {
        super(context, theme);
        this.mContext = context;
        tips = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_regist);
        login = (TextView) findViewById(R.id.login_button);
        tipsText = (TextView) findViewById(R.id.tips_text);
        if (!StringUtil.isNullOrEmpty(tips)) {
            tipsText.setText(tips);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needToJump){
                    LoginLogic.getInstance(mContext).sendEmptyMessageDelayed(BussinessConstants.LoginMsgID.JUMP_TO_LOGIN_ACTIVITY,100);
                }
                dismiss();
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    /***/
    public static void show(Activity activity) {
        RegistDialog videoInComingDialog = new RegistDialog(activity, R.style.CalendarDialog);
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
        RegistDialog videoInComingDialog = new RegistDialog(activity, R.style.CalendarDialog, text);
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
    public static void show(Activity activity, String text, boolean isCanBack, boolean isNeedToJump) {
        needToJump = isNeedToJump;
        canBack = isCanBack;
        show(activity, text);
    }

}
