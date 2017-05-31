package com.chinamobile.hejiaqin.business.model.login.req;

import android.os.Parcel;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class PasswordInfoTest extends TestCase {

    public void testGetResetToken() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setResetToken("123");
        assertEquals("123", passwordInfo.getResetToken());
    }

    public void testSetResetToken() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setResetToken("123");
        assertEquals("123", passwordInfo.getResetToken());
    }

    public void testGetPassword() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setPassword("123");
        assertEquals("123", passwordInfo.getPassword());
    }

    public void testSetPassword() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setPassword("123");
        assertEquals("123", passwordInfo.getPassword());
    }

    public void testToBody() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setResetToken("123");
        passwordInfo.setPassword("123");
        assertNotNull(passwordInfo.toBody());
    }

    public void testDescribeContents() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        assertEquals(0, passwordInfo.describeContents());
    }

    public void testWriteToParcel() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.writeToParcel(Parcel.obtain(), 0);
    }
}