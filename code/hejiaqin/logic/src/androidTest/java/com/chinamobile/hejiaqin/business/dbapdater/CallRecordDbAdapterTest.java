package com.chinamobile.hejiaqin.business.dbapdater;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.time.DateTimeUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class CallRecordDbAdapterTest extends AndroidTestCase {
    private String userId = "123456";

    public void testGetInstance() throws Exception {
        CallRecordDbAdapter instance = CallRecordDbAdapter.getInstance(getContext(), userId);
        assertNotNull(instance);

        assertEquals(instance, CallRecordDbAdapter.getInstance(getContext(), userId));
    }

    public void testDelById() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());

        CallRecord newCallRecord = callRecordList.get(0);
        dbAdapter.delById(new String[]{newCallRecord.getId()});
        callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertTrue(callRecordList.isEmpty());
    }

    public void testQueryWithNumbers() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
    }

    public void testDeleteByNumbers() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);

        dbAdapter.deleteByNumbers(new String[]{noCountryNumber});
        callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertTrue(callRecordList.isEmpty());
    }

    public void testQueryWithName() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String peerName = "abc";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        callRecord.setPeerName(peerName);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithName();
        assertNotNull(callRecordList);
    }

    public void testSearchNumbers() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String peerName = "abc";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        callRecord.setPeerName(peerName);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.searchNumbers("138100");
        assertNotNull(callRecordList);
    }

    public void testInsert() throws Exception {

        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);

    }

    public void testUpdateByRecordId() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());
        assertEquals(callRecordList.get(0).getPeerNumber(), "13810001000");

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseInfo.CallRecord.PEER_NUMBER, "13810001001");
        dbAdapter.updateByRecordId(recordId, contentValues);
        callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());
        assertEquals(callRecordList.get(0).getPeerNumber(), "13810001001");
    }

    public void testUpdateById() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());
        assertEquals(callRecordList.get(0).getPeerNumber(), "13810001000");

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseInfo.CallRecord.PEER_NUMBER, "13810001001");
        dbAdapter.updateById(callRecordList.get(0).getId(), contentValues);
        callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());
        assertEquals(callRecordList.get(0).getPeerNumber(), "13810001001");
    }

    public void testDelAll() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        String calleeNumber = "13810001000";
        String noCountryNumber = CommonUtils.getPhoneNumber(calleeNumber);
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(noCountryNumber);
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(100);
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        dbAdapter.insert(callRecord);


        List<CallRecord> callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertNotNull(callRecordList);
        assertFalse(callRecordList.isEmpty());

        dbAdapter.delAll();
        callRecordList = dbAdapter.queryWithNumbers(new String[]{noCountryNumber});
        assertTrue(callRecordList.isEmpty());
    }

    public void testGetDbHelper() throws Exception {
        CallRecordDbAdapter dbAdapter = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()));
        assertNotNull(dbAdapter);
        assertNotNull(dbAdapter.getDbHelper());
    }
}