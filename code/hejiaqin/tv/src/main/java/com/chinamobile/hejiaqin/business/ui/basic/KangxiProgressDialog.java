package com.chinamobile.hejiaqin.business.ui.basic;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.chinamobile.hejiaqin.tv.R;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/5/7.
 */
public class KangxiProgressDialog extends Dialog {
    public KangxiProgressDialog(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }

    public KangxiProgressDialog(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.kangxi_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

}
