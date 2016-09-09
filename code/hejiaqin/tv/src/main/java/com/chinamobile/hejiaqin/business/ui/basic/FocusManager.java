package com.chinamobile.hejiaqin.business.ui.basic;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yupeng on 8/9/16.
 */
public class FocusManager {
    private static final FocusManager instance =
            new FocusManager();

    private Map<String, View> leftFragFocusViews = new HashMap<>();

    private FocusManager() {

    }

    public static FocusManager getInstance() {
        return instance;
    }

    public void addFocusViewInLeftFrag(String fragName, View view) {
        leftFragFocusViews.put(fragName, view);
    }

    public View getFocusViewInLeftFrag(String fragName) {
        return leftFragFocusViews.get(fragName);
    }

    public boolean isExistInLeftFrag(String fragName) {
        return leftFragFocusViews.containsKey(fragName);
    }

    public void requestFocus(View view) {
        view.setFocusable(true);
        view.requestFocus();
    }
}
