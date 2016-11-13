package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;

import java.util.List;

/**
 * Created by  on 2016/6/25.
 */
public class DialNumberDialog extends Dialog {
    public LinearLayout addLayout;
    public LinearLayout cancelLayout;
    public ContactsInfo mContactsInfo;
    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;

    public DialNumberDialog(Context context, int theme, ContactsInfo contactsInfo,IVoipLogic voipLogic,IContactsLogic contactsLogic) {
        super(context, theme);
        mContactsInfo = contactsInfo;
        this.mVoipLogic = voipLogic;
        this.mContactsLogic = contactsLogic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_dial_number);
        LinearLayout numberInfoLayout = (LinearLayout) findViewById(R.id.number_info_layout);

        if (null != mContactsInfo && null != mContactsInfo.getNumberLst()) {
            List<NumberInfo> numberInfoList = mContactsInfo.getNumberLst();
            for (NumberInfo numberInfo : numberInfoList) {
                View numberInfoView = LayoutInflater.from(getContext()).inflate(R.layout.view_number_info, null);
                TextView textView = (TextView) numberInfoView.findViewById(R.id.text);

                final String number = numberInfo.getNumber();
                textView.setText(number);

                final String name = mContactsInfo.getName();
                numberInfoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoOutDialog.show(getContext(), number, mVoipLogic, mContactsLogic);
                    }
                });

                numberInfoLayout.addView(numberInfoView);
            }
        }
        cancelLayout = (LinearLayout) findViewById(R.id.cancel_layout);
    }


}
