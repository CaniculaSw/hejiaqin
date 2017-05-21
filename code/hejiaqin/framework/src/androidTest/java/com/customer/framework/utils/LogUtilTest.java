package com.customer.framework.utils;

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class LogUtilTest extends AndroidTestCase {

    public void testSetContext() throws Exception {
        Context context = getContext();
        assertNotNull(context);
        LogUtil.setContext(context);
    }

    public void testSetLogCommonDir() throws Exception {
        Context context = getContext();
        assertNotNull(context);
        LogUtil.setLogCommonDir(context.getCacheDir().getAbsolutePath());
    }

    public void testSetLogLevel() throws Exception {
        LogUtil.setLogLevel(LogUtil.DEBUG);
    }

    public void testV() throws Exception {
        LogUtil.v("", "testV");
    }

    public void testV1() throws Exception {
        LogUtil.v("", "testV", new Throwable("aaa"));
    }

    public void testV2() throws Exception {
        LogUtil.v("", new Throwable("aaa"));
    }

    public void testD() throws Exception {
        LogUtil.d("", "testD");
    }

    public void testD1() throws Exception {
        LogUtil.d("", "testD", new Throwable("aaa"));
    }

    public void testD2() throws Exception {
        LogUtil.d("", new Throwable("aaa"));
    }

    public void testI() throws Exception {
        LogUtil.i("", "testI");
    }

    public void testI1() throws Exception {
        LogUtil.i("", "testI1", new Throwable("aaa"));
    }

    public void testI2() throws Exception {
        LogUtil.i("", new Throwable("aaa"));
    }

    public void testW() throws Exception {
        LogUtil.w("", "test");
    }

    public void testW1() throws Exception {
        LogUtil.w("", "test", new Throwable("aaa"));
    }

    public void testW2() throws Exception {
        LogUtil.w("", new Throwable("aaa"));
    }

    public void testE() throws Exception {
        LogUtil.e("", "test");
    }

    public void testE1() throws Exception {
        LogUtil.e("", "test", new Throwable("aaa"));
    }

    public void testE2() throws Exception {
        LogUtil.e("", new Throwable("aaa"));
    }
}