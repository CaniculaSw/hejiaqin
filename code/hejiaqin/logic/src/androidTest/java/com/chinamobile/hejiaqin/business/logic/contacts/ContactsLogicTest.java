package com.chinamobile.hejiaqin.business.logic.contacts;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.contacts.rsp.ContactBean;
import com.customer.framework.component.net.NetInvoker;

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
        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.fetchAppContactLst();
        syncWait();

        List<ContactBean> contactBeanList = new ArrayList<>();
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        contactBean.setName("123");
        contactBean.setPhone("2");
        contactBean.setContactId("123");
        contactBeanList.add(contactBean);
        contactsLogic.setInvoker(NetInvoker.buildInvoker1().setResultObj(contactBeanList));
        contactsLogic.fetchAppContactLst();
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.fetchAppContactLst();
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.fetchAppContactLst();
        syncWait();

        contactsLogic.setInvoker(null);
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

        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.addAppContact(contactsInfo);
        syncWait();

        List<ContactBean> contactBeanList = new ArrayList<>();
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        contactBean.setName("123");
        contactBean.setPhone("2");
        contactBean.setContactId("123");
        contactBeanList.add(contactBean);
        contactsLogic.setInvoker(NetInvoker.buildInvoker1().setResultObj(contactBeanList));
        contactsLogic.addAppContact(contactsInfo);
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.addAppContact(contactsInfo);
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.addAppContact(contactsInfo);
        syncWait();

        contactsLogic.setInvoker(null);
        contactsLogic.addAppContact(contactsInfo);
        syncWait();
    }

    public void testAddAppContact1() throws Exception {
        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.addAppContact("abc", "123456", "123");
        syncWait();

        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        contactBean.setName("123");
        contactBean.setPhone("2");
        contactBean.setContactId("123");
        contactsLogic.setInvoker(NetInvoker.buildInvoker1().setResultObj(contactBean));
        contactsLogic.addAppContact("abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.addAppContact("abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.addAppContact("abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(null);
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

        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();

        List<ContactBean> contactBeanList = new ArrayList<>();
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        contactBean.setName("123");
        contactBean.setPhone("2");
        contactBean.setContactId("123");
        contactBeanList.add(contactBean);
        contactsLogic.setInvoker(NetInvoker.buildInvoker1().setResultObj(contactBeanList));
        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();

        contactsLogic.setInvoker(null);
        contactsLogic.batchAddAppContacts(contactsInfoList);
        syncWait();
    }

    public void testUpdateAppContact() throws Exception {
        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();

        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        contactBean.setName("123");
        contactBean.setPhone("2");
        contactBean.setContactId("123");
        contactsLogic.setInvoker(NetInvoker.buildInvoker1().setResultObj(contactBean));
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();

        contactsLogic.setInvoker(null);
        contactsLogic.updateAppContact("123", "abc", "123456", "123");
        syncWait();
    }

    public void testDeleteAppContact() throws Exception {
        contactsLogic.setInvoker(NetInvoker.buildInvoker1());
        contactsLogic.deleteAppContact("123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker2());
        contactsLogic.deleteAppContact("123");
        syncWait();

        contactsLogic.setInvoker(NetInvoker.buildInvoker3());
        contactsLogic.deleteAppContact("123");
        syncWait();

        contactsLogic.setInvoker(null);
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