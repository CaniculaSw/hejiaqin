package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by  on 2016/6/25.
 */
public class AddContactDialog extends Dialog {
    public LinearLayout addLayout;
    public LinearLayout cancelLayout;

    public AddContactDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_add_contact);
        addLayout = (LinearLayout) findViewById(R.id.add_contact_layout);
        cancelLayout = (LinearLayout) findViewById(R.id.cancel_layout);
    }

}
