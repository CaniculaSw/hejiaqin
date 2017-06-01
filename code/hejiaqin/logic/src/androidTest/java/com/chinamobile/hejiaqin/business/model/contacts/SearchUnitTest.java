package com.chinamobile.hejiaqin.business.model.contacts;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class SearchUnitTest extends TestCase {

    public void testGetNumberLst() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        List<NumberInfo> numberInfoList = new ArrayList<>();
        searchUnit.setNumberLst(numberInfoList);
        assertEquals(numberInfoList, searchUnit.getNumberLst());
    }

    public void testSetNumberLst() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        List<NumberInfo> numberInfoList = new ArrayList<>();
        searchUnit.setNumberLst(numberInfoList);
        assertEquals(numberInfoList, searchUnit.getNumberLst());
    }

    public void testGetPinyinUnit() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        PinyinUnit pinyinUnit = new PinyinUnit();
        searchUnit.setPinyinUnit(pinyinUnit);
        assertEquals(pinyinUnit, searchUnit.getPinyinUnit());
    }

    public void testSetPinyinUnit() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        PinyinUnit pinyinUnit = new PinyinUnit();
        searchUnit.setPinyinUnit(pinyinUnit);
        assertEquals(pinyinUnit, searchUnit.getPinyinUnit());
    }

    public void testGetNumberText() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        assertNotNull(searchUnit.getNumberText());
    }

    public void testGetNameText() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        assertNotNull(searchUnit.getNameText());
    }

    public void testGetChinesePinyin() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        assertNotNull(searchUnit.getChinesePinyin());
    }

    public void testSearch() throws Exception {
        SearchUnit searchUnit = new SearchUnit();
        assertFalse(searchUnit.search(""));
    }
}