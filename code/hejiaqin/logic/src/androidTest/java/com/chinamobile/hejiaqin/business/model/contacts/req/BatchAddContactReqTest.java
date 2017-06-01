package com.chinamobile.hejiaqin.business.model.contacts.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class BatchAddContactReqTest extends TestCase {

    public void testToBody() throws Exception {
        BatchAddContactReq batchAddContactReq = new BatchAddContactReq();
        assertNotNull(batchAddContactReq.toBody());
    }

    public void testGetContactJson() throws Exception {
        BatchAddContactReq batchAddContactReq = new BatchAddContactReq();
        batchAddContactReq.setContactJson("123");
        assertNotNull(batchAddContactReq.getContactJson());
    }

    public void testSetContactJson() throws Exception {
        BatchAddContactReq batchAddContactReq = new BatchAddContactReq();
        batchAddContactReq.setContactJson("123");
        assertNotNull(batchAddContactReq.getContactJson());
    }
}