package com.chinamobile.hejiaqin.business.model.more.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class TestAdaptReqTest extends TestCase {

    public void testToBody() throws Exception {
        TestAdaptReq testAdaptReq = new TestAdaptReq();
        assertNotNull(testAdaptReq.toBody());
    }

    public void testGetVersion() throws Exception {
        TestAdaptReq testAdaptReq = new TestAdaptReq();
        testAdaptReq.setVersion("123");
        assertEquals("123", testAdaptReq.getVersion());
    }

    public void testSetVersion() throws Exception {
        TestAdaptReq testAdaptReq = new TestAdaptReq();
        testAdaptReq.setVersion("123");
        assertEquals("123", testAdaptReq.getVersion());
    }
}