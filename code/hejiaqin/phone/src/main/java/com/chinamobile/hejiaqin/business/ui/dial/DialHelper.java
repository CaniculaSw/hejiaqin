package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;

/**
 * Created by yupeng on 1/7/17.
 */
public final class DialHelper {
    private static final DialHelper instance = new DialHelper();

    private DialHelper() {

    }

    public static DialHelper getInstance() {
        return instance;
    }

    public boolean isPhoneCall(ContactsInfo contactsInfo) {
        if (contactsInfo == null) {
            return false;
        }

        // 看第一个号码是否是手机号,如果是则展示手机拨号图标
        return isPhoneCall(contactsInfo.getPhone());
    }

    private boolean isPhoneCall(String phoneNumber) {
        return CommonUtils.isPhoneNumber(phoneNumber);
    }

//    public void call(Context context, NumberInfo numberInfo, String name) {
//        if (null == context) {
//            return;
//        }
//
//        if (null == numberInfo) {
//            return;
//        }
//
//        if (null == numberInfo.getNumber()) {
//            return;
//        }
//
//        if (isPhoneCall(numberInfo.getNumber())) {
//            startPhoneCall(context, numberInfo.getNumber());
//        } else {
//            startVideoCall(context, numberInfo, name);
//        }
//    }

    public void call(Context context, String number, String name) {
        if (null == context) {
            return;
        }

        if (null == number) {
            return;
        }


        if (isPhoneCall(number)) {
            startPhoneCall(context, number);
        } else {
            startVideoCall(context, number, name);
        }
    }

//    private void startVideoCall(Context context, NumberInfo numberInfo, String name) {
//        Intent outingIntent = new Intent(context, VideoCallActivity.class);
//        outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, numberInfo.getNumber());
//        outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NAME, name);
//        context.startActivity(outingIntent);
//    }

    private void startVideoCall(Context context, String number, String name) {
        Intent outingIntent = new Intent(context, VideoCallActivity.class);
        outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, number);
        if (null != name) {
            outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NAME, name);
        }
        context.startActivity(outingIntent);
    }

    private void startPhoneCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                phoneNumber));
        context.startActivity(intent);
    }
}
