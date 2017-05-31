package com.chinamobile.hejiaqin.business.model.dial;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class DialInfoGroupTest extends TestCase {

    public void testGetGroupName() throws Exception {
        DialInfoGroup dialInfoGroup = new DialInfoGroup();
        dialInfoGroup.setGroupName("123");
        assertEquals("123", dialInfoGroup.getGroupName());
    }

    public void testSetGroupName() throws Exception {
        DialInfoGroup dialInfoGroup = new DialInfoGroup();
        dialInfoGroup.setGroupName("123");
        assertEquals("123", dialInfoGroup.getGroupName());
    }

    public void testGetDialInfoList() throws Exception {
        DialInfoGroup dialInfoGroup = new DialInfoGroup();

        List<DialInfo> dialInfos = new ArrayList<>();
        dialInfoGroup.setDialInfoList(dialInfos);
        assertEquals(dialInfos, dialInfoGroup.getDialInfoList());
    }

    public void testSetDialInfoList() throws Exception {
        DialInfoGroup dialInfoGroup = new DialInfoGroup();

        List<DialInfo> dialInfos = new ArrayList<>();
        dialInfoGroup.setDialInfoList(dialInfos);
        assertEquals(dialInfos, dialInfoGroup.getDialInfoList());
    }
}