package com.chinamobile.hejiaqin.business.model.more;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class SystemMessageTest extends TestCase {

    public void testGetId() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setId("123");
        assertEquals("123", systemMessage.getId());
    }

    public void testSetId() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setId("123");
        assertEquals("123", systemMessage.getId());
    }

    public void testGetContent() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setContent("123");
        assertEquals("123", systemMessage.getContent());
    }

    public void testSetContent() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setContent("123");
        assertEquals("123", systemMessage.getContent());
    }

    public void testGetTitle() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("123");
        assertEquals("123", systemMessage.getTitle());
    }

    public void testSetTitle() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTitle("123");
        assertEquals("123", systemMessage.getTitle());
    }

    public void testGetTime() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTime("123");
        assertEquals("123", systemMessage.getTime());
    }

    public void testSetTime() throws Exception {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setTime("123");
        assertEquals("123", systemMessage.getTime());
    }
}