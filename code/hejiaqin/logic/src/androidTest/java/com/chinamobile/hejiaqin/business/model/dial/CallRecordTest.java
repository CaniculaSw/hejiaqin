package com.chinamobile.hejiaqin.business.model.dial;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class CallRecordTest extends TestCase {

    public void testGetId() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setId("123");
        assertEquals("123", callRecord.getId());
    }

    public void testSetId() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setId("123");
        assertEquals("123", callRecord.getId());
    }

    public void testGetRecordId() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setRecordId("123");
        assertEquals("123", callRecord.getRecordId());
    }

    public void testSetRecordId() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setRecordId("123");
        assertEquals("123", callRecord.getRecordId());
    }

    public void testGetPeerNumber() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerNumber("123");
        assertEquals("123", callRecord.getPeerNumber());
    }

    public void testSetPeerNumber() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerNumber("123");
        assertEquals("123", callRecord.getPeerNumber());
    }

    public void testGetNoCountryNumber() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setNoCountryNumber("123");
        assertEquals("123", callRecord.getNoCountryNumber());
    }

    public void testSetNoCountryNumber() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setNoCountryNumber("123");
        assertEquals("123", callRecord.getNoCountryNumber());
    }

    public void testGetPeerName() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerName("123");
        assertEquals("123", callRecord.getPeerName());
    }

    public void testSetPeerName() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerName("123");
        assertEquals("123", callRecord.getPeerName());
    }

    public void testGetPeerHeaderImage() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerHeaderImage("123");
        assertEquals("123", callRecord.getPeerHeaderImage());
    }

    public void testSetPeerHeaderImage() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setPeerHeaderImage("123");
        assertEquals("123", callRecord.getPeerHeaderImage());
    }

    public void testGetInfoFlag() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setInfoFlag(1);
        assertEquals(1, callRecord.getInfoFlag());
    }

    public void testSetInfoFlag() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setInfoFlag(1);
        assertEquals(1, callRecord.getInfoFlag());
    }

    public void testGetContactsInfo() throws Exception {
        CallRecord callRecord = new CallRecord();

        ContactsInfo contactsInfo = new ContactsInfo();
        callRecord.setContactsInfo(contactsInfo);
        assertEquals(contactsInfo, callRecord.getContactsInfo());
    }

    public void testSetContactsInfo() throws Exception {
        CallRecord callRecord = new CallRecord();

        ContactsInfo contactsInfo = new ContactsInfo();
        callRecord.setContactsInfo(contactsInfo);
        assertEquals(contactsInfo, callRecord.getContactsInfo());
    }

    public void testGetBeginTimeformatter() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setBeginTime("20110606060606");
        assertNotNull(callRecord.getBeginTimeformatter());
    }

    public void testGetBeginHour() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setBeginTime("20110606060606");
        assertNotNull(callRecord.getBeginHour());
    }

    public void testGetBeginDay() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setBeginTime("20110606060606");
        assertNotNull(callRecord.getBeginDay());
    }

    public void testGetBeginTime() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setBeginTime("20110606060606");
        assertEquals("20110606060606", callRecord.getBeginTime());
    }

    public void testSetBeginTime() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setBeginTime("20110606060606");
        assertEquals("20110606060606", callRecord.getBeginTime());
    }

    public void testGetDuration() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setDuration(1234);
        assertEquals(1234, callRecord.getDuration());
    }

    public void testSetDuration() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setDuration(1234);
        assertEquals(1234, callRecord.getDuration());
    }

    public void testGetType() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setType(1);
        assertEquals(1, callRecord.getType());
    }

    public void testSetType() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setType(1);
        assertEquals(1, callRecord.getType());
    }

    public void testGetRead() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setRead(1);
        assertEquals(1, callRecord.getRead());
    }

    public void testSetRead() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setRead(1);
        assertEquals(1, callRecord.getRead());
    }

    public void testIsReaded() throws Exception {
        CallRecord callRecord = new CallRecord();
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        assertTrue(callRecord.isReaded());
        callRecord.setRead(BussinessConstants.DictInfo.NO);
        assertFalse(callRecord.isReaded());
    }

    public void testGetRecordSearchUnit() throws Exception {
        CallRecord callRecord = new CallRecord();

        RecordSearchUnit recordSearchUnit = new RecordSearchUnit();
        callRecord.setRecordSearchUnit(recordSearchUnit);
        assertEquals(recordSearchUnit, callRecord.getRecordSearchUnit());
    }

    public void testSetRecordSearchUnit() throws Exception {
        CallRecord callRecord = new CallRecord();

        RecordSearchUnit recordSearchUnit = new RecordSearchUnit();
        callRecord.setRecordSearchUnit(recordSearchUnit);
        assertEquals(recordSearchUnit, callRecord.getRecordSearchUnit());
    }
}