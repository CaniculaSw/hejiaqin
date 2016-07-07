package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.R;

/**
 * Created by zhanggj on 2016/6/25.
 */
public class EditContactDialog extends Dialog {
    public LinearLayout editLayout;
    public LinearLayout delLayout;
    public LinearLayout cancelLayout;

    public EditContactDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_edit_contact);
        editLayout = (LinearLayout) findViewById(R.id.edit_contact_layout);
        delLayout = (LinearLayout) findViewById(R.id.del_contact_layout);
        cancelLayout = (LinearLayout) findViewById(R.id.cancel_layout);
    }


}
