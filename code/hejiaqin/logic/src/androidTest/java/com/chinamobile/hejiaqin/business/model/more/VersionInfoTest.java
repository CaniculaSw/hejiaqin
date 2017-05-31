package com.chinamobile.hejiaqin.business.model.more;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class VersionInfoTest extends TestCase {

    public void testGetVersionCode() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersionCode("123456");
        assertEquals(versionInfo.getVersionCode(), "123456");
    }

    public void testSetVersionCode() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersionCode("123456");
        assertEquals(versionInfo.getVersionCode(), "123456");
    }

    public void testGetVersionName() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersionName("123456");
        assertEquals(versionInfo.getVersionName(), "123456");
    }

    public void testSetVersionName() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersionName("123456");
        assertEquals(versionInfo.getVersionName(), "123456");
    }

    public void testGetUrl() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setUrl("123456");
        assertEquals(versionInfo.getUrl(), "123456");
    }

    public void testSetUrl() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setUrl("123456");
        assertEquals(versionInfo.getUrl(), "123456");
    }

    public void testGetTime() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setTime("123456");
        assertEquals(versionInfo.getTime(), "123456");
    }

    public void testSetTime() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setTime("123456");
        assertEquals(versionInfo.getTime(), "123456");
    }

    public void testGetByForce() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setByForce(1);
        assertEquals(versionInfo.getByForce(), 1);
    }

    public void testSetByForce() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setByForce(1);
        assertEquals(versionInfo.getByForce(), 1);
    }

    public void testGetForceVersionCode() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setForceVersionCode("1");
        assertEquals(versionInfo.getForceVersionCode(), "1");
    }

    public void testSetForceVersionCode() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setForceVersionCode("1");
        assertEquals(versionInfo.getForceVersionCode(), "1");
    }

    public void testIsNew() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setNew(true);
        assertEquals(versionInfo.isNew(), true);
    }

    public void testSetNew() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setNew(true);
        assertEquals(versionInfo.isNew(), true);
    }
}