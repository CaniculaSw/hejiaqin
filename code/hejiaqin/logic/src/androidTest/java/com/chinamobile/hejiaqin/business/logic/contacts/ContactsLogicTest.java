package com.chinamobile.hejiaqin.business.logic.contacts;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class ContactsLogicTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    ContactsLogic contactsLogic;
    Handler handler;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contactsLogic = new ContactsLogic();
        contactsLogic.init(getContext());
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                cancelWait();
            }
        };
        contactsLogic.addHandler(handler);
    }

    public void testFetchLocalContactLst() throws Exception {
        contactsLogic.fetchLocalContactLst();
        syncWait();
    }

    public void testFetchAppContactLst() throws Exception {
        contactsLogic.fetchAppContactLst();
        syncWait();
    }

    public void testGetCacheLocalContactLst() throws Exception {
        contactsLogic.getCacheLocalContactLst();
    }

    public void testGetCacheAppContactLst() throws Exception {
        contactsLogic.getCacheAppContactLst();
    }

    public void testSearchLocalContactLst() throws Exception {
        contactsLogic.searchLocalContactLst("a", "123");
    }

    public void testSearchAppContactLst() throws Exception {
        contactsLogic.searchAppContactLst("a", "123");
    }

    public void testAddAppContact() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123456");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);
        contactsInfo.setName("abc");

        contactsLogic.addAppContact(contactsInfo);
        syncWait();
    }

    public void testAddAppContact1() throws Exception {
        contactsLogic.addAppContact("abc", "123456", "123");
        syncWait();
    }

    public void testBatchAddAppContacts() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123456");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);
        contactsInfo.setName("abc");
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        contactsInfoList.add(contactsInfo);

        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();
    }

    public void testUpdateAppContact() throws Exception {
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();
    }

    public void testDeleteAppContact() throws Exception {
        contactsLogic.deleteAppContact("123");
        syncWait();
    }

    public void testIsAppContactExist() throws Exception {
        contactsLogic.isAppContactExist("123456");
    }

    public void testQueryContactCallRecords() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123456");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);
        contactsInfo.setName("abc");
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        contactsInfoList.add(contactsInfo);

        contactsLogic.queryContactCallRecords(contactsInfo);
        syncWait();
    }

    public void testDeleteContactCallRecords() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123456");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);
        contactsInfo.setName("abc");
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        contactsInfoList.add(contactsInfo);

        contactsLogic.deleteContactCallRecords(contactsInfo);
        syncWait();
    }


    private void syncWait() {
        waitingFlag = true;
        while (waitingFlag) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
    }

    private void cancelWait() {
        waitingFlag = false;
    }
}