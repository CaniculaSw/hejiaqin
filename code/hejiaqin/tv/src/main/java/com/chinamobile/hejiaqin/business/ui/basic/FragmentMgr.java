package com.chinamobile.hejiaqin.business.ui.basic;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.customer.framework.component.log.Logger;
import com.customer.framework.ui.BaseFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by yupeng on 8/9/16.
 */
public class FragmentMgr {
    private static final String TAG = "FragmentMgr";
    private static final FragmentMgr instance =
            new FragmentMgr();

    int leftContainerResId, rightContainerResId;
    FragmentManager mFragmentManager;

    Stack<BaseFragment> recentFragmentStack = new Stack<>();

    Stack<BaseFragment> contactFragmentStack = new Stack<>();

    Stack<BaseFragment> dialFragmentStack = new Stack<>();

    Stack<BaseFragment> settingFragmentStack = new Stack<>();

    Stack<BaseFragment> rightFragmentStack = new Stack<>();

    BaseFragment curLeftShowFragment;

    BaseFragment curRightShowFragment;

    private FragmentMgr() {

    }

    public static FragmentMgr getInstance() {
        return instance;
    }

    public void init(FragmentActivity activity, int leftContainerResId, int rightContainerResId) {
        mFragmentManager = activity.getSupportFragmentManager();
        this.leftContainerResId = leftContainerResId;
        this.rightContainerResId = rightContainerResId;
    }

    public void init(FragmentActivity activity, int leftContainerResId) {
        mFragmentManager = activity.getSupportFragmentManager();
        this.leftContainerResId = leftContainerResId;
    }

    public void showRecentFragment(BaseFragment fragment) {
        showFragment(recentFragmentStack, fragment, true);
    }

    public void showContactFragment(BaseFragment fragment) {
        showFragment(contactFragmentStack, fragment, true);
    }


    public void showDialFragment(BaseFragment fragment) {
        showFragment(dialFragmentStack, fragment, true);
    }

    public void showSettingFragment(BaseFragment fragment) {
        showFragment(settingFragmentStack, fragment, true);
    }

    public void showRightFragment(BaseFragment fragment) {
        showFragment(rightFragmentStack, fragment, false);
    }

    private void showFragment(Stack fragmentStack, BaseFragment fragment, boolean isLeft) {
        Logger.d(TAG, "showFragment: " + fragment.getClass());
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        BaseFragment curTopFragment = isLeft ? curLeftShowFragment : curRightShowFragment;
        if (null != curTopFragment) {
            Logger.d(TAG, "showFragment,  currentTopFragment is " + curTopFragment.getClass());
            fragmentTransaction.hide(curTopFragment);
        }


        if (fragment.isAdded()) {
            Logger.d(TAG, "showFragment, fragment added. ");
            fragmentTransaction.show(fragment);
        } else {
            Logger.d(TAG, "showFragment, fragment not added. ");
            fragmentTransaction.add(isLeft ? leftContainerResId : rightContainerResId, fragment);
            addFragment(fragmentStack, fragment);
        }

        if (isLeft) {
            curLeftShowFragment = fragment;
        } else {
            curRightShowFragment = fragment;
        }
        fragmentTransaction.commit();
    }

    public void finishRecentFragment(BaseFragment fragment) {
        finishFragment(recentFragmentStack, fragment);
    }

    public void finishContactFragment(BaseFragment fragment) {
        finishFragment(contactFragmentStack, fragment);
    }


    public void finishDialFragment(BaseFragment fragment) {
        finishFragment(dialFragmentStack, fragment);
    }

    public void finishSettingFragment(BaseFragment fragment) {
        finishFragment(settingFragmentStack, fragment);
    }

    public void finishRightFragment(BaseFragment fragment) {
        finishFragment(rightFragmentStack, fragment);
    }


    private void finishFragment(Stack fragmentStack, BaseFragment fragment) {
        Logger.d(TAG, "finishFragment: " + fragment.getClass());
        synchronized (fragmentStack) {
            fragmentStack.remove(fragment);
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null != fragment && fragment.isAdded()) {
            fragmentTransaction.hide(fragment);
            fragmentTransaction.remove(fragment);
        }

        BaseFragment curTopFragment = getTopFragment(fragmentStack);
        if (null != curTopFragment) {
            Logger.d(TAG, "finishFragment, show curTopFragment: " + curTopFragment.getClass());
            fragmentTransaction.show(curTopFragment);
        }
        fragmentTransaction.commit();
    }


    private BaseFragment getTopFragment(Stack<BaseFragment> fragmentStack) {
        synchronized (fragmentStack) {
            if (fragmentStack.empty()) {
                return null;
            }
            return fragmentStack.peek();
        }
    }

    private void addFragment(Stack<BaseFragment> fragmentStack, BaseFragment fragment) {
        synchronized (fragmentStack) {
            fragmentStack.push(fragment);
        }
    }

}
