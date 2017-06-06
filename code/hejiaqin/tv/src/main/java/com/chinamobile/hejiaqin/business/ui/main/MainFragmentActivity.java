package com.chinamobile.hejiaqin.business.ui.main;

import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.ui.BaseFragment;
import com.customer.framework.utils.LogUtil;

/**
 * desc: main
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/22.
 */
public class MainFragmentActivity extends BasicFragmentActivity {

    private static final int DIAL_STATUS_NORMAL = 1;

    private static final int DIAL_STATUS_SHOW_KEYBORD = 2;

    private static final int DIAL_STATUS_CALL = 3;

    View mNavigatorLay;

    BasicFragment[] mLeftFragments = new BasicFragment[4];

    //    TextView[] mTextViews = new TextView[3];

    View[] mMenuViews = new View[4];

    int mCurrentIndex;
    final int mRecentIndex = 0;

    final int mContactsIndex = 1;

    final int mDialIndex = 2;

    final int mSettingIndex = 3;

    private boolean mDialSeleted = false;

    private int mDialStatus = DIAL_STATUS_NORMAL;

    private long exitTime = 0;

    private IVoipLogic mVoipLogic;

    private ISettingLogic settingLogic;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID:
                    Intent intent = new Intent(MainFragmentActivity.this, LoginActivity.class);
                    MainFragmentActivity.this.startActivity(intent);
                    MainFragmentActivity.this.finishAllActivity(LoginActivity.class.getName());
                    break;
                // 显示导航栏
                case BussinessConstants.FragmentActionId.CONTACT_FRAGMENT_SHOW_NAVIGATOR_ACTION_ID:
                    mNavigatorLay.setVisibility(View.VISIBLE);
                    break;
                // 隐藏导航栏
                case BussinessConstants.FragmentActionId.CONTACT_FRAGMENT_HIDE_NAVIGATOR_ACTION_ID:
                    mNavigatorLay.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity_main;
    }

    @Override
    protected void initView() {
        mNavigatorLay = findViewById(R.id.main_nav);

        mMenuViews[mRecentIndex] = findViewById(R.id.recent_layout);
        mMenuViews[mRecentIndex].setBackgroundResource(R.drawable.nav_btn_bg_normal);
        mMenuViews[mContactsIndex] = findViewById(R.id.contact_layout);
        mMenuViews[mContactsIndex].setBackgroundResource(R.drawable.nav_btn_bg_normal);
        mMenuViews[mDialIndex] = findViewById(R.id.dial_layout);
        mMenuViews[mDialIndex].setBackgroundResource(R.drawable.nav_btn_bg_normal);
        mMenuViews[mSettingIndex] = findViewById(R.id.more_layout);
        mMenuViews[mSettingIndex].setBackgroundResource(R.drawable.nav_btn_bg_normal);

        FragmentMgr.getInstance().init(this, R.id.content_left);
        mLeftFragments[mRecentIndex] = new CallRecordFragment();
        mLeftFragments[mRecentIndex].setActivityListener(listener);
        FragmentMgr.getInstance().showRecentFragment(mLeftFragments[mRecentIndex]);

        mCurrentIndex = mRecentIndex;
        mMenuViews[mCurrentIndex].setBackgroundColor(getResources().getColor(R.color.transparent));
        FocusManager.getInstance().requestFocus(mMenuViews[mCurrentIndex]);

    }

    @Override
    protected void initDate() {
        //settingLogic.checkVersion();
        settingLogic.getBindList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVoipLogic.setNeedShowManFragment(false);
        if (mVoipLogic.isNeedVoipLogin()) {
            LogUtil.d(tagString, "autoLogin");
            mVoipLogic.setNotNeedVoipLogin();
            mVoipLogic.autoLogin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVoipLogic.setNeedShowManFragment(true);
    }

    @Override
    protected void initListener() {
        for (int i = 0; i < mMenuViews.length; i++) {
            final int menuIndex = i;
            mMenuViews[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    switchFragment(menuIndex);
                }
            });

            mMenuViews[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    switchFragment(menuIndex);
                }
            });
        }
    }

    private void switchFragment(int toIndex) {
        if (mCurrentIndex == toIndex) {
            return;
        }

        BaseFragment switchFragment = FragmentMgr.getInstance().getTopFragment(toIndex);
        switch (toIndex) {
            case mRecentIndex:
                if (null == switchFragment) {
                    mLeftFragments[toIndex] = new CallRecordFragment();
                    mLeftFragments[toIndex].setActivityListener(listener);
                    FragmentMgr.getInstance().showRecentFragment(mLeftFragments[toIndex]);
                } else {
                    // FragmentMgr.getInstance().showRecentFragment(switchFragment);
                    FragmentMgr.getInstance().showAndFinishAllFragment(toIndex);
                }
                break;
            case mContactsIndex:
                if (null == switchFragment) {
                    mLeftFragments[toIndex] = new ContactListFragment();
                    mLeftFragments[toIndex].setActivityListener(listener);
                    FragmentMgr.getInstance().showContactFragment(mLeftFragments[toIndex]);
                } else {
                    // FragmentMgr.getInstance().showContactFragment(switchFragment);
                    FragmentMgr.getInstance().showAndFinishAllFragment(toIndex);
                }
                break;
            case mDialIndex:
                if (null == switchFragment) {
                    mLeftFragments[toIndex] = new DialFragment();
                    mLeftFragments[toIndex].setActivityListener(listener);
                    FragmentMgr.getInstance().showDialFragment(mLeftFragments[toIndex]);
                } else {
                    // FragmentMgr.getInstance().showDialFragment(switchFragment);
                    FragmentMgr.getInstance().showAndFinishAllFragment(toIndex);
                }
                break;
            case mSettingIndex:
                if (null == switchFragment) {
                    mLeftFragments[toIndex] = new SettingFragment();
                    mLeftFragments[toIndex].setActivityListener(listener);
                    FragmentMgr.getInstance().showSettingFragment(mLeftFragments[toIndex]);
                } else {
                    // FragmentMgr.getInstance().showSettingFragment(switchFragment);
                    FragmentMgr.getInstance().showAndFinishAllFragment(toIndex);
                }
                break;
            default:
                break;
        }
        //        LogUtil.i("MainFragmentActivity","toIndex:"+toIndex);
        //        LogUtil.d("MainFragmentActivity", "commit:" + mLeftFragments[toIndex].getClass());

        mMenuViews[mCurrentIndex].setBackgroundResource(R.drawable.nav_btn_bg_normal);
        mMenuViews[toIndex].setBackgroundColor(getResources().getColor(R.color.transparent));

        mCurrentIndex = toIndex;
        FocusManager.getInstance().requestFocus(mMenuViews[mCurrentIndex]);
    }

    @Override
    protected void initLogics() {
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
        settingLogic = (ISettingLogic) super.getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        dismissWaitDailog();
        switch (msg.what) {
            default:
                break;
        }
    }

    @Override
    public void doNetWorkConnect() {
        //通知Fragment网络已经连接
        if (mLeftFragments[mContactsIndex] != null) {
            mLeftFragments[mContactsIndex].doNetWorkConnect();
        }
    }

    @Override
    public void onBackPressed() {
        if (!FragmentMgr.getInstance().isParentFragmentShowingOfCurrentIndex(mCurrentIndex)) {
            BaseFragment fragment = FragmentMgr.getInstance().getCurLeftShowFragment();
            FragmentMgr.getInstance().finishFragment(mCurrentIndex, fragment);
            return;
        } else if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(R.string.quit_app_toast_msg, Toast.LENGTH_SHORT, null);
            exitTime = System.currentTimeMillis();
            return;
        }

        super.moveTaskToBack(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View focused = getCurrentFocus();
        LogUtil.d(tagString, "focused: " + focused);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                LogUtil.d(tagString, "KeyEvent.KEYCODE_DPAD_LEFT");
                View nextLeftFocuse = focused.focusSearch(View.FOCUS_LEFT);
                //if (nextFocuse != null && nextFocuse.focusSearch(View.FOCUS_LEFT) != null) {
                if (nextLeftFocuse != null && !isViewInMenus(nextLeftFocuse.getId())) {
                    nextLeftFocuse.requestFocus();
                } else {
                    FocusManager.getInstance().requestFocus(mMenuViews[mCurrentIndex]);
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                LogUtil.d(tagString, "KeyEvent.KEYCODE_DPAD_RIGHT");
                BaseFragment topFragment = FragmentMgr.getInstance().getTopFragment(mCurrentIndex);
                View leftFocusView = ((BasicFragment) topFragment).getFirstFouseView();
                if (null != leftFocusView && mMenuViews[mCurrentIndex].isFocused()) {
                    FocusManager.getInstance().requestFocus(leftFocusView);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                LogUtil.d(tagString, "KeyEvent.KEYCODE_DPAD_UP");
                if (!isViewInMenus(focused.getId())) {
                    View nextUpFocuse = focused.focusSearch(View.FOCUS_UP);
                    if (null != nextUpFocuse && isViewInMenus(nextUpFocuse.getId())) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                LogUtil.d(tagString, "KeyEvent.KEYCODE_DPAD_DOWN");
                if (!isViewInMenus(focused.getId())) {
                    View nextDownFocuse = focused.focusSearch(View.FOCUS_DOWN);
                    if (null != nextDownFocuse && isViewInMenus(nextDownFocuse.getId())) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
            case KeyEvent.KEYCODE_STAR:
            case KeyEvent.KEYCODE_POUND:
            case KeyEvent.KEYCODE_DEL:
                if (mCurrentIndex == mDialIndex
                        && FragmentMgr.getInstance().isParentFragmentShowingOfCurrentIndex(
                                mCurrentIndex)) {
                    BaseFragment switchFragment = FragmentMgr.getInstance().getTopFragment(
                            mCurrentIndex);
                    if (switchFragment != null) {
                        Message msg = new Message();
                        msg.what = BussinessConstants.FragmentActionId.TV_DAIL_FRAGMENT_KEY_CODE;
                        msg.obj = keyCode;
                        ((BasicFragment) switchFragment).recieveMsg(msg);
                        return true;
                    }
                }
                break;
            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isViewInMenus(int viewId) {
        return mNavigatorLay.findViewById(viewId) != null;
    }
}
