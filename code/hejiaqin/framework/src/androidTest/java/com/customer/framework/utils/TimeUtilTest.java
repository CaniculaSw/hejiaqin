package com.customer.framework.utils;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class TimeUtilTest extends TestCase {

    public void testSecToTime() throws Exception {
       assertNotNull(TimeUtil.secToTime(123));
    }

    public void testUnitFormat() throws Exception {
        assertNotNull(TimeUtil.unitFormat(123));
    }

    public void testDisToTime() throws Exception {
        assertNotNull(TimeUtil.disToTime(123));
    }

    public void testDisToTimeWithLanuage() throws Exception {
        assertNotNull(TimeUtil.disToTimeWithLanuage(123));
    }
}