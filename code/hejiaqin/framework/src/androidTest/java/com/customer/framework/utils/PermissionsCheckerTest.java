package com.customer.framework.utils;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class PermissionsCheckerTest extends AndroidTestCase {

    public void testLacksPermissions() throws Exception {
        Context context = getContext();
        assertNotNull(context);
        assertTrue(PermissionsChecker.lacksPermissions(context, ""));
        assertFalse(PermissionsChecker.lacksPermissions(context, "android.permission.INTERNET"));
        assertTrue(PermissionsChecker.lacksPermissions(context, "android.permission.VII"));
    }
}