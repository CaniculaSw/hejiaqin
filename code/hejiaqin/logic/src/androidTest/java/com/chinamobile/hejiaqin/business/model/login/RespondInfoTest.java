package com.chinamobile.hejiaqin.business.model.login;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class RespondInfoTest extends TestCase {

    public void testGetCode() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setCode("123");
        assertEquals("123", respondInfo.getCode());
    }

    public void testSetCode() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setCode("123");
        assertEquals("123", respondInfo.getCode());
    }

    public void testGetMsg() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setMsg("123");
        assertEquals("123", respondInfo.getMsg());
    }

    public void testSetMsg() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setMsg("123");
        assertEquals("123", respondInfo.getMsg());
    }

    public void testGetData() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setData("123");
        assertEquals("123", respondInfo.getData());
    }

    public void testSetData() throws Exception {
        RespondInfo respondInfo = new RespondInfo();
        respondInfo.setData("123");
        assertEquals("123", respondInfo.getData());
    }
}