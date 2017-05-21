package com.chinamobile.hejiaqin.business.dbapdater;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.SystemMessage;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class SystemMessageDbAdapterTest extends AndroidTestCase {

    String userID = "123456";

    public void testGetInstance() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
    }

    public void testAdd() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
    }

    public void testQueryAll() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
    }

    public void testQuerySystemMessageByID() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
        String id = systemMessageList.get(0).getId();
        SystemMessage newSystemMessage = dbAdapter.querySystemMessageByID(id);
        assertNotNull(newSystemMessage);
        assertEquals(newSystemMessage.getTitle(), "XXX");
    }

    public void testDeleteSystemMessageByIDs() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
        String id = systemMessageList.get(0).getId();
        SystemMessage newSystemMessage = dbAdapter.querySystemMessageByID(id);
        assertNotNull(newSystemMessage);
        assertEquals(newSystemMessage.getTitle(), "XXX");

        dbAdapter.deleteSystemMessageByID("001");
        assertNotSame("XXX", dbAdapter.querySystemMessageByID("001").getTitle());
    }

    public void testDeleteSystemMessageByID() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
        String id = systemMessageList.get(0).getId();
        SystemMessage newSystemMessage = dbAdapter.querySystemMessageByID(id);
        assertNotNull(newSystemMessage);
        assertEquals(newSystemMessage.getTitle(), "XXX");

        dbAdapter.deleteSystemMessageByID("001");
        assertNotSame("XXX", dbAdapter.querySystemMessageByID("001").getTitle());
    }

    public void testDeleteAll() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        dbAdapter.deleteAll();

        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("XXX");
        systemMessage.setTime("12:23:22");
        systemMessage.setId("001");
        systemMessage.setContent("hello world.");
        dbAdapter.add(systemMessage);

        List<SystemMessage> systemMessageList = dbAdapter.queryAll();
        assertNotNull(systemMessageList);
        String id = systemMessageList.get(0).getId();
        SystemMessage newSystemMessage = dbAdapter.querySystemMessageByID(id);
        assertNotNull(newSystemMessage);
        assertEquals(newSystemMessage.getTitle(), "XXX");

        dbAdapter.deleteAll();
        assertNotSame("XXX", dbAdapter.querySystemMessageByID("001").getTitle());
    }

    public void testGetDbHelper() throws Exception {
        SystemMessageDbAdapter dbAdapter = SystemMessageDbAdapter.getInstance(getContext(), userID);
        assertNotNull(dbAdapter);
        assertNotNull(dbAdapter.getDbHelper());
    }
}