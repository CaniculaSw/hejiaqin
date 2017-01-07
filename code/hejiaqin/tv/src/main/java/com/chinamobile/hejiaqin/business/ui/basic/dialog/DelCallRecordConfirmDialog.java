package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by  on 2016/6/25.
 */
public class DelCallRecordConfirmDialog extends Dialog {
    public LinearLayout mConfirmLay;
    public LinearLayout mCancelLayout;

    public DelCallRecordConfirmDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_del_call_record_confirm);
        mConfirmLay = (LinearLayout) findViewById(R.id.btn_agree);
        mCancelLayout = (LinearLayout)findViewById(R.id.btn_denied);
    }

}
