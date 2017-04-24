package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.dial.StbVideoCallActivity;
import com.chinamobile.hejiaqin.business.ui.dial.VtVideoCallActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by  on 2016/6/25.
 */
public class VideoInComingDialog extends BasicActivity {

    public static final String TAG = VideoInComingDialog.class.getSimpleName();

    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private TextView mCallerNumberTv;
    private RelativeLayout mOperationLayout;
    private LinearLayout mAutoRejectLayout;

    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;

    private boolean closed;
    private boolean onClickAnswer;
    //通话会话对象
    private CallSession mCallSession = null;
    private Handler handler = new Handler();

    @Override
    protected void initLogics() {
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
        mContactsLogic = (IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popwindow_video_incoming;
    }

    @Override
    protected void initView() {
        mCallerIv = (CircleImageView) findViewById(R.id.caller_iv);
        mCallerNameTv = (TextView) findViewById(R.id.caller_name_tv);
        mCallerNumberTv = (TextView) findViewById(R.id.caller_number_tv);
        long sessionId = getIntent().getLongExtra(
                BussinessConstants.Dial.INTENT_INCOMING_SESSION_ID, CallSession.INVALID_ID);
        mCallSession = CallApi.getCallSessionById(sessionId);
        if (mCallSession == null) {
            finish();
            return;
        }
        mOperationLayout = (RelativeLayout) findViewById(R.id.call_operation_layout);
        findViewById(R.id.answer_call_layout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (closed) {
                    LogUtil.w(TAG, "is closed");
                    return;
                }
                if (onClickAnswer) {
                    return;
                }
                mVoipLogic.answerVideo(mCallSession);
                onClickAnswer = true;
            }
        });
        findViewById(R.id.reject_call_layout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mVoipLogic.hangup(mCallSession, true, false, 0);
                finish();
            }
        });
        mAutoRejectLayout = (LinearLayout) findViewById(R.id.auto_reject_call_layout);
        mAutoRejectLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mVoipLogic.hangup(mCallSession, true, false, 0);
                finish();
            }
        });
        showIncoming();
        if (CommonUtils.isAutoAnswer(getApplicationContext(), mCallSession.getPeer().getNumber())) {
            ((TextView) findViewById(R.id.call_status_tv)).setText(R.string.auto_answer_status);
            mOperationLayout.setVisibility(View.GONE);
            mAutoRejectLayout.setVisibility(View.VISIBLE);
            mVoipLogic.answerVideo(mCallSession);
        }
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {

    }

    private void showIncoming() {
        String incomingNumber = CommonUtils.getTvNumberWithZero(CommonUtils
                .getPhoneNumber(mCallSession.getPeer().getNumber()));
        // 查询姓名和头像信息
        ContactsInfo info = searchContactInfo(incomingNumber);
        if (info != null) {
            if (!StringUtil.isNullOrEmpty(info.getName())) {
                mCallerNameTv.setText(info.getName());
                mCallerNameTv.setVisibility(View.VISIBLE);
            }
            mCallerNumberTv.setText(incomingNumber);
            if (!StringUtil.isNullOrEmpty(info.getPhotoSm())) {
                Picasso.with(getApplicationContext()).load(info.getPhotoSm())
                        .placeholder(R.drawable.contact_photo_default)
                        .error(R.drawable.contact_photo_default).into(mCallerIv);
            }
        } else {
            mCallerNumberTv.setText(incomingNumber);
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

    /***/
    public void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.DialMsgID.CALL_INCOMING_FINISH_CLOSING_MSG_ID:
                if (!closed) {
                    mVoipLogic.dealOnClosed(mCallSession, true, false, 0);
                    closed = true;
                }
                finish();
                break;
            case BussinessConstants.DialMsgID.CALL_ON_TALKING_MSG_ID:
                if (Const.getDeviceType() == Const.TYPE_OTHER) {
                    LogUtil.d(TAG, "VtVideoCallActivity incoming");
                    Intent intentTalking = new Intent(VideoInComingDialog.this,
                            VtVideoCallActivity.class);
                    intentTalking.putExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, true);
                    startActivity(intentTalking);
                } else {
                    LogUtil.d(TAG, "VtVideoCallActivity incoming");
                    Intent intentTalking = new Intent(VideoInComingDialog.this,
                            StbVideoCallActivity.class);
                    intentTalking.putExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, true);
                    startActivity(intentTalking);
                }
                finish();
                break;
            case BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID:
                if (msg.obj != null) {
                    CallSession session = (CallSession) msg.obj;
                    if (mCallSession != null && mCallSession.equals(session)) {
                        mVoipLogic.dealOnClosed(mCallSession, true, false, 0);
                        closed = true;
                        if (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_TEMPORARILY_UNAVAILABLE) {
                            showToast(R.string.sip_temporarily_unavailable, Toast.LENGTH_SHORT,
                                    null);
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
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

}
