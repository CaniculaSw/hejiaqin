package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;
import com.chinamobile.hejiaqin.tv.R;

import java.util.List;

/**
 * Created by  on 2016/6/25.
 */
public class VideoOutDialog extends Dialog {
    public LinearLayout addLayout;
    public LinearLayout cancelLayout;
    public ContactsInfo mContactsInfo;

    public VideoOutDialog(Context context, int theme, ContactsInfo contactsInfo) {
        super(context, theme);
        mContactsInfo = contactsInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_video_out);

    }


}
