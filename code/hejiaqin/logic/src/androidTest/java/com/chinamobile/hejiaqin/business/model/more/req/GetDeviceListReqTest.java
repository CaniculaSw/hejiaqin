package com.chinamobile.hejiaqin.business.model.more.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class GetDeviceListReqTest extends TestCase {

    public void testToBody() throws Exception {
        GetDeviceListReq getDeviceListReq = new GetDeviceListReq();
        assertNotNull(getDeviceListReq.toBody());
    }
}