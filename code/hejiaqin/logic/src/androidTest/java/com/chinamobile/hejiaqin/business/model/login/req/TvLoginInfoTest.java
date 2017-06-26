package com.chinamobile.hejiaqin.business.model.login.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class TvLoginInfoTest extends TestCase {

    public void testGetTvToken() throws Exception {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setCode("123");
        assertEquals("123", tvLoginInfo.getCode());
    }

    public void testSetTvToken() throws Exception {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setCode("123");
        assertEquals("123", tvLoginInfo.getCode());
    }

    public void testGetTvId() throws Exception {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setTvId("123");
        assertEquals("123", tvLoginInfo.getTvId());
    }

    public void testSetTvId() throws Exception {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setTvId("123");
        assertEquals("123", tvLoginInfo.getTvId());
    }

    public void testToBody() throws Exception {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setCode("123");
        tvLoginInfo.setTvId("123");
        assertNotNull(tvLoginInfo.toBody());
    }
}