package com.customer.framework.utils;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class StringUtilTest extends TestCase {

    public void testIsNullOrEmpty() throws Exception {
        assertTrue(StringUtil.isNullOrEmpty(""));
        assertTrue(StringUtil.isNullOrEmpty(null));
        assertTrue(!StringUtil.isNullOrEmpty("abc"));
    }

    public void testEquals() throws Exception {
        assertTrue(StringUtil.equals("abc", "abc"));
        assertTrue(!StringUtil.equals("abc", "aaa"));
        assertTrue(!StringUtil.equals("abc", "Abc"));
    }

    public void testEqualsIgnoreCase() throws Exception {
        assertTrue(StringUtil.equals("abc", "abc"));
        assertTrue(!StringUtil.equals("abc", "aaa"));
        assertTrue(StringUtil.equals("abc", "Abc"));
    }

    public void testSplitStringToList() throws Exception {
        String content = "aaa,bbb,ccc";
        List<String> contentList = StringUtil.splitStringToList(content, ",");
        assertNotNull(contentList);
        assertEquals(contentList.size(), 3);

        assertEquals("aaa", contentList.get(0));
        assertEquals("bbb", contentList.get(1));
        assertEquals("ccc", contentList.get(2));
    }

    public void testArrayToString() throws Exception {
        String arrayStr = StringUtil.arrayToString(new String[] { "aaa", "bbb", "ccc" }, "+");
        assertNotNull(arrayStr);
        assertEquals(arrayStr, "aaa+bbb+ccc");
    }

    public void testListToString() throws Exception {
        List<String> stringList = new ArrayList<>();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");

        String listStr = StringUtil.listToString(stringList, "+");
        assertNotNull(listStr);
        assertEquals(listStr, "aaa+bbb+ccc");
    }

    public void testContains() throws Exception {
        assertTrue(StringUtil.contains("abc", "a"));
    }

    public void testParseInt() throws Exception {
        assertEquals(StringUtil.parseInt("12", true), 12);
    }

    public void testIsNumeric() throws Exception {
        assertTrue(StringUtil.isNumeric("1234567", true));
        assertTrue(StringUtil.isNumeric("123 4567", true));
        assertTrue(!StringUtil.isNumeric("123b4567", true));
    }

}
