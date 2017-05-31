package com.chinamobile.hejiaqin.business.model;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class FailResponseTest extends TestCase {

    public void testGetCode() throws Exception {
        FailResponse failResponse = new FailResponse();
        failResponse.setCode("123");
        assertEquals(failResponse.getCode(), "123");
    }

    public void testSetCode() throws Exception {
        FailResponse failResponse = new FailResponse();
        failResponse.setCode("123");
        assertEquals(failResponse.getCode(), "123");
    }

    public void testGetMsg() throws Exception {
        FailResponse failResponse = new FailResponse();
        failResponse.setMsg("123");
        assertEquals(failResponse.getMsg(), "123");
    }

    public void testSetMsg() throws Exception {
        FailResponse failResponse = new FailResponse();
        failResponse.setMsg("123");
        assertEquals(failResponse.getMsg(), "123");
    }
}