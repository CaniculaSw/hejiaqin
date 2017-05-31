package com.chinamobile.hejiaqin.business.model.login.req;

import android.os.Parcel;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class RegisterSecondStepInfoTest extends TestCase {

    public void testToBody() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        assertNotNull(registerSecondStepInfo.toBody());
    }

    public void testGetPhone() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPhone("123");
        assertEquals("123", registerSecondStepInfo.getPhone());
    }

    public void testSetPhone() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPhone("123");
        assertEquals("123", registerSecondStepInfo.getPhone());
    }

    public void testGetCode() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setCode("123");
        assertEquals("123", registerSecondStepInfo.getCode());
    }

    public void testSetCode() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setCode("123");
        assertEquals("123", registerSecondStepInfo.getCode());
    }

    public void testGetPwd() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPwd("123");
        assertEquals("123", registerSecondStepInfo.getPwd());
    }

    public void testSetPwd() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPwd("123");
        assertEquals("123", registerSecondStepInfo.getPwd());
    }

    public void testDescribeContents() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        assertEquals(0, registerSecondStepInfo.describeContents());
    }

    public void testWriteToParcel() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.writeToParcel(Parcel.obtain(), 0);
    }
}