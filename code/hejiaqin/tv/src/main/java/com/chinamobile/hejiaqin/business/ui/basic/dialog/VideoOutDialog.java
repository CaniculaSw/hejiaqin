package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.call.CallSession;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by  on 2016/6/25.
 */
public class VideoOutDialog extends Dialog {

    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private TextView mCallerNumberTv;
    public String mCalleeNumber;
    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;
    //通话会话对象
    private CallSession mCallSession = null;
    private boolean closed;
    private Handler handler = new Handler();
    private MyToast myToast;
    private Context mContext;

    public VideoOutDialog(Context context, int theme, String calleeNumber,IVoipLogic voipLogic,IContactsLogic contactsLogic) {
        super(context, theme);
        this.mContext= context;
        this.mCalleeNumber = calleeNumber;
        this.mVoipLogic = voipLogic;
        this.mContactsLogic = contactsLogic;
        myToast = new MyToast(context.getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_video_out);
        mCallerIv = (CircleImageView) findViewById(R.id.caller_iv);
        mCallerNameTv = (TextView) findViewById(R.id.caller_name_tv);
        mCallerNumberTv = (TextView) findViewById(R.id.caller_number_tv);
        findViewById(R.id.reject_call_layout).setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                mVoipLogic.hangup(mCallSession, false, false, 0);
                VideoOutDialog.this.dismiss();
            }
        });
        outingCall();
        showOuting();
    }

    private void outingCall() {
        mCallSession = mVoipLogic.call(mCalleeNumber, true);
        if (mCallSession.getErrCode() != CallSession.ERRCODE_OK) {
            showToast(R.string.call_outing_error, Toast.LENGTH_SHORT, null);
            closed = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    VideoOutDialog.this.dismiss();
                }
            }, 2000);
        }
    }

    private void showOuting() {
        //TODO 查询姓名
            ContactsInfo info = searchContactInfo(mCalleeNumber);
            if (info != null) {
                if(!StringUtil.isNullOrEmpty(info.getName())) {
                    mCallerNameTv.setText(info.getName());
                    mCallerNameTv.setVisibility(View.VISIBLE);
                }
                mCallerNumberTv.setText(mCalleeNumber);
                if (!StringUtil.isNullOrEmpty(info.getPhotoSm())) {
                    Picasso.with(mContext.getApplicationContext())
                            .load(info.getPhotoSm())
                            .placeholder(R.drawable.contact_photo_default)
                            .error(R.drawable.contact_photo_default).into(mCallerIv);
                }
            } else {
                mCallerNumberTv.setText(mCalleeNumber);
            }
    }

    private ContactsInfo searchContactInfo(String phoneNumber) {
        //遍历本地联系人
        boolean isMatch = false;
        List<ContactsInfo> appContactsInfos = mContactsLogic.getCacheAppContactLst();
        for (ContactsInfo contactsInfo : appContactsInfos) {
            if (isMatch) {
                break;
            }
            if (contactsInfo.getNumberLst() != null) {
                for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(numberInfo.getNumberNoCountryCode())) {
                        return contactsInfo;
                    }
                }
            }
        }
        List<ContactsInfo> localContactsInfos = mContactsLogic.getCacheLocalContactLst();
        for (ContactsInfo contactsInfo : localContactsInfos) {
            if (isMatch) {
                break;
            }
            if (contactsInfo.getNumberLst() != null) {
                for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(numberInfo.getNumberNoCountryCode())) {
                        return contactsInfo;
                    }
                }
            }
        }
        return null;
    }

    protected void showToast(int resId, int duration, MyToast.Position pos) {
        myToast.showToast(resId, duration, pos);
    }

}
