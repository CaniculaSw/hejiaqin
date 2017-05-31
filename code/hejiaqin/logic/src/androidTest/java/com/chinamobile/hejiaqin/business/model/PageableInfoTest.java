package com.chinamobile.hejiaqin.business.model;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class PageableInfoTest extends TestCase {

    public void testGetCurrentPageno() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setCurrentPageno(1);
        assertEquals(pageableInfo.getCurrentPageno(), 1);
    }

    public void testSetCurrentPageno() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setCurrentPageno(1);
        assertEquals(pageableInfo.getCurrentPageno(), 1);
    }

    public void testGetTotalRows() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setTotalRows(1);
        assertEquals(pageableInfo.getTotalRows(), 1);
    }

    public void testSetTotalRows() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setTotalRows(1);
        assertEquals(pageableInfo.getTotalRows(), 1);
    }

    public void testGetEachPageRows() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setEachPageRows(1);
        assertEquals(pageableInfo.getEachPageRows(), 1);
    }

    public void testSetEachPageRows() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setEachPageRows(1);
        assertEquals(pageableInfo.getEachPageRows(), 1);
    }

    public void testGetTotalPages() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setTotalPages(1);
        assertEquals(pageableInfo.getTotalPages(), 1);
    }

    public void testSetTotalPages() throws Exception {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setTotalPages(1);
        assertEquals(pageableInfo.getTotalPages(), 1);
    }
}