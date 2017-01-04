package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.logic.ILogic;
import com.huawei.rcs.call.CallSession;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eshaohu on 17/1/4.
 */
public class RegistingDialog extends Dialog {
    public static final String TAG = RegistingDialog.class.getSimpleName();

    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private TextView mCallerNumberTv;
    private RelativeLayout mOperationLayout;
    private LinearLayout mAutoRejectLayout;

    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;
    private long mIncomingSessionId;

    private MyToast myToast;
    private Context mContext;
    private boolean closed;
    //通话会话对象
    private CallSession mCallSession = null;
    private Handler handler = new Handler();

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mHandler != null) {
                RegistingDialog.this.handleStateMessage(msg);
            }
        }
    };

    public RegistingDialog(Context context, int theme) {
        super(context, theme);
//        this.mIncomingSessionId = incomingSessionId;
//        this.mVoipLogic = voipLogic;
//        this.mContactsLogic = contactsLogic;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((ILogic)this.mVoipLogic).addHandler(mHandler);
        setContentView(R.layout.popwindow_registing);
//        mCallerIv = (CircleImageView) findViewById(R.id.caller_iv);
//        mCallerNameTv = (TextView) findViewById(R.id.caller_name_tv);
//        mCallerNumberTv = (TextView) findViewById(R.id.caller_number_tv);
//        myToast = new MyToast(mContext.getApplicationContext());
//        mCallSession = CallApi.getCallSessionById(this.mIncomingSessionId);
//        if (mCallSession == null) {
//            dismiss();
//            return;
//        }
//        mOperationLayout = (RelativeLayout)findViewById(R.id.call_operation_layout);
//        findViewById(R.id.answer_call_layout).setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v) {
//                if (closed) {
//                    LogUtil.w(TAG, "is closed");
//                    return;
//                }
//                if(onClickAnswer)
//                {
//                    return;
//                }
//                mVoipLogic.answerVideo(mCallSession);
//            }
//        });
//        findViewById(R.id.reject_call_layout).setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View v) {
//                mVoipLogic.hangup(mCallSession, true, false, 0);
//                VideoInComingDialog.this.dismiss();
//            }
//        });
//        mAutoRejectLayout = (LinearLayout)findViewById(R.id.auto_reject_call_layout);
//        mAutoRejectLayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mVoipLogic.hangup(mCallSession, true, false, 0);
//                VideoInComingDialog.this.dismiss();
//            }
//        });
//        showIncoming();
//        if(CommonUtils.isAutoAnswer(getContext().getApplicationContext(),mCallSession.getPeer().getNumber()))
//        {
//            ((TextView)findViewById(R.id.call_status_tv)).setText(R.string.auto_answer_status);
//            mOperationLayout.setVisibility(View.GONE);
//            mAutoRejectLayout.setVisibility(View.VISIBLE);
//            mVoipLogic.answerVideo(mCallSession);
//        }
    }

    private void showIncoming() {
//        String incomingNumber = CommonUtils.getPhoneNumber(mCallSession.getPeer().getNumber());
//        // 查询姓名和头像信息
//        ContactsInfo info = searchContactInfo(incomingNumber);
//        if (info != null) {
//            if(!StringUtil.isNullOrEmpty(info.getName())) {
//                mCallerNameTv.setText(info.getName());
//                mCallerNameTv.setVisibility(View.VISIBLE);
//            }
//            mCallerNumberTv.setText(incomingNumber);
//            if (!StringUtil.isNullOrEmpty(info.getPhotoSm())) {
//                Picasso.with(mContext.getApplicationContext())
//                        .load(info.getPhotoSm())
//                        .placeholder(R.drawable.contact_photo_default)
//                        .error(R.drawable.contact_photo_default).into(mCallerIv);
//            }
//        } else {
//            mCallerNumberTv.setText(incomingNumber);
//        }
    }

//    private ContactsInfo searchContactInfo(String phoneNumber) {
//        //遍历本地联系人
//        boolean isMatch = false;
//        List<ContactsInfo> appContactsInfos = mContactsLogic.getCacheAppContactLst();
//        for (ContactsInfo contactsInfo : appContactsInfos) {
//            if (isMatch) {
//                break;
//            }
//            if (contactsInfo.getNumberLst() != null) {
//                for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
//                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(numberInfo.getNumberNoCountryCode())) {
//                        return contactsInfo;
//                    }
//                }
//            }
//        }
//        List<ContactsInfo> localContactsInfos = mContactsLogic.getCacheLocalContactLst();
//        for (ContactsInfo contactsInfo : localContactsInfos) {
//            if (isMatch) {
//                break;
//            }
//            if (contactsInfo.getNumberLst() != null) {
//                for (NumberInfo numberInfo : contactsInfo.getNumberLst()) {
//                    if (CommonUtils.getPhoneNumber(phoneNumber).equals(numberInfo.getNumberNoCountryCode())) {
//                        return contactsInfo;
//                    }
//                }
//            }
//        }
//
//        return null;
//    }

    private void handleStateMessage(Message msg) {
        switch (msg.what) {
//            case BussinessConstants.DialMsgID.CALL_ON_TALKING_MSG_ID:
//                if(Const.deviceType == Const.TYPE_OTHER) {
//                    LogUtil.d(TAG,"VtVideoCallActivity incoming");
//                    Intent intentTalking = new Intent(getContext(), VtVideoCallActivity.class);
//                    intentTalking.putExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, true);
//                    mContext.startActivity(intentTalking);
//                } else{
//                    LogUtil.d(TAG,"VtVideoCallActivity incoming");
//                    Intent intentTalking = new Intent(getContext(), StbVideoCallActivity.class);
//                    intentTalking.putExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, true);
//                    mContext.startActivity(intentTalking);
//                }
//                dismiss();
//                break;
//            case BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID:
//                if (msg.obj != null) {
//                    CallSession session = (CallSession) msg.obj;
//                    if (mCallSession != null && mCallSession.equals(session)) {
//                        mVoipLogic.dealOnClosed(mCallSession, true, false, 0);
//                        closed = true;
//                        if (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_TEMPORARILY_UNAVAILABLE) {
//                            showToast(R.string.sip_temporarily_unavailable, Toast.LENGTH_SHORT, null);
//                        }
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                VideoInComingDialog.this.dismiss();
//                            }
//                        }, 3000);
//                    } else if (session != null && session.getType() == CallSession.TYPE_VIDEO_INCOMING) {
//                        mVoipLogic.dealOnClosed(session, true, false, 0);
//                    }
//                }
            default:
                break;
        }
    }

    protected void showToast(int resId, int duration, MyToast.Position pos) {
        myToast.showToast(resId, duration, pos);
    }

    @Override
    public void dismiss() {
        ((ILogic)this.mVoipLogic).removeHandler(mHandler);
        super.dismiss();
    }

    public static void show(Activity activity)
    {
        RegistingDialog videoInComingDialog = new RegistingDialog(activity, R.style.CalendarDialog);
        Window window = videoInComingDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        videoInComingDialog.setCancelable(false);
        videoInComingDialog.show();
    }
}
