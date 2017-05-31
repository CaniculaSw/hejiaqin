package com.chinamobile.hejiaqin.business.model.dial;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class RecordSearchUnitTest extends TestCase {

    public void testSetPeerNumber() throws Exception {
        RecordSearchUnit recordSearchUnit = new RecordSearchUnit();
        recordSearchUnit.setPeerNumber("123");
    }

    public void testGetNumberText() throws Exception {
        RecordSearchUnit recordSearchUnit = new RecordSearchUnit();
        recordSearchUnit.setPeerNumber("123");
        try {
            recordSearchUnit.getNumberText();
        } catch (Exception e) {

        }
    }

    public void testSearch() throws Exception {
        RecordSearchUnit recordSearchUnit = new RecordSearchUnit();
        recordSearchUnit.search("");
        recordSearchUnit.setPeerNumber("123");
        recordSearchUnit.search("");

        recordSearchUnit.search("123");
        recordSearchUnit.search("1234");
    }
}