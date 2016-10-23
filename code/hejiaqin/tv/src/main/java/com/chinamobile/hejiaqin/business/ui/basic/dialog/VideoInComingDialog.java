package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by  on 2016/6/25.
 */
public class VideoInComingDialog extends Dialog {
    public LinearLayout addLayout;
    public LinearLayout cancelLayout;
    public ContactsInfo mContactsInfo;

    public VideoInComingDialog(Context context, int theme, ContactsInfo contactsInfo) {
        super(context, theme);
        mContactsInfo = contactsInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_video_incoming);

    }


}
