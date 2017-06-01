package com.chinamobile.hejiaqin.business.utils;

import android.test.AndroidTestCase;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class DirUtilTest extends AndroidTestCase {

    public void testGetExternalCacheDir() throws Exception {
        DirUtil.getExternalCacheDir(getContext());
    }

    public void testGetExternalFileDir() throws Exception {
        DirUtil.getExternalFileDir(getContext());
    }
}