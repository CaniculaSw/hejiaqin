package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.business.ui.dial.StbVideoCallActivity;
import com.chinamobile.hejiaqin.business.ui.dial.VtVideoCallActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.logic.ILogic;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.call.CallSession;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by  on 2016/6/25.
 */
public class VideoOutDialog extends Dialog {

    public static final String TAG = VideoOutDialog.class.getSimpleName();

    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private TextView mCallerNumberTv;
    public String mCalleeNumber;
    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;
    private boolean mIsPhoneApp;
    //通话会话对象
    private CallSession mCallSession = null;
    private boolean closed;
    private Handler handler = new Handler();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mHandler != null) {
                VideoOutDialog.this.handleStateMessage(msg);
            }
        }
    };
    private MyToast myToast;
    private Context mContext;

    public VideoOutDialog(Context context, int theme, String calleeNumber, IVoipLogic voipLogic,
                          IContactsLogic contactsLogic, boolean isPhoneApp) {
        super(context, theme);
        this.mContext = context;
        this.mCalleeNumber = calleeNumber;
        this.mVoipLogic = voipLogic;
        this.mContactsLogic = contactsLogic;
        this.mIsPhoneApp = isPhoneApp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ILogic) this.mVoipLogic).addHandler(mHandler);
        setContentView(R.layout.popwindow_video_out);
        myToast = new MyToast(mContext.getApplicationContext());
        mCallerIv = (CircleImageView) findViewById(R.id.caller_iv);
        mCallerNameTv = (TextView) findViewById(R.id.caller_name_tv);
        mCallerNumberTv = (TextView) findViewById(R.id.caller_number_tv);
        findViewById(R.id.reject_call_layout).setOnClickListener(new View.OnClickListener() {

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
        mCallSession = mVoipLogic.call(mCalleeNumber, true, mIsPhoneApp);
        if (mCallSession.getErrCode() != CallSession.ERRCODE_OK) {
            showToast(R.string.call_outing_error, Toast.LENGTH_SHORT, null);
            closed = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    VideoOutDialog.this.dismiss();
                }
            }, 3000);
        }
    }

    private void showOuting() {
        //TODO 查询姓名
        ContactsInfo info = searchContactInfo(mCalleeNumber);
        if (info != null) {
            if (!StringUtil.isNullOrEmpty(info.getName())) {
                mCallerNameTv.setText(info.getName());
                mCallerNameTv.setVisibility(View.VISIBLE);
            }
            mCallerNumberTv.setText(mCalleeNumber);
            if (!StringUtil.isNullOrEmpty(info.getPhotoSm())) {
                Picasso.with(mContext.getApplicationContext()).load(info.getPhotoSm())
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
                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(
                            numberInfo.getNumberNoCountryCode())
                            || CommonUtils.getPhoneNumber(phoneNumber).equals(
                                    "92" + numberInfo.getNumberNoCountryCode())) {
                        return contactsInfo;
                    }
                }
            }
        }
        List<ContactsInfo> localContactsInfos = mContactsLogic.getCacheAppContactLst();
        for (ContactsInfo contactsInfo : localContactsInfos) {
            if (isMatch) {
                break;
            }
            if (contactsInfo.getNumberLst() != null) {
                for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(
                            numberInfo.getNumberNoCountryCode())
                            || CommonUtils.getPhoneNumber(phoneNumber).equals(
                                    "92" + numberInfo.getNumberNoCountryCode())) {
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

    private void handleStateMessage(Message msg) {
        switch (msg.what) {
            case BussinessConstants.DialMsgID.CALL_INCOMING_FINISH_CLOSING_MSG_ID:
                if (!closed) {
                    mVoipLogic.dealOnClosed(mCallSession, false, false, 0);
                    closed = true;
                }
                dismiss();
                break;
            case BussinessConstants.DialMsgID.CALL_ON_TALKING_MSG_ID:
                if (Const.getDeviceType() == Const.TYPE_OTHER) {
                    Intent intentTalking = new Intent(getContext(), VtVideoCallActivity.class);
                    mContext.startActivity(intentTalking);
                } else {
                    Intent intentTalking = new Intent(getContext(), StbVideoCallActivity.class);
                    mContext.startActivity(intentTalking);
                }
                dismiss();
                break;
            case BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID:
                if (msg.obj != null) {
                    CallSession session = (CallSession) msg.obj;
                    if (mCallSession != null && mCallSession.equals(session)) {
                        mVoipLogic.dealOnClosed(mCallSession, false, false, 0);
                        closed = true;
                        if (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_TEMPORARILY_UNAVAILABLE) {
                            showToast(R.string.sip_temporarily_unavailable, Toast.LENGTH_SHORT,
                                    null);
                        } else if ((mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_BUSY_HERE || mCallSession
                                .getSipCause() == BussinessConstants.DictInfo.SIP_DECLINE)) {
                            showToast(R.string.sip_busy_here, Toast.LENGTH_SHORT, null);
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                VideoOutDialog.this.dismiss();
                            }
                        }, 3000);
                    } else if (session != null
                            && session.getType() == CallSession.TYPE_VIDEO_INCOMING) {
                        mVoipLogic.dealOnClosed(session, true, false, 0);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void dismiss() {
        ((ILogic) this.mVoipLogic).removeHandler(mHandler);
        super.dismiss();
    }

    /***/
    public static void show(Context context, String calleeNumber, IVoipLogic voipLogic,
                            IContactsLogic contactsLogic, boolean isPhoneApp) {
        VideoOutDialog videoOutDialog = new VideoOutDialog(context, R.style.CalendarDialog,
                calleeNumber, voipLogic, contactsLogic, isPhoneApp);
        Window window = videoOutDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        videoOutDialog.setCancelable(false);
        videoOutDialog.show();
    }

}
