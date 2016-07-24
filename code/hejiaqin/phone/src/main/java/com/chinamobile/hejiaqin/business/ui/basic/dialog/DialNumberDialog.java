package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;

import java.util.List;

/**
 * Created by zhanggj on 2016/6/25.
 */
public class DialNumberDialog extends Dialog {
    public LinearLayout addLayout;
    public LinearLayout cancelLayout;
    public ContactsInfo mContactsInfo;

    public DialNumberDialog(Context context, int theme, ContactsInfo contactsInfo) {
        super(context, theme);
        mContactsInfo = contactsInfo;
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
                        Intent outingIntent = new Intent(getContext(), VideoCallActivity.class);
                        outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, number);
                        outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NAME, name);
                        getContext().startActivity(outingIntent);
                        dismiss();
                    }
                });

                numberInfoLayout.addView(numberInfoView);
            }
        }
        cancelLayout = (LinearLayout) findViewById(R.id.cancel_layout);
    }


}
