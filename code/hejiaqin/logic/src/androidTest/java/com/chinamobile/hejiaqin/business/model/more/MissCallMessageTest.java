package com.chinamobile.hejiaqin.business.model.more;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class MissCallMessageTest extends TestCase {

    public void testGetFrom() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setFrom("123");
        assertEquals("123", missCallMessage.getFrom());
    }

    public void testSetFrom() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setFrom("123");
        assertEquals("123", missCallMessage.getFrom());
    }

    public void testGetDate() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setDate("123");
        assertEquals("123", missCallMessage.getDate());
    }

    public void testSetDate() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setDate("123");
        assertEquals("123", missCallMessage.getDate());
    }

    public void testIsChecked() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setChecked(true);
        assertEquals(true, missCallMessage.isChecked());
    }

    public void testSetChecked() throws Exception {
        MissCallMessage missCallMessage = new MissCallMessage();
        missCallMessage.setChecked(true);
        assertEquals(true, missCallMessage.isChecked());
    }
}