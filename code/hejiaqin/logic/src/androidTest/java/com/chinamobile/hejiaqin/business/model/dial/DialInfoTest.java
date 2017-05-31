package com.chinamobile.hejiaqin.business.model.dial;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class DialInfoTest extends TestCase {

    public void testGetDialTime() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialTime("123");
        assertEquals("123", dialInfo.getDialTime());
    }

    public void testGetDialDuration() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialDuration("123");
        assertEquals("123", dialInfo.getDialDuration());
    }

    public void testGetType() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setType(DialInfo.Type.in);
        assertEquals(DialInfo.Type.in, dialInfo.getType());
    }

    public void testSetDialTime() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialTime("123");
        assertEquals("123", dialInfo.getDialTime());
    }

    public void testSetDialDuration() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialDuration("123");
        assertEquals("123", dialInfo.getDialDuration());
    }

    public void testGetDialDay() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialDay("123");
        assertEquals("123", dialInfo.getDialDay());
    }

    public void testSetDialDay() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setDialDay("123");
        assertEquals("123", dialInfo.getDialDay());
    }

    public void testSetType() throws Exception {
        DialInfo dialInfo = new DialInfo();
        dialInfo.setType(DialInfo.Type.in);
        assertEquals(DialInfo.Type.in, dialInfo.getType());
    }

    public void testConvertType() throws Exception {
        assertEquals(DialInfo.Type.in, DialInfo.convertType(CallRecord.TYPE_VIDEO_INCOMING));
        assertEquals(DialInfo.Type.missed, DialInfo.convertType(CallRecord.TYPE_VIDEO_MISSING));
        assertEquals(DialInfo.Type.out, DialInfo.convertType(CallRecord.TYPE_VIDEO_OUTGOING));
        assertEquals(DialInfo.Type.reject, DialInfo.convertType(CallRecord.TYPE_VIDEO_REJECT));
    }
}