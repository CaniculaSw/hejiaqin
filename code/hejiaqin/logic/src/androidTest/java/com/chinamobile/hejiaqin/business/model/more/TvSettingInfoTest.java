package com.chinamobile.hejiaqin.business.model.more;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class TvSettingInfoTest extends TestCase {

    public void testGetNumberOne() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberOne("1");
        assertEquals(tvSettingInfo.getNumberOne(), "1");
    }

    public void testSetNumberOne() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberOne("1");
        assertEquals(tvSettingInfo.getNumberOne(), "1");
    }

    public void testGetNumberTwo() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberTwo("1");
        assertEquals(tvSettingInfo.getNumberTwo(), "1");
    }

    public void testSetNumberTwo() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberTwo("1");
        assertEquals(tvSettingInfo.getNumberTwo(), "1");
    }

    public void testGetNumberThree() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberThree("1");
        assertEquals(tvSettingInfo.getNumberThree(), "1");
    }

    public void testSetNumberThree() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberThree("1");
        assertEquals(tvSettingInfo.getNumberThree(), "1");
    }

    public void testGetNumberFour() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberFour("1");
        assertEquals(tvSettingInfo.getNumberFour(), "1");
    }

    public void testSetNumberFour() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberFour("1");
        assertEquals(tvSettingInfo.getNumberFour(), "1");
    }

    public void testIsAutoAnswer() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setAutoAnswer(true);
        assertEquals(tvSettingInfo.isAutoAnswer(), true);
    }

    public void testSetAutoAnswer() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setAutoAnswer(true);
        assertEquals(tvSettingInfo.isAutoAnswer(), true);
    }

    public void testIsInAutoAnswerList() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setAutoAnswer(false);
        assertFalse(tvSettingInfo.isInAutoAnswerList(""));

        tvSettingInfo.setAutoAnswer(true);
        assertTrue(tvSettingInfo.isInAutoAnswerList(""));

        tvSettingInfo.setNumberOne("1");
        tvSettingInfo.setNumberTwo("2");
        tvSettingInfo.setNumberThree("3");
        tvSettingInfo.setNumberFour("4");
        assertTrue(tvSettingInfo.isInAutoAnswerList("1"));
        assertTrue(tvSettingInfo.isInAutoAnswerList("2"));
        assertTrue(tvSettingInfo.isInAutoAnswerList("3"));
        assertTrue(tvSettingInfo.isInAutoAnswerList("4"));
        assertFalse(tvSettingInfo.isInAutoAnswerList("5"));
    }
}