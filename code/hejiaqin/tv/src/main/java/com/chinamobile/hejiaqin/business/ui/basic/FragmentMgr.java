package com.chinamobile.hejiaqin.business.ui.basic;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.customer.framework.component.log.Logger;
import com.customer.framework.ui.BaseFragment;
import com.customer.framework.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by yupeng on 8/9/16.
 */
public class FragmentMgr {
    private static final String TAG = "FragmentMgr";
    private static FragmentMgr instance =
            new FragmentMgr();

    int leftContainerResId;
    FragmentManager mFragmentManager;
    Map fragmentStackMap = new HashMap();
    Map focusedViewBackStack = new HashMap();
    BaseFragment curLeftShowFragment;

    private FragmentMgr() {
        fragmentStackMap.put(0, new Stack<BaseFragment>());
        fragmentStackMap.put(1, new Stack<BaseFragment>());
        fragmentStackMap.put(2, new Stack<BaseFragment>());
        fragmentStackMap.put(3, new Stack<BaseFragment>());
        focusedViewBackStack.put(0, new Stack<View>());
        focusedViewBackStack.put(1, new Stack<View>());
        focusedViewBackStack.put(2, new Stack<View>());
        focusedViewBackStack.put(3, new Stack<View>());
    }

    public static FragmentMgr getInstance() {
        return instance;
    }

    public static void resetFragmentMgr() {
        instance = null;
        instance = new FragmentMgr();
    }

    public void init(FragmentActivity activity, int leftContainerResId) {
        mFragmentManager = activity.getSupportFragmentManager();
        this.leftContainerResId = leftContainerResId;
    }

    public void showRecentFragment(BaseFragment fragment) {
        showFragment(0, fragment);
    }

    public void showContactFragment(BaseFragment fragment) {
        showFragment(1, fragment);
    }


    public void showDialFragment(BaseFragment fragment) {
        showFragment(2, fragment);
    }

    public void showSettingFragment(BaseFragment fragment) {
        showFragment(3, fragment);
    }

    public void showFragment(int index, BaseFragment fragment) {
        showFragment((Stack) fragmentStackMap.get(index), (Stack) focusedViewBackStack.get(index), fragment);
    }

    private void showFragment(Stack fragmentStack, Stack backStack, BaseFragment fragment) {
        Logger.d(TAG, "showFragment: " + fragment.getClass());
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        BaseFragment curTopFragment = curLeftShowFragment;
        if (null != curTopFragment) {
            LogUtil.d(TAG, "showFragment,  currentTopFragment is " + curTopFragment.getClass());
            fragmentTransaction.hide(curTopFragment);
        }

        if (fragment.isAdded()) {
            LogUtil.d(TAG, "showFragment, fragment added. ");
            fragmentTransaction.show(fragment);
        } else {
            LogUtil.d(TAG, "showFragment, fragment not added. ");
            fragmentTransaction.add(leftContainerResId, fragment);
            addFragment(fragmentStack, fragment);
        }

        LogUtil.i(TAG, "curLeftShowFragment: " + curLeftShowFragment);

        if (curLeftShowFragment != null && curLeftShowFragment.getActivity() != null) {
            View focus = curLeftShowFragment.getActivity().getCurrentFocus();
            if (focus != null && fragmentStack.size() > 1) {
                backStack.push(focus);
            }
        }
        LogUtil.i(TAG, "Current back stack size is: " + backStack.size());
        curLeftShowFragment = fragment;

        fragmentTransaction.commit();
    }

    public BaseFragment getCurLeftShowFragment() {
        return curLeftShowFragment;
    }

    public void finishRecentFragment(BaseFragment fragment) {
        finishFragment(0, fragment);
    }

    public void finishContactFragment(BaseFragment fragment) {
        finishFragment(1, fragment);
    }


    public void finishDialFragment(BaseFragment fragment) {
        finishFragment(2, fragment);
    }

    public void finishSettingFragment(BaseFragment fragment) {
        finishFragment(3, fragment);
    }

    public void finishFragment(int index, BaseFragment fragment) {
        finishFragment((Stack) fragmentStackMap.get(index), fragment);
        if (((Stack) focusedViewBackStack.get(index)).size() > 0) {
            View backView = (View) ((Stack) focusedViewBackStack.get(index)).pop();
            if (backView != null) {
                backView.requestFocus();
            }
        }
        if (isParentFragmentShowingOfCurrentIndex(index)) {
            ((Stack) focusedViewBackStack.get(index)).clear();
        }
    }

    public void showAndFinishAllFragment(int index) {
        Stack<BaseFragment> fragments = (Stack) fragmentStackMap.get(index);
        if (null == fragments || fragments.size() < 1) {
            return;
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        BaseFragment curTopFragment = curLeftShowFragment;
        if (null != curTopFragment) {
            LogUtil.d(TAG, "showFragment,  currentTopFragment is " + curTopFragment.getClass());
            fragmentTransaction.hide(curTopFragment);
        }

        while (fragments.size() > 1) {
            BaseFragment forRemovedFragment = fragments.pop();
            if (null != forRemovedFragment && forRemovedFragment.isAdded()) {
                fragmentTransaction.hide(forRemovedFragment);
                fragmentTransaction.remove(forRemovedFragment);
            }
        }

        BaseFragment lastFragment = fragments.peek();
        fragmentTransaction.show(lastFragment);
        curLeftShowFragment = lastFragment;
        fragmentTransaction.commit();

        if (isParentFragmentShowingOfCurrentIndex(index)) {
            ((Stack) focusedViewBackStack.get(index)).clear();
        }
    }

    public boolean isParentFragmentShowingOfCurrentIndex(int index) {
        Stack<BaseFragment> stack = (Stack<BaseFragment>) fragmentStackMap.get(index);
        return stack.size() <= 1 ? true : false;
    }

    private void finishFragment(Stack fragmentStack, BaseFragment fragment) {
        LogUtil.d(TAG, "finishFragment: " + fragment.getClass());
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
            LogUtil.d(TAG, "finishFragment, show curTopFragment: " + curTopFragment.getClass());
            fragmentTransaction.show(curTopFragment);
            curLeftShowFragment = curTopFragment;
        }
        fragmentTransaction.commit();
    }

    public BaseFragment getTopFragment(int index) {
        return getTopFragment((Stack) fragmentStackMap.get(index));
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
