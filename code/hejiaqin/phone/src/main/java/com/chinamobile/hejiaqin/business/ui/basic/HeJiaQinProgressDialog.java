package com.chinamobile.hejiaqin.business.ui.basic;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.chinamobile.hejiaqin.R;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/5/7.
 */
public class HeJiaQinProgressDialog extends Dialog {
    public HeJiaQinProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }

    public HeJiaQinProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.hejiaqin_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

}
