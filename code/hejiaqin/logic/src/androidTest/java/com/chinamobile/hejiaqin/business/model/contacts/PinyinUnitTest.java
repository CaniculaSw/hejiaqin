package com.chinamobile.hejiaqin.business.model.contacts;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class PinyinUnitTest extends TestCase {

    public void testGetChineseWords() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setChineseWords("abc");
        assertEquals("abc", pinyinUnit.getChineseWords());
    }

    public void testSetChineseWords() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setChineseWords("abc");
        assertEquals("abc", pinyinUnit.getChineseWords());
    }

    public void testGetChinesePinyin() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setChinesePinyin("abc");
        assertEquals("abc", pinyinUnit.getChinesePinyin());
    }

    public void testSetChinesePinyin() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setChinesePinyin("abc");
        assertEquals("abc", pinyinUnit.getChinesePinyin());
    }

    public void testGetFirstChars() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setFirstChars("a");
        assertEquals("a", pinyinUnit.getFirstChars());
    }

    public void testSetFirstChars() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setFirstChars("a");
        assertEquals("a", pinyinUnit.getFirstChars());
    }

    public void testGetFirstCharIndexs() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        int[] indexes = new int[]{1, 2, 3};
        pinyinUnit.setFirstCharIndexs(indexes);
        assertEquals(indexes, pinyinUnit.getFirstCharIndexs());
    }

    public void testSetFirstCharIndexs() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        int[] indexes = new int[]{1, 2, 3};
        pinyinUnit.setFirstCharIndexs(indexes);
        assertEquals(indexes, pinyinUnit.getFirstCharIndexs());
    }

    public void testIsValid() throws Exception {
        PinyinUnit pinyinUnit = new PinyinUnit();
        assertFalse(pinyinUnit.isValid());
    }
}