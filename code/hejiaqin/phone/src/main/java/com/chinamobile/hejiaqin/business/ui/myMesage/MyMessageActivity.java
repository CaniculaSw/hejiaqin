package com.chinamobile.hejiaqin.business.ui.myMesage;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.event.MessageEvent;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageParame;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageResutl;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.myMessageFragment.MessageFragmentAll;
import com.chinamobile.hejiaqin.business.ui.myMessageFragment.MessageFragmentReadNo;
import com.chinamobile.hejiaqin.business.ui.myMessageFragment.MessageFragmentReadYes;
import com.chinamobile.hejiaqin.business.utils.BusProvider;

/**
 * Created by wubg on 2016/4/26.
 */
public class MyMessageActivity extends BasicFragmentActivity {

    HeaderView headerView;
    EditText et_search;
    View iamg_all;
    View iamg_read_yes;
    View iamg_read_no;
    View line_all;
    View line_read_yes;
    View line_read_no;
    FragmentManager mFm;
    BasicFragment[] mFragments = new BasicFragment[3];
    int mCurrentIndex;
    final int mMessageAllIndex = 0;
    final int mMessageReadYesIndex = 1;
    final int mmMessageReadNOIndex = 2;
    TextView[] mTextView = new TextView[3];
    ImageView[] mImageView = new ImageView[3];
    int testValue = 1;

    private ISettingLogic settingLogic;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_ACTION_ID:
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_YES_ACTION_ID:
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_NO_ACTION_ID:
                    AppMessageParame parame = (AppMessageParame) obj;
                    settingLogic.getMessage(actionId, parame);
                    break;
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_SHOW_LOADING_ACTION_ID:
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_YES_SHOW_LOADING_ACTION_ID:
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_NO_SHOW_LOADING_ACTION_ID:
                    showWaitDailog();
                    AppMessageParame parame2 = (AppMessageParame) obj;
                    settingLogic.getMessage(actionId, parame2);
                    break;
                case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_LAST_PAGE_ACTION_ID:
                    //最后一页，不需要加载
                    showToast(R.string.last_page, 1, null);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getUIBusInstance().register(this);
        mFm = getSupportFragmentManager();
        mFragments[0] = new MessageFragmentAll();
        mFragments[0].setActivityListener(listener);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.content, mFragments[0]);
        ft.commit();
        mCurrentIndex = mMessageAllIndex;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getUIBusInstance().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message1;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.my_message);
        headerView.backImageView.setImageResource(R.mipmap.back);
        et_search = (EditText) findViewById(R.id.et_search);
        line_all = findViewById(R.id.line_all);
        line_read_yes = findViewById(R.id.line_read_yes);
        line_read_no = findViewById(R.id.line_read_no);
        iamg_all = findViewById(R.id.iamg_all);
        iamg_read_yes = findViewById(R.id.iamg_read_yes);
        iamg_read_no = findViewById(R.id.iamg_read_no);
        mTextView[0] = (TextView) findViewById(R.id.tv_all);
        mTextView[1] = (TextView) findViewById(R.id.tv_read_yes);
        mTextView[2] = (TextView) findViewById(R.id.tv_read_no);
        mImageView[0] = (ImageView) findViewById(R.id.iamg_all);
        mImageView[1] = (ImageView) findViewById(R.id.iamg_read_yes);
        mImageView[2] = (ImageView) findViewById(R.id.iamg_read_no);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        dismissWaitDailog();
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.DEL_MESSAGE_SUCCESS_MSG_ID:
                //删除消息成功
                showToast(R.string.sucess_to_delete,1,null);
                //删除未读或者已读，全部消息也要跟着刷新
                for (int i=0;i<mFragments.length;i++){
                    mFragments[i].recieveMsg(msg);
                }
                setUpDataCount();
                break;
            case BussinessConstants.SettingMsgID.DEL_MESSAGE_FAIL_MSG_ID:
                //删除消息失败
                showToast(R.string.fail_to_delete,1,null);
                break;
            default:
                try {
                    AppMessageResutl resutl = (AppMessageResutl) msg.obj;
                    switch (resutl.getActionId()) {
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_ACTION_ID:
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_SHOW_LOADING_ACTION_ID:
                            //全部消息Fragment
                            mFragments[0].recieveMsg(msg);
                            break;
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_YES_ACTION_ID:
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_YES_SHOW_LOADING_ACTION_ID:
                            //已读消息Fragment
                            mFragments[1].recieveMsg(msg);
                            break;
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_NO_ACTION_ID:
                        case BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_READ_NO_SHOW_LOADING_ACTION_ID:
                            //未读消息Fragment
                            mFragments[2].recieveMsg(msg);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setUpDataCount();
                break;
        }
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();   //回退
            }
        });

        line_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(mMessageAllIndex);
            }
        });
        line_read_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(mMessageReadYesIndex);
            }
        });
        line_read_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(mmMessageReadNOIndex);
            }
        });
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    private void switchFragment(int toIndex) {
        if (mCurrentIndex == toIndex) {
            return;
        }
        FragmentTransaction ft = mFm.beginTransaction();
        ft.hide(mFragments[mCurrentIndex]);
        if (mFragments[toIndex] != null) {
            if (mFragments[toIndex].isAdded()) {
                ft.show(mFragments[toIndex]);
            } else {
                ft.add(R.id.content, mFragments[toIndex]);
            }
        } else {
            switch (toIndex) {
                case mMessageAllIndex:
                    mFragments[toIndex] = new MessageFragmentAll();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mMessageReadYesIndex:
                    mFragments[toIndex] = new MessageFragmentReadYes();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mmMessageReadNOIndex:
                    mFragments[toIndex] = new MessageFragmentReadNo();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
//                case mSettingIndex:
//                    mFragments[toIndex] = new SettingFragment();
//                    mFragments[toIndex].setActivityListener(listener);
//                    break;
            }
            ft.add(R.id.content, mFragments[toIndex]);
        }
        Log.d("MainFragmentActivity", "commit:" + mFragments[toIndex].getClass());
        ft.commit();
        mCurrentIndex = toIndex;
        for (int i = 0; i < mTextView.length; i++) {
            if (i == mCurrentIndex) {
//                BitmapDrawable bd = (BitmapDrawable) drawable;
//                    Bitmap bitmap = bd.getBitmap();
                mImageView[i].setImageResource(R.mipmap.bottom_line);
                mTextView[i].setTextColor(getResources().getColor(R.color.white));
            } else {
                mImageView[i].setImageDrawable(null);
                mTextView[i].setTextColor(getResources().getColor(R.color.navigation_unselected));
            }
        }

        setUpDataCount();
    }

    /**
     * 监听删除消息
     */
    @Subscribe
    public void copyToDel(MessageEvent event) {
        if (event != null && event.getMessageInfo() != null) {
            if (event.getType() == 2) {
                //删除消息
                showWaitDailog();
                settingLogic.delMessage(event.getMessageInfo().getId()+"");
            }
        }
    }

    private void setUpDataCount(){

        int count = 0;
        if(mCurrentIndex == mMessageAllIndex) {
            if (mFragments[0] != null) {
                MessageFragmentAll fragmentAll = (MessageFragmentAll) mFragments[0];
                count = fragmentAll.getDataCount();
            }
        }

        if(mCurrentIndex == mMessageReadYesIndex) {
            if (mFragments[1] != null) {
                MessageFragmentReadYes fragmentReadYes = (MessageFragmentReadYes) mFragments[1];
                count = fragmentReadYes.getDataCount();
            }
        }

        if(mCurrentIndex == mmMessageReadNOIndex) {
            if (mFragments[2] != null) {
                MessageFragmentReadNo fragmentReadNo = (MessageFragmentReadNo) mFragments[2];
                count = fragmentReadNo.getDataCount();
            }
        }

        et_search.setHint("共" + count +"条信息");
    }

}
