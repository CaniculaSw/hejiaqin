package com.chinamobile.hejiaqin.business.model.login.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class UpdatePhotoReqTest extends TestCase {

    public void testGetFile() throws Exception {
        UpdatePhotoReq updatePhotoReq = new UpdatePhotoReq();
        updatePhotoReq.setFile("123");
        assertEquals("123", updatePhotoReq.getFile());
    }

    public void testSetFile() throws Exception {
        UpdatePhotoReq updatePhotoReq = new UpdatePhotoReq();
        updatePhotoReq.setFile("123");
        assertEquals("123", updatePhotoReq.getFile());
    }

    public void testToBody() throws Exception {
        UpdatePhotoReq updatePhotoReq = new UpdatePhotoReq();
        assertNotNull(updatePhotoReq.toBody());
    }
}