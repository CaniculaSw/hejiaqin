package com.chinamobile.hejiaqin.business.ui.login.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 17/4/16.
 */
public class ServiceContractDialog extends Dialog {
    public static final String TAG = ServiceContractDialog.class.getSimpleName();


    private ServiceContractDialog(Context context, int theme) {
        this(context, theme, "");
    }

    private ServiceContractDialog(Context context, int theme, String text) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_window_service_contract);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    public static void show(Activity activity) {
        ServiceContractDialog serviceContractDialog = new ServiceContractDialog(activity, R.style.CalendarDialog);
        Window window = serviceContractDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        serviceContractDialog.setCancelable(true);
        serviceContractDialog.show();
    }

    public static void show(Activity activity, String text) {
        ServiceContractDialog serviceContractDialog = new ServiceContractDialog(activity, R.style.CalendarDialog, text);
        Window window = serviceContractDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        serviceContractDialog.setCancelable(false);
        serviceContractDialog.show();
    }
}
