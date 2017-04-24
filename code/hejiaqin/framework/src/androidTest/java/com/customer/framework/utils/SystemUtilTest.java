package com.customer.framework.utils;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class SystemUtilTest extends AndroidTestCase {

    public void testGetPackageVersionName() {
        Context context = getContext();
        assertNotNull(context);

        assertNotNull(SystemUtil.getPackageVersionName(context));
    }

    public void testGetPackageInfo() {
        Context context = getContext();
        assertNotNull(context);

        assertNotNull(SystemUtil.getPackageInfo(context));
    }
}
