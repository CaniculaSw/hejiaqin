package com.chinamobile.hejiaqin.business.model.login.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class FeedBackReqTest extends TestCase {

    public void testGetContent() throws Exception {
        FeedBackReq feedBackReq = new FeedBackReq();
        feedBackReq.setContent("123");
        assertEquals("123", feedBackReq.getContent());
    }

    public void testSetContent() throws Exception {
        FeedBackReq feedBackReq = new FeedBackReq();
        feedBackReq.setContent("123");
        assertEquals("123", feedBackReq.getContent());
    }

    public void testToBody() throws Exception {
        FeedBackReq feedBackReq = new FeedBackReq();
        feedBackReq.setContent("123");
        assertNotNull(feedBackReq.toBody());
    }
}